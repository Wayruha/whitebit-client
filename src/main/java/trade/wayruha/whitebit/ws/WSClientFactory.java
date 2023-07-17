package trade.wayruha.whitebit.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import trade.wayruha.whitebit.ClientConfig;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.client.ApiClient;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.dto.Order;
import trade.wayruha.whitebit.dto.request.ExecutedOrdersFilter;
import trade.wayruha.whitebit.dto.ws.*;
import trade.wayruha.whitebit.utils.ValueModelParser;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static trade.wayruha.whitebit.ws.Constants.MAX_MARKETS_FOR_PENDING_ORDERS_SUBSCRIPTION;
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


  //public subscriptions

  public WebSocketSubscriptionClient<LastPriceUpdate> lastPriceSubscription(Set<Market> market, WebSocketCallback<LastPriceUpdate> callback) {
    final Set<Subscription> subscriptions = market.stream()
        .map(m -> new Subscription("lastprice_subscribe", market.toArray()))
        .collect(Collectors.toSet());
    final WebSocketSubscriptionClient<LastPriceUpdate> client = new WebSocketSubscriptionClient<>(apiClient, objectMapper, callback, new LastPriceUpdate.Parser(objectMapper));
    client.connect(subscriptions);
    return client;
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

  //private subscriptions
  public WebSocketPrivateClient<SpotBalanceUpdate> spotBalanceSubscription(Set<String> assets, WebSocketCallback<SpotBalanceUpdate> callback) {
    final Set<Subscription> subscriptions = assets.stream()
        .map(m -> new Subscription("balanceSpot_subscribe", assets.toArray()))
        .collect(Collectors.toSet());
    final WebSocketPrivateClient<SpotBalanceUpdate> client = new WebSocketPrivateClient<>(apiClient, objectMapper, callback, new SpotBalanceUpdate.Parser(objectMapper));
    client.connect(subscriptions);
    return client;
  }

  public WebSocketPrivateClient<MarginBalanceUpdate> marginBalanceSubscription(Set<String> assets, WebSocketCallback<MarginBalanceUpdate> callback) {
    final Set<Subscription> subscriptions = assets.stream()
        .map(m -> new Subscription("balanceMargin_subscribe", assets.toArray()))
        .collect(Collectors.toSet());
    final WebSocketPrivateClient<MarginBalanceUpdate> client = new WebSocketPrivateClient<>(apiClient, objectMapper, callback, new MarginBalanceUpdate.Parser(objectMapper));
    client.connect(subscriptions);
    return client;
  }

  public WebSocketPrivateClient<PendingOrderUpdate> pendingOrdersSubscription(Set<Market> markets, WebSocketCallback<PendingOrderUpdate> callback) {
    if (markets.size() > MAX_MARKETS_FOR_PENDING_ORDERS_SUBSCRIPTION) throw new IllegalArgumentException("Max markets count is " + MAX_MARKETS_FOR_PENDING_ORDERS_SUBSCRIPTION);
    final Set<Subscription> subscriptions = markets.stream()
        .map(m -> new Subscription("ordersPending_subscribe", markets.toArray()))
        .collect(Collectors.toSet());
    final WebSocketPrivateClient<PendingOrderUpdate> client = new WebSocketPrivateClient<>(apiClient, objectMapper, callback, new PendingOrderUpdate.Parser(objectMapper));
    client.connect(subscriptions);
    return client;
  }

  public WebSocketPrivateClient<Order> executedOrdersSubscription(Set<Market> markets, ExecutedOrdersFilter orderTypeFilter, WebSocketCallback<Order> callback) {
    if (markets.size() > MAX_MARKETS_FOR_PENDING_ORDERS_SUBSCRIPTION) throw new IllegalArgumentException("Max markets count is " + MAX_MARKETS_FOR_PENDING_ORDERS_SUBSCRIPTION);
    final Set<Subscription> subscriptions = markets.stream()
        .map(m -> new Subscription("ordersExecuted_subscribe", markets.toArray(), orderTypeFilter))
        .collect(Collectors.toSet());
    final WebSocketPrivateClient<Order> client = new WebSocketPrivateClient<>(apiClient, objectMapper, callback, new ValueModelParser<>(objectMapper, Order.class));
    client.connect(subscriptions);
    return client;
  }

  public WebSocketPrivateClient<DealsUpdate> dealsSubscription(Set<Market> markets, WebSocketCallback<DealsUpdate> callback) {
    if (markets.size() > MAX_MARKETS_FOR_PENDING_ORDERS_SUBSCRIPTION) throw new IllegalArgumentException("Max markets count is " + MAX_MARKETS_FOR_PENDING_ORDERS_SUBSCRIPTION);
    final Set<Subscription> subscriptions = markets.stream()
        .map(m -> new Subscription("deals_subscribe", List.of(markets.toArray())))
        .collect(Collectors.toSet());
    final WebSocketPrivateClient<DealsUpdate> client = new WebSocketPrivateClient<>(apiClient, objectMapper, callback, new DealsUpdate.Parser(objectMapper));
    client.connect(subscriptions);
    return client;
  }
}
