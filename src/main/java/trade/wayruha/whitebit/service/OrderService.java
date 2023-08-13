package trade.wayruha.whitebit.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import trade.wayruha.whitebit.ClientConfig;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.client.ApiClient;
import trade.wayruha.whitebit.domain.Deal;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.domain.Order;
import trade.wayruha.whitebit.domain.OrderID;
import trade.wayruha.whitebit.dto.OrderDealsResponse;
import trade.wayruha.whitebit.dto.request.DealsRequest;
import trade.wayruha.whitebit.dto.request.MarketFilter;
import trade.wayruha.whitebit.dto.request.OrderDealsRequest;
import trade.wayruha.whitebit.dto.request.OrderDetailsRequest;
import trade.wayruha.whitebit.exception.WBCloudException;
import trade.wayruha.whitebit.service.endpoint.OrdersEndpoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static trade.wayruha.whitebit.APIConstant.*;
import static trade.wayruha.whitebit.APIConstant.FETCH_ORDERS_MAX_OFFSET;

public class OrderService extends ServiceBase {
  private static final TypeReference<Map<Market, List<Deal>>> marketDealsTypeRef = new TypeReference<>() {};
  private static final TypeReference<Map<Market, List<Order>>> marketOrdersTypeRef = new TypeReference<>() {};
  private final OrdersEndpoint api;
  private final ObjectMapper mapper = ClientConfig.getObjectMapper();

  public OrderService(WBConfig config) {
    super(config);
    this.api = createService(OrdersEndpoint.class);
  }

  public OrderService(ApiClient client) {
    super(client);
    this.api = createService(OrdersEndpoint.class);
  }

  public Order cancelOrder(OrderID orderId) {
    return client.executeSync(api.cancelOrder(orderId)).getData();
  }

  public List<Order> getOpenOrders(OrderDetailsRequest request) {
    requireNonNull(request.getMarket(), "Market is required");
    validateLimitAndOffset(request.getLimit(), request.getOffset());
    try {
      return client.executeSync(api.getActiveOrders(request)).getData();
    } catch (WBCloudException ex){
      if(StringUtils.isNotEmpty(ex.getMessage()) && ex.getMessage().contains(ORDER_NOT_FOUND_ERROR_MSG))
        return List.of();
      throw ex;
    }
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

  private static void populateMarket(Map<Market, List<Order>> marketOrderMap) {
    marketOrderMap.forEach(OrderService::populateMarket);
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
