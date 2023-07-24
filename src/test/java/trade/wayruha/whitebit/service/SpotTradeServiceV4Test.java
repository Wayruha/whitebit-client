package trade.wayruha.whitebit.service;

import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import trade.wayruha.whitebit.TestConstants;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.Deal;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.domain.Order;
import trade.wayruha.whitebit.domain.OrderID;
import trade.wayruha.whitebit.domain.enums.OrderSide;
import trade.wayruha.whitebit.domain.enums.OrderType;
import trade.wayruha.whitebit.dto.request.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SpotTradeServiceV4Test {
  final WBConfig config = TestConstants.getSimpleConfig();
  final String usdt = "USDT";
  final String wbt = "WBT";
  final String eth = "ETH";
  final Market market = Market.parse("WBT_USDT");
  final SpotTradeServiceV4 service = new SpotTradeServiceV4(config);

  /**
   * WARNING: real order is going to be created
   */
  @Ignore
  @Test
  public void test_createLimitOrder() {
    //create limit order
    final BigDecimal baseQty = new BigDecimal("4");
    final BigDecimal price = new BigDecimal("2");
    final NewOrderRequest orderRequest = NewOrderRequest.limitOrder(market, OrderSide.BUY, baseQty, price);
    Order order = service.createOrder(orderRequest);

    //assert correct response format
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.LIMIT, order.getType());

    // fetch it from Open Orders
    final OrderDetailsRequest orderQuery = OrderDetailsRequest.byId(order.getMarket(), order.getOrderId());
    order = service.getOpenOrders(orderQuery).get(0);
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.LIMIT, order.getType());

    // cancel order
    order = service.cancelOrder(new OrderID(market, order.getOrderId()));
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.LIMIT, order.getType());

    // ensure it's not in Open Orders anymore
    assertEquals(0, service.getOpenOrders(orderQuery).size());

    // check if it's present in orders history (cancelled orders are not visible in history)
    final Map<Market, List<Order>> ordersHistory = service.getOrdersHistory(OrderDetailsRequest.all());
    ordersHistory.forEach((k, v) -> v.forEach(SpotTradeServiceV4Test::validateOrder));

    // no deals are expected for this order
    final List<Deal> dealsHistory = service.getDealsHistory(new MarketFilter(market));
    System.out.println("Deals: " + dealsHistory);
  }

  /**
   * WARNING: real order is going to be created
   */
  @Ignore
  @Test
  public void test_createStockMarketOrder() {
    //create limit order
    final BigDecimal baseQty = new BigDecimal("2");
    final String clientOrderId = "test-id-" + (int) (Math.random() * 100);
    NewOrderRequest orderRequest = NewOrderRequest.marketOrderBaseAsset(market, OrderSide.SELL, baseQty, clientOrderId);
    Order order = service.createOrder(orderRequest);

    //assert correct response format
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.MARKET_STOCK, order.getType());

    // fetch it from Open Orders
    final OrderDetailsRequest orderQuery = OrderDetailsRequest.byClientOrderId(order.getMarket(), order.getClientOrderId());
    assertEquals(0, service.getOpenOrders(orderQuery).size());

    // post reversed (sell) order
    orderRequest = NewOrderRequest.marketOrderBaseAsset(market, OrderSide.SELL, order.getFilledQty(), clientOrderId + "-reverse");
    order = service.createOrder(orderRequest);
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.MARKET_STOCK, order.getType());

    // ensure it's not in Open Orders anymore
    assertEquals(0, service.getOpenOrders(orderQuery).size());

    // check if it's present in orders history (cancelled orders are not visible in history)
    final Map<Market, List<Order>> ordersHistory = service.getOrdersHistory(OrderDetailsRequest.byClientOrderId(market, order.getClientOrderId()));
    assertEquals(1, ordersHistory.get(market).size());
    ordersHistory.forEach((k, v) -> v.forEach(SpotTradeServiceV4Test::validateOrder));

    // check deals this order
    final List<Deal> dealsHistory = service.getDealsHistory(new MarketFilter(market));
    System.out.println("Deals: " + dealsHistory);
    assertTrue(dealsHistory.size() > 0);
  }

  @Test
  public void test_getOrders() {
    Long existingOrderId = 259258397657L; // it should be your id

    // fetch it from Open Orders
    final OrderDetailsRequest orderQuery = OrderDetailsRequest.byMarket(market);
    List<Order> orders = service.getOpenOrders(orderQuery);
    assertNotNull(orders);
    orders.forEach(SpotTradeServiceV4Test::validateOrder);
    System.out.println("Open orders: " + orders);

    Map<Market, List<Order>> ordersHistory = service.getOrdersHistory(OrderDetailsRequest.all());
    System.out.println("OrdersHistory All: " + ordersHistory);
    ordersHistory.forEach((k, v) -> v.forEach(SpotTradeServiceV4Test::validateOrder));

    ordersHistory = service.getOrdersHistory(OrderDetailsRequest.byMarket(market));
    System.out.println("OrdersHistory by Market: " + ordersHistory);
    ordersHistory.forEach((k, v) -> v.forEach(SpotTradeServiceV4Test::validateOrder));

    ordersHistory = service.getOrdersHistory(OrderDetailsRequest.byMarket(new Market("QWERTY", "USDT")));
    System.out.println("OrdersHistory by non-existing Market: " + ordersHistory);
    ordersHistory.forEach((k, v) -> v.forEach(SpotTradeServiceV4Test::validateOrder));

    ordersHistory = service.getOrdersHistory(OrderDetailsRequest.builder().orderId(100L).limit(10).build());
    System.out.println("OrdersHistory by non-existing Id: " + ordersHistory);
    ordersHistory.forEach((k, v) -> v.forEach(SpotTradeServiceV4Test::validateOrder));

    if (existingOrderId != null) {
      ordersHistory = service.getOrdersHistory(OrderDetailsRequest.builder().orderId(existingOrderId).limit(10).build());
      System.out.println("OrdersHistory by existing Id: " + ordersHistory);
      ordersHistory.forEach((k, v) -> v.forEach(SpotTradeServiceV4Test::validateOrder));
    }

    String nonExistingClientOrderId = "test-id";
    assertThrows(Exception.class, () -> service.getOrdersHistory(OrderDetailsRequest.builder().clientOrderId(nonExistingClientOrderId).build()));
  }

  @Test
  public void test_getDeals() {
    final String yourClientOrderId = "test-id-27-reverse";
    final long yourOrderId = 266641212026L;
    List<Deal> dealsHistory = service.getDealsHistory(new MarketFilter(market));
    System.out.println("Deals by Market: " + dealsHistory);
    dealsHistory.forEach(SpotTradeServiceV4Test::validateDeal);

    Map<Market, List<Deal>> dealsMap = service.getDealsHistory(DealsRequest.byClientOrderId(yourClientOrderId));
    System.out.println("Deals by existing clientOrderId: " + dealsMap);
    dealsMap.forEach((k, v) -> v.forEach(SpotTradeServiceV4Test::validateDeal));

    //check querying for non-existing clientOrderId
    assertThrows(Exception.class, () -> service.getDealsHistory(DealsRequest.byClientOrderId("none")));

    dealsMap = service.getDealsHistory(DealsRequest.all());
    System.out.println("Deals All: " + dealsMap);
    dealsMap.forEach((k, v) -> v.forEach(SpotTradeServiceV4Test::validateDeal));

    final List<Deal> orderDeals = service.getOrderDeals(new OrderDealsRequest(yourOrderId)).getRecords();
    System.out.println("Deals by orderId: " + orderDeals);
    orderDeals.forEach(SpotTradeServiceV4Test::validateDeal);
  }

  private static void validateDeal(Deal deal) {
    assertTrue(deal.getId() > 0);
    assertTrue(deal.getDealOrderId() > 0);
    assertTrue(deal.getTime() > 0);
    assertNotNull(deal.getPrice());
    assertNotNull(deal.getRole());
    assertNotNull(deal.getAmount());
    assertNotNull(deal.getTotal());
    assertNotNull(deal.getFee());
  }

  private static void validateOrder(Order order) {
    assertNotNull(order);
    assertNotNull(order.getOrderId());
    assertNotNull(order.getMarket());
    assertNotNull(order.getSide());
    assertNotNull(order.getPrice());
    assertNotNull(order.getAmount());
    assertNotNull(order.getFilledQty());
    assertNotNull(order.getFilledTotal());
    assertNotNull(order.getQuoteFee());
    assertNotNull(order.getTakerFeeRatio());
    assertNotNull(order.getMakerFeeRatio());
    assertTrue(order.getTimestamp() > 0);
  }

  private static void compareOrderWithRequested(NewOrderRequest orderRequest, Order actual) {
    assertNotNull(actual);
    assertNotNull(actual.getOrderId());
    assertEquals(orderRequest.getSide(), actual.getSide());
    assertEquals(orderRequest.getPrice(), actual.getPrice());
    assertEquals(orderRequest.getBaseQty(), actual.getAmount());
    assertNotNull(actual.getAmountLeft());
    assertNotNull(actual.getFilledQty());
    assertNotNull(actual.getFilledTotal());
    assertNotNull(actual.getQuoteFee());
    assertNotNull(actual.getTakerFeeRatio());
    assertNotNull(actual.getMakerFeeRatio());
    assertEquals(Boolean.TRUE.equals(orderRequest.getPostOnly()), actual.getPostOnly());
    assertEquals(Boolean.TRUE.equals(orderRequest.getIoc()), actual.getIoc());
    assertTrue(orderRequest.getClientOrderId().equals(actual.getClientOrderId()) || StringUtils.isBlank(actual.getClientOrderId()));
    assertNotNull(actual.getTimestamp());
  }
}
