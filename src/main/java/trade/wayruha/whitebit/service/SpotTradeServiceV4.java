package trade.wayruha.whitebit.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import trade.wayruha.whitebit.ClientConfig;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.Deal;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.domain.Order;
import trade.wayruha.whitebit.domain.OrderID;
import trade.wayruha.whitebit.dto.KillSwitchStatus;
import trade.wayruha.whitebit.dto.OrderCreationStatus;
import trade.wayruha.whitebit.dto.OrderDealsResponse;
import trade.wayruha.whitebit.dto.request.*;
import trade.wayruha.whitebit.service.endpoint.SpotTradeEndpoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static trade.wayruha.whitebit.APIConstant.FETCH_ORDERS_MAX_LIMIT;
import static trade.wayruha.whitebit.APIConstant.FETCH_ORDERS_MAX_OFFSET;

public class SpotTradeServiceV4 extends ServiceBase {
  private static final TypeReference<Map<Market, List<Deal>>> marketDealsTypeRef = new TypeReference<>() {};
  private static final TypeReference<Map<Market, List<Order>>> marketOrdersTypeRef = new TypeReference<>() {};
  private final SpotTradeEndpoint api;
  private final ObjectMapper mapper = ClientConfig.getObjectMapper();

  public SpotTradeServiceV4(WBConfig config) {
    super(config);
    this.api = createService(SpotTradeEndpoint.class);
  }

  public Order createOrder(NewOrderRequest o) {
    final NewOrderRequest req;
    switch (o.getOrderType()) {
      case LIMIT:
        req = NewOrderRequest.limitOrder(o.getMarket(), o.getSide(), o.getBaseQty(), o.getPrice(), o.getClientOrderId(),
            o.getPostOnly(), o.getIoc());
        return client.executeSync(api.createLimitOrder(req)).getData();
      case MARKET:
        if (nonNull(o.getBaseQty())) {
          req = NewOrderRequest.marketOrderBaseAsset(o.getMarket(), o.getSide(), o.getBaseQty(), o.getClientOrderId());
          return client.executeSync(api.createStockMarketOrder(req)).getData();
        }
        req = NewOrderRequest.marketOrderQuoteAsset(o.getMarket(), o.getSide(), o.getQuoteQty(), o.getClientOrderId());
        return client.executeSync(api.createMarketOrder(req)).getData();
      default:
        throw new IllegalArgumentException("Unexpected order type: " + o.getOrderType());
    }
  }

  public Order createStopOrder(StopOrderRequest o) {
    final StopOrderRequest req;
    switch (o.getOrderType()) {
      case STOP_LIMIT:
        req = StopOrderRequest.stopLimitOrder(o.getMarket(), o.getSide(), o.getBaseQty(), o.getPrice(), o.getActivationPrice(), o.getClientOrderId());
        return client.executeSync(api.createStopLimitOrder(req)).getData();
      case STOP_MARKET:
        req = StopOrderRequest.stopMarketOrder(o.getMarket(), o.getSide(), o.getBaseQty(), o.getActivationPrice(), o.getClientOrderId());
        return client.executeSync(api.createStopMarketOrder(req)).getData();
      default:
        throw new IllegalArgumentException("Unexpected order type: " + o.getOrderType());
    }
  }

  public List<OrderCreationStatus> createBulkLimitOrders(List<NewOrderRequest> orders, boolean stopOnFail) {
    orders.forEach(order -> requireNonNull(order.getBaseQty()));
    final BulkOrdersCreation req = new BulkOrdersCreation(orders, stopOnFail);
    return client.executeSync(api.createBulkLimitOrders(req)).getData();
  }

  public Order cancelOrder(OrderID orderId) {
    return client.executeSync(api.cancelOrder(orderId)).getData();
  }

  public List<Order> getOpenOrders(OrderDetailsRequest request) {
    requireNonNull(request.getMarket(), "Market is required");
    validateLimitAndOffset(request.getLimit(), request.getOffset());
    return client.executeSync(api.getActiveOrders(request)).getData();
  }

  public Map<Market, List<Order>> getOrdersHistory(OrderDetailsRequest request) {
    validateLimitAndOffset(request.getLimit(), request.getOffset());
    final JsonNode data = client.executeSync(api.getExecutedOrders(request)).getData();
    if (data.isArray() && data.size() == 0) return new HashMap<>();

    final Map<Market, List<Order>> ordersMap = mapper.convertValue(data, marketOrdersTypeRef);
    populateMarket(ordersMap);
    return ordersMap;
  }

  public List<Deal> getDealsHistory(MarketFilter request) {
    validateLimitAndOffset(request.getLimit(), request.getOffset());
    return client.executeSync(api.dealsHistory(request)).getData();
  }

  public Map<Market, List<Deal>> getDealsHistory(DealsRequest request) {
    validateLimitAndOffset(request.getLimit(), request.getOffset());
    final JsonNode data = client.executeSync(api.dealsHistory(request)).getData();
    if (data.isArray() && data.size() == 0) return new HashMap<>();

    return mapper.convertValue(data, marketDealsTypeRef);
  }

  public OrderDealsResponse getOrderDeals(OrderDealsRequest request) {
    validateLimitAndOffset(request.getLimit(), request.getOffset());
    return client.executeSync(api.getOrderDeals(request)).getData();
  }

  public KillSwitchStatus createKillSwitch(KillSwitchRequest request) {
    return client.executeSync(api.createKillSwitch(request)).getData();
  }

  public KillSwitchStatus deleteKillSwitch(Market market) {
    final KillSwitchRequest req = new KillSwitchRequest(market, null);
    return client.executeSync(api.createKillSwitch(req)).getData();
  }

  public List<KillSwitchStatus> getKillSwitchStatus(Market market) {
    final MarketParameter req = new MarketParameter(market);
    return client.executeSync(api.getKillSwitchStatus(req)).getData();
  }

  private static void populateMarket(Map<Market, List<Order>> marketOrderMap) {
    marketOrderMap.forEach(SpotTradeServiceV4::populateMarket);
  }

  private static void populateMarket(Market market, List<Order> orders) {
    orders.forEach(o -> o.setMarket(market));
  }

  private static void validateLimitAndOffset(Integer limit, Integer offset) {
    if (nonNull(limit) && limit > FETCH_ORDERS_MAX_LIMIT)
      throw new IllegalArgumentException("Limit is higher than max allowed:" + limit + " vs " + FETCH_ORDERS_MAX_LIMIT);
    if (nonNull(offset) && offset > FETCH_ORDERS_MAX_OFFSET) {
      throw new IllegalArgumentException("Offset is higher than max allowed:" + offset + " vs " + FETCH_ORDERS_MAX_OFFSET);
    }
  }
}
