package trade.wayruha.whitebit.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import trade.wayruha.whitebit.ClientConfig;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.*;
import trade.wayruha.whitebit.dto.KillSwitchStatus;
import trade.wayruha.whitebit.dto.OrderCreationStatus;
import trade.wayruha.whitebit.dto.OrderDealsResponse;
import trade.wayruha.whitebit.dto.request.*;
import trade.wayruha.whitebit.exception.WBCloudException;
import trade.wayruha.whitebit.service.endpoint.OrdersEndpoint;
import trade.wayruha.whitebit.service.endpoint.TradeEndpoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static trade.wayruha.whitebit.APIConstant.*;

public class SpotTradeServiceV4 extends ServiceBase {
  private static final TypeReference<Map<Market, List<Deal>>> marketDealsTypeRef = new TypeReference<>() {};
  private static final TypeReference<Map<Market, List<Order>>> marketOrdersTypeRef = new TypeReference<>() {};
  private final TradeEndpoint tradeApi;
  private final OrdersEndpoint ordersApi;
  private final ObjectMapper mapper = ClientConfig.getObjectMapper();

  public SpotTradeServiceV4(WBConfig config) {
    super(config);
    this.ordersApi = createService(OrdersEndpoint.class);
    this.tradeApi = createService(TradeEndpoint.class);
  }

  public Order createOrder(NewOrderRequest req) {
    switch (req.getOrderType()) {
      case LIMIT:
        return client.executeSync(tradeApi.createLimitOrder(req)).getData();
      case MARKET:
        return client.executeSync(tradeApi.createMarketOrder(req)).getData();
      case MARKET_STOCK:
        return client.executeSync(tradeApi.createStockMarketOrder(req)).getData();
      default:
        throw new IllegalArgumentException("Unexpected order type: " + req.getOrderType());
    }
  }

  public StopOrder createStopOrder(StopOrderRequest req) {
    switch (req.getOrderType()) {
      case STOP_LIMIT:
        return client.executeSync(tradeApi.createStopLimitOrder(req)).getData();
      case STOP_MARKET:
        return client.executeSync(tradeApi.createStopMarketOrder(req)).getData();
      default:
        throw new IllegalArgumentException("Unexpected order type: " + req.getOrderType());
    }
  }

  public List<OrderCreationStatus> createBulkLimitOrders(List<NewOrderRequest> orders, boolean stopOnFail) {
    orders.forEach(order -> requireNonNull(order.getAmount()));
    final BulkOrdersCreation req = new BulkOrdersCreation(orders, stopOnFail);
    return client.executeSync(tradeApi.createBulkLimitOrders(req)).getData();
  }

  public Order cancelOrder(OrderID orderId) {
    return client.executeSync(ordersApi.cancelOrder(orderId)).getData();
  }

  public List<Order> getOpenOrders(OrderDetailsRequest request) {
    requireNonNull(request.getMarket(), "Market is required");
    validateLimitAndOffset(request.getLimit(), request.getOffset());
    try {
      return client.executeSync(ordersApi.getActiveOrders(request)).getData();
    } catch (WBCloudException ex){
      if(StringUtils.isNotEmpty(ex.getMessage()) && ex.getMessage().contains(ORDER_NOT_FOUND_ERROR_MSG))
        return List.of();
      throw ex;
    }
  }

  public Map<Market, List<Order>> getOrdersHistory(OrderDetailsRequest request) {
    validateLimitAndOffset(request.getLimit(), request.getOffset());
    final JsonNode data = client.executeSync(ordersApi.getExecutedOrders(request)).getData();
    if (data.isArray() && data.size() == 0) return new HashMap<>();

    final Map<Market, List<Order>> ordersMap = mapper.convertValue(data, marketOrdersTypeRef);
    populateMarket(ordersMap);
    return ordersMap;
  }

  public List<Deal> getDealsHistory(MarketFilter request) {
    validateLimitAndOffset(request.getLimit(), request.getOffset());
    return client.executeSync(ordersApi.dealsHistory(request)).getData();
  }

  public Map<Market, List<Deal>> getDealsHistory(DealsRequest request) {
    validateLimitAndOffset(request.getLimit(), request.getOffset());
    final JsonNode data = client.executeSync(ordersApi.dealsHistory(request)).getData();
    if (data.isArray() && data.size() == 0) return new HashMap<>();

    return mapper.convertValue(data, marketDealsTypeRef);
  }

  public OrderDealsResponse getOrderDeals(OrderDealsRequest request) {
    validateLimitAndOffset(request.getLimit(), request.getOffset());
    return client.executeSync(ordersApi.getOrderDeals(request)).getData();
  }

  public KillSwitchStatus createKillSwitch(KillSwitchRequest request) {
    return client.executeSync(tradeApi.createKillSwitch(request)).getData();
  }

  public KillSwitchStatus deleteKillSwitch(Market market) {
    final KillSwitchRequest req = new KillSwitchRequest(market, null);
    return client.executeSync(tradeApi.createKillSwitch(req)).getData();
  }

  public List<KillSwitchStatus> getKillSwitchStatus(Market market) {
    final MarketParameter req = new MarketParameter(market);
    return client.executeSync(tradeApi.getKillSwitchStatus(req)).getData();
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
