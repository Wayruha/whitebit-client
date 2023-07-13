package trade.wayruha.whitebit.ws;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import trade.wayruha.whitebit.APIConstant;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.utils.ModelParser;
import trade.wayruha.whitebit.exception.WebSocketException;
import trade.wayruha.whitebit.client.ApiClient;
import trade.wayruha.whitebit.utils.IdGenerator;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static trade.wayruha.whitebit.APIConstant.WEBSOCKET_INTERRUPTED_EXCEPTION;
import static trade.wayruha.whitebit.ws.Constants.*;

@Slf4j
public class WebSocketSubscriptionClient<T> extends WebSocketListener {
  protected static final WSRequest pingRequest = new WSRequest("ping");

  protected final WBConfig config;
  protected final ApiClient apiClient;
  protected final ObjectMapper objectMapper;
  protected final WebSocketCallback<T> callback;
  protected final ModelParser<T> modelParser;
  @Getter
  protected final int id;
  protected final String logPrefix;
  protected final Set<Subscription> subscriptions;

  protected WebSocket webSocket;
  protected Request connectionRequest;
  protected final AtomicInteger reconnectionCounter;
  @Setter
  protected ScheduledExecutorService scheduler;
  protected ScheduledFuture<?> scheduledPingTask;
  @Getter
  protected WSState state;
  @Getter
  protected long lastReceivedTime;

  public WebSocketSubscriptionClient(ApiClient apiClient, ObjectMapper mapper, WebSocketCallback<T> callback, ModelParser<T> modelParser) {
    this(apiClient, mapper, callback, modelParser, Executors.newSingleThreadScheduledExecutor());
  }

  public WebSocketSubscriptionClient(ApiClient apiClient, ObjectMapper mapper, WebSocketCallback<T> callback, ModelParser<T> modelParser,
                                     ScheduledExecutorService scheduler) {
    this.apiClient = apiClient;
    this.config = apiClient.getConfig();
    this.modelParser = modelParser;
    this.connectionRequest = buildRequestFromHost(this.config.getWebSocketHost());
    this.callback = callback;
    this.objectMapper = mapper;
    this.subscriptions = new HashSet<>();
    this.reconnectionCounter = new AtomicInteger(0);
    this.scheduler = scheduler;
    this.id = IdGenerator.getNextId();
    this.logPrefix = "[ws-" + this.id + "]";
  }
  protected void connect(Set<Subscription> subscriptions) {
    if (this.state != WSState.CONNECTED && this.state != WSState.CONNECTING) {
      log.debug("{} Connecting to channels {} ...", logPrefix, subscriptions);
      this.state = WSState.CONNECTING;
      this.webSocket = apiClient.createWebSocket(this.connectionRequest, this);
      if (this.webSocket == null) {
        log.error("Error connect {}", this.connectionRequest);
      }
    } else {
      log.warn("{} Already connected to channels {}", logPrefix, this.subscriptions);
    }
    this.scheduledPingTask = scheduler.scheduleAtFixedRate(new PingTask(), this.config.getWebSocketPingIntervalSec(), this.config.getWebSocketPingIntervalSec(), TimeUnit.SECONDS);
    reconnectionCounter.set(0); //reset reconnection indexer due to successful connection
    this.subscribe(subscriptions);
  }

  @SneakyThrows
  public void subscribe(Subscription subscription) {
    if (this.sendRequest(subscription)) {
      this.subscriptions.add(subscription);
    }
  }

  public void subscribe(Set<Subscription> subscriptions) {
    if (subscriptions.size() > APIConstant.MAX_WS_BATCH_SUBSCRIPTIONS)
      throw new IllegalArgumentException("Can't subscribe to more then " + APIConstant.MAX_WS_BATCH_SUBSCRIPTIONS + " channels in single WS connection");
    subscriptions.forEach(this::subscribe);
  }

  @SneakyThrows
  public boolean sendRequest(WSRequest request) {
    boolean result = false;
    log.debug("{} Try to send request {}", logPrefix, request);
    final String requestStr = objectMapper.writeValueAsString(request);
    if (webSocket != null) {
      result = webSocket.send(requestStr);
    }
    if (!result) {
      log.error("{} Failed to send message: {}. Closing the connection...", logPrefix, request);
      closeOnError(null);
    }
    return result;
  }

  public void close() {
    log.debug("{} Closing WS.", logPrefix);
    state = WSState.IDLE;
    if (webSocket != null) {
      webSocket.cancel();
      webSocket = null;
    }
    this.subscriptions.clear();
    if (nonNull(scheduledPingTask))
      scheduledPingTask.cancel(false);
  }

  public boolean reConnect() {
    boolean success = false;
    final Set<Subscription> cancelledSubscriptions = new HashSet<>(this.subscriptions);
    while (!success &&
        (config.isWebSocketReconnectAlways() || reconnectionCounter.incrementAndGet() < config.getWebSocketMaxReconnectAttempts())) {
      try {
        log.debug("{} Try to reconnect. Attempt #{}", logPrefix, reconnectionCounter.get());
        this.close();
        this.connect(cancelledSubscriptions);
        success = true;
      } catch (Exception e) {
        log.error("{} [Connection error] Error while reconnecting: {}", logPrefix, e.getMessage(), e);
        try {
          Thread.sleep(WEB_SOCKET_RECONNECTION_DELAY_MS);
        } catch (InterruptedException ex) {
          log.error("{} [Connection error] Interrupted while Thread.sleep(). {}", logPrefix, ex.getMessage());
        }
      }
    }
    log.debug("{} Successfully reconnected to WebSocket channels: {}.", logPrefix, cancelledSubscriptions);
    cancelledSubscriptions.clear();
    return success;
  }

  @SneakyThrows
  public void ping() {
    this.sendRequest(pingRequest);
    log.trace("{} Ping.", logPrefix);
  }

  @Override
  public void onOpen(WebSocket webSocket, Response response) {
    super.onOpen(webSocket, response);
    log.debug("{} onOpen WS event: Connected to channels {}", logPrefix, this.subscriptions);
    state = WSState.CONNECTED;
    lastReceivedTime = System.currentTimeMillis();
    this.webSocket = webSocket;
    callback.onOpen(response);
  }

  @Override
  public void onMessage(WebSocket webSocket, String text) {
    super.onMessage(webSocket, text);
    lastReceivedTime = System.currentTimeMillis();
    log.debug("{} onMessage WS event: {}", logPrefix, text);
    try {
      final ObjectNode response = objectMapper.readValue(text, ObjectNode.class);
      final JsonNode resultNode = response.get(WS_RESULT_PARAM);
      if (nonNull(resultNode)) {
        if (resultNode.asText().equalsIgnoreCase(WS_PONG_TEXT)) {
          log.trace("{} server: 'pong'", logPrefix);
        } else {
          checkForErrorResponse(response);
        }
      } else {
        final ArrayNode paramsNode = (ArrayNode) response.get(WS_PARAMS_PARAM);
        final T data = modelParser.parseUpdate(paramsNode);
        callback.onResponse(data);
      }
    } catch (Exception e) {
      log.error("{} WS message parsing failed: {}. Response:{}", log, e, text);
      closeOnError(e);
    }
  }

  @Override
  public void onMessage(WebSocket webSocket, ByteString bytes) {
    log.info("onMessage: {} " + bytes.toString());
    super.onMessage(webSocket, bytes);
  }

  @Override
  public void onClosed(WebSocket webSocket, int code, String reason) {
    log.debug("{} onClosed WS event: {}, code: {}", logPrefix, reason, code);
    super.onClosed(webSocket, code, reason);
    if (state == WSState.CONNECTED || state == WSState.CONNECTING) {
      state = WSState.IDLE;
    }
    log.debug("{} Closed", logPrefix);
    callback.onClosed(code, reason);
  }

  @SneakyThrows
  @Override
  public void onFailure(WebSocket webSocket, Throwable t, Response response) {
    if (state == WSState.IDLE) {
      return; //this is a handled websocket closure. No failure event should be created.
    }
    final String responseBody = response.body() != null ? response.body().string() : null;
    log.error("{} WS failed. Response: {}. Trying to reconnect...", logPrefix, responseBody, t);

    if (!reConnect()) {
      log.warn("{} [Connection error] Could not reconnect in {} attempts.", logPrefix, config.getWebSocketMaxReconnectAttempts());
      super.onFailure(webSocket, t, response);
      closeOnError(t);
      callback.onFailure(t, response);
    }
  }

  private void checkForErrorResponse(ObjectNode responseNode) {
    final JsonNode errorNode = responseNode.get(WS_ERROR_PARAM);
    final JsonNode resultNode = responseNode.get(WS_RESULT_PARAM);
    final JsonNode statusNode = resultNode.get(WS_STATUS_PARAM);
    final Optional<String> status = ofNullable(statusNode).map(JsonNode::asText);
    final boolean success = status.filter(txt -> txt.equalsIgnoreCase(WS_STATUS_SUCCESS))
        .isPresent();

    WSResponse.Error error = null;
    if (nonNull(errorNode) && !errorNode.isNull()) {
      error = objectMapper.convertValue(errorNode, WSResponse.Error.class);
    }
    if (!success || nonNull(error)) {
      throw new WebSocketException("WebSocket responded with error.", error, status.orElse(null));
    }
  }

  private void closeOnError(Throwable ex) {
    log.warn("{} [Connection error] Connection will be closed due to error: {}", logPrefix,
        ex != null ? ex.getMessage() : WEBSOCKET_INTERRUPTED_EXCEPTION);
    this.close();
    state = WSState.CLOSED_ON_ERROR;
  }

  static Request buildRequestFromHost(String host) {
    return new Request.Builder().url(host).build();
  }

  class PingTask implements Runnable {

    @SneakyThrows
    @Override
    public void run() {
      try {
        ping();
      } catch (Exception ex) {
        log.error("{} Ping error. Try to reconnect and send again in {} sec...", logPrefix, WEB_SOCKET_RECONNECTION_DELAY_MS / 1000);
        Thread.sleep(WEB_SOCKET_RECONNECTION_DELAY_MS);
        reConnect();
      }
    }
  }
}
