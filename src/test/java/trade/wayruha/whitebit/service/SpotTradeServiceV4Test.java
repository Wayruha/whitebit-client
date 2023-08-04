package trade.wayruha.whitebit.service;

import org.junit.Ignore;
import org.junit.Test;
import trade.wayruha.whitebit.TestConstants;
import trade.wayruha.whitebit.TestUtils;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.*;
import trade.wayruha.whitebit.domain.enums.ActivationCondition;
import trade.wayruha.whitebit.domain.enums.OrderSide;
import trade.wayruha.whitebit.domain.enums.OrderType;
import trade.wayruha.whitebit.dto.request.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static trade.wayruha.whitebit.TestUtils.compareOrderWithRequested;

public class SpotTradeServiceV4Test {
  final WBConfig config = TestConstants.getSimpleConfig();
  final String usdt = "USDT";
  final String wbt = "WBT";
  final String eth = "ETH";
  final Market market = Market.parse("WBT_USDT");
  final SpotTradeServiceV4 service = new SpotTradeServiceV4(config);

  /** WARNING: real order is going to be created */
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
    ordersHistory.forEach((k, v) -> v.forEach(TestUtils::validateOrder));

    // no deals are expected for this order
    final List<Deal> dealsHistory = service.getDealsHistory(new MarketFilter(market));
    System.out.println("Deals: " + dealsHistory);
  }

  /** WARNING: real order is going to be created */
  @Ignore
  @Test
  public void test_createStockMarketOrder() {
    //create market order
    final BigDecimal baseQty = new BigDecimal("2");
    final String clientOrderId = "test-id-" + (int) (Math.random() * 100);
    NewOrderRequest orderRequest = NewOrderRequest.marketOrderBaseAsset(market, OrderSide.BUY, baseQty, clientOrderId);
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
    ordersHistory.forEach((k, v) -> v.forEach(TestUtils::validateOrder));

    // check deals this order
    final List<Deal> dealsHistory = service.getDealsHistory(new MarketFilter(market));
    System.out.println("Deals: " + dealsHistory);
    assertTrue(dealsHistory.size() > 0);
  }

  /** WARNING: real order is going to be created */
  @Ignore
  @Test
  public void test_createStopLimitOrder() {
    //create limit order
    final BigDecimal baseQty = new BigDecimal("4");
    final BigDecimal price = new BigDecimal("4.6");
    final String clientOrderId = "test-stop-limit-" + (int) (Math.random() * 100);
    final StopOrderRequest orderRequest = StopOrderRequest.spotStopLimitOrder(market, OrderSide.BUY, baseQty, price, price, clientOrderId);
    Order order = service.createStopOrder(orderRequest);

    //assert correct response format
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.STOP_LIMIT, order.getType());

    // fetch it from Open Orders
    final OrderDetailsRequest orderQuery = OrderDetailsRequest.byId(order.getMarket(), order.getOrderId());
    order = service.getOpenOrders(orderQuery).get(0);
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.STOP_LIMIT, order.getType());

    // cancel order
    order = service.cancelOrder(new OrderID(market, order.getOrderId()));
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.STOP_LIMIT, order.getType());

    // ensure it's not in Open Orders anymore
    assertEquals(0, service.getOpenOrders(orderQuery).size());

    // check if it's present in orders history (cancelled orders are not visible in history)
    final Map<Market, List<Order>> ordersHistory = service.getOrdersHistory(OrderDetailsRequest.all());
    ordersHistory.forEach((k, v) -> v.forEach(TestUtils::validateOrder));

    // no deals are expected for this order
    final List<Deal> dealsHistory = service.getDealsHistory(new MarketFilter(market));
    System.out.println("Deals: " + dealsHistory);
  }

  /** WARNING: real order is going to be created */
  @Ignore
  @Test
  public void test_createStopMarketOrder() {
    //create market order
    final BigDecimal amount = new BigDecimal("6"); // quoteQty for BUY
    final BigDecimal activationPrice = new BigDecimal("10");
    final String clientOrderId = "test-id-" + (int) (Math.random() * 100);
    final StopOrderRequest orderRequest = StopOrderRequest.spotStopMarketOrder(market, OrderSide.BUY, amount, activationPrice, clientOrderId);
    StopOrder stopOrder = service.createStopOrder(orderRequest);

    //assert correct response format
    compareOrderWithRequested(orderRequest, stopOrder);
    assertEquals(activationPrice, stopOrder.getActivationPrice());
    assertEquals(ActivationCondition.GTE, stopOrder.getActivationCondition());
    assertEquals(OrderType.STOP_MARKET, stopOrder.getType());

    //cancel
    Order order = service.cancelOrder(new OrderID(market, stopOrder.getOrderId()));
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.STOP_MARKET, order.getType());
  }

  @Test
  public void test_getOrders() {
    Long existingOrderId = 259258397657L; // it should be your id

    // fetch it from Open Orders
    final OrderDetailsRequest orderQuery = OrderDetailsRequest.byMarket(market);
    List<Order> orders = service.getOpenOrders(orderQuery);
    assertNotNull(orders);
    orders.forEach(TestUtils::validateOrder);
    System.out.println("Open orders: " + orders);

    Map<Market, List<Order>> ordersHistory = service.getOrdersHistory(OrderDetailsRequest.all());
    System.out.println("OrdersHistory All: " + ordersHistory);
    ordersHistory.forEach((k, v) -> v.forEach(TestUtils::validateOrder));

    ordersHistory = service.getOrdersHistory(OrderDetailsRequest.byMarket(market));
    System.out.println("OrdersHistory by Market: " + ordersHistory);
    ordersHistory.forEach((k, v) -> v.forEach(TestUtils::validateOrder));

    ordersHistory = service.getOrdersHistory(OrderDetailsRequest.byMarket(new Market("QWERTY", "USDT")));
    System.out.println("OrdersHistory by non-existing Market: " + ordersHistory);
    ordersHistory.forEach((k, v) -> v.forEach(TestUtils::validateOrder));

    ordersHistory = service.getOrdersHistory(OrderDetailsRequest.builder().orderId(100L).limit(10).build());
    System.out.println("OrdersHistory by non-existing Id: " + ordersHistory);
    ordersHistory.forEach((k, v) -> v.forEach(TestUtils::validateOrder));

    if (existingOrderId != null) {
      ordersHistory = service.getOrdersHistory(OrderDetailsRequest.builder().orderId(existingOrderId).limit(10).build());
      System.out.println("OrdersHistory by existing Id: " + ordersHistory);
      ordersHistory.forEach((k, v) -> v.forEach(TestUtils::validateOrder));
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
    dealsHistory.forEach(TestUtils::validateDeal);

    Map<Market, List<Deal>> dealsMap = service.getDealsHistory(DealsRequest.byClientOrderId(yourClientOrderId));
    System.out.println("Deals by existing clientOrderId: " + dealsMap);
    dealsMap.forEach((k, v) -> v.forEach(TestUtils::validateDeal));

    //check querying for non-existing clientOrderId
    assertThrows(Exception.class, () -> service.getDealsHistory(DealsRequest.byClientOrderId("none")));

    dealsMap = service.getDealsHistory(DealsRequest.all());
    System.out.println("Deals All: " + dealsMap);
    dealsMap.forEach((k, v) -> v.forEach(TestUtils::validateDeal));

    final List<Deal> orderDeals = service.getOrderDeals(new OrderDealsRequest(yourOrderId)).getRecords();
    System.out.println("Deals by orderId: " + orderDeals);
    orderDeals.forEach(TestUtils::validateDeal);
  }
}
