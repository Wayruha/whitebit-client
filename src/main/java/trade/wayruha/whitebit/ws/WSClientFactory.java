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
    final Subscription subscription = new Subscription("lastprice_subscribe", market.toArray());
    final WebSocketSubscriptionClient<LastPriceUpdate> client = new WebSocketSubscriptionClient<>(apiClient, objectMapper, callback, new LastPriceUpdate.Parser(objectMapper));
    client.connect(Set.of(subscription));
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

  /**
   * To find supported intervals please read https://whitebit-exchange.github.io/api-docs/public/websocket/#kline
   */
  public WebSocketSubscriptionClient<List<KlineUpdate>> candlesSubscription(Set<Market> markets, int intervalSec, WebSocketCallback<List<KlineUpdate>> callback) {
    final Set<Subscription> subscriptions = markets.stream()
        .map(market -> new Subscription("candles_subscribe", market, intervalSec))
        .collect(Collectors.toSet());
    final WebSocketSubscriptionClient<List<KlineUpdate>> client = new WebSocketSubscriptionClient<>(apiClient, objectMapper, callback, new KlineUpdate.Parser(objectMapper));
    client.connect(subscriptions);
    return client;
  }

  public WebSocketSubscriptionClient<MarketTradesUpdate> marketTradesSubscription(Set<Market> markets, WebSocketCallback<MarketTradesUpdate> callback) {
    final Subscription subscription = new Subscription("trades_subscribe", markets.toArray());
    final WebSocketSubscriptionClient<MarketTradesUpdate> client = new WebSocketSubscriptionClient<>(apiClient, objectMapper, callback, new MarketTradesUpdate.Parser(objectMapper));
    client.connect(Set.of(subscription));
    return client;
  }

  //private subscriptions

  public WebSocketPrivateClient<SpotBalanceUpdate> spotBalanceSubscription(Set<String> assets, WebSocketCallback<SpotBalanceUpdate> callback) {
    final Subscription subscription = new Subscription("balanceSpot_subscribe", assets.toArray());
    final WebSocketPrivateClient<SpotBalanceUpdate> client = new WebSocketPrivateClient<>(apiClient, objectMapper, callback, new SpotBalanceUpdate.Parser(objectMapper));
    client.connect(Set.of(subscription));
    return client;
  }

  public WebSocketPrivateClient<MarginBalanceUpdate> marginBalanceSubscription(Set<String> assets, WebSocketCallback<MarginBalanceUpdate> callback) {
    final Subscription subscription = new Subscription("balanceMargin_subscribe", assets.toArray());
    final WebSocketPrivateClient<MarginBalanceUpdate> client = new WebSocketPrivateClient<>(apiClient, objectMapper, callback, new MarginBalanceUpdate.Parser(objectMapper));
    client.connect(Set.of(subscription));
    return client;
  }

  public WebSocketPrivateClient<PendingOrderUpdate> pendingOrdersSubscription(Set<Market> markets, WebSocketCallback<PendingOrderUpdate> callback) {
    if (markets.size() > MAX_MARKETS_FOR_PENDING_ORDERS_SUBSCRIPTION) throw new IllegalArgumentException("Max markets count is " + MAX_MARKETS_FOR_PENDING_ORDERS_SUBSCRIPTION);
    final Subscription subscription = new Subscription("ordersPending_subscribe", markets.toArray());
    final WebSocketPrivateClient<PendingOrderUpdate> client = new WebSocketPrivateClient<>(apiClient, objectMapper, callback, new PendingOrderUpdate.Parser(objectMapper));
    client.connect(Set.of(subscription));
    return client;
  }

  public WebSocketPrivateClient<Order> executedOrdersSubscription(Set<Market> markets, ExecutedOrdersFilter orderTypeFilter, WebSocketCallback<Order> callback) {
    if (markets.size() > MAX_MARKETS_FOR_PENDING_ORDERS_SUBSCRIPTION) throw new IllegalArgumentException("Max markets count is " + MAX_MARKETS_FOR_PENDING_ORDERS_SUBSCRIPTION);
    final Subscription subscription = new Subscription("ordersExecuted_subscribe", markets.toArray(), orderTypeFilter);
    final WebSocketPrivateClient<Order> client = new WebSocketPrivateClient<>(apiClient, objectMapper, callback, new ValueModelParser<>(objectMapper, Order.class));
    client.connect(Set.of(subscription));
    return client;
  }

  public WebSocketPrivateClient<DealsUpdate> dealsSubscription(Set<Market> markets, WebSocketCallback<DealsUpdate> callback) {
    if (markets.size() > MAX_MARKETS_FOR_PENDING_ORDERS_SUBSCRIPTION) throw new IllegalArgumentException("Max markets count is " + MAX_MARKETS_FOR_PENDING_ORDERS_SUBSCRIPTION);
    final Subscription subscription = new Subscription("deals_subscribe", List.of(markets.toArray()));
    final WebSocketPrivateClient<DealsUpdate> client = new WebSocketPrivateClient<>(apiClient, objectMapper, callback, new DealsUpdate.Parser(objectMapper));
    client.connect(Set.of(subscription));
    return client;
  }
}
