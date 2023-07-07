package trade.wayruha.whitebit.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import trade.wayruha.whitebit.ClientConfig;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.client.ApiClient;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.dto.ws.OrderBookUpdate;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import static trade.wayruha.whitebit.ws.Constants.MAX_ORDER_BOOK_DEPTH;

public class WSClientFactory {
  private final WBConfig config;
  private final ApiClient apiClient;
  @Setter
  private ObjectMapper objectMapper;

  public WSClientFactory(WBConfig config) {
    this(new ApiClient(config));
  }

  public WSClientFactory(ApiClient apiClient) {
    this.apiClient = apiClient;
    this.config = apiClient.getConfig();
    this.objectMapper = ClientConfig.getObjectMapper();
  }

  public WebSocketSubscriptionClient<OrderBookUpdate> orderBookSubscription(Set<Market> market, int depth, BigDecimal priceInterval,
                                                                            boolean multipleSubs, WebSocketCallback<OrderBookUpdate> callback) {
    if (depth > MAX_ORDER_BOOK_DEPTH) throw new IllegalArgumentException("Max OrderBook Depth is " + MAX_ORDER_BOOK_DEPTH);
    final Set<Subscription> subscriptions = market.stream()
        .map(m -> new Subscription("depth_subscribe", m, depth, priceInterval.toPlainString(), multipleSubs))
        .collect(Collectors.toSet());
    final WebSocketSubscriptionClient<OrderBookUpdate> client = new WebSocketSubscriptionClient<>(apiClient, objectMapper, callback, new OrderBookUpdate.Parser(objectMapper));
    client.connect(subscriptions);
    return client;
  }

  public WebSocketSubscriptionClient<OrderBookUpdate> orderBookSubscription(Set<Market> market, int depth, WebSocketCallback<OrderBookUpdate> callback) {
    return orderBookSubscription(market, depth, BigDecimal.ZERO, true, callback);
  }
}
