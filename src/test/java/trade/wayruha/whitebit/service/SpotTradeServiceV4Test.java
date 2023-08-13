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
  final Market market = Market.parse("WBT_USDT");
  final SpotTradeServiceV4 service = new SpotTradeServiceV4(config);
  final OrderService orderService = new OrderService(config);

  /** WARNING: real order is going to be created */
  @Ignore
  @Test
  public void test_createLimitOrder() {
    //create limit order
    final BigDecimal baseQty = new BigDecimal("4");
    final BigDecimal price = new BigDecimal("4.5");
    final NewOrderRequest orderRequest = NewOrderRequest.limitOrder(market, OrderSide.BUY, baseQty, price);
    Order order = service.createOrder(orderRequest);

    //assert correct response format
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.LIMIT, order.getType());

    // fetch it from Open Orders
    final OrderDetailsRequest orderQuery = OrderDetailsRequest.byId(order.getMarket(), order.getOrderId());
    order = orderService.getOpenOrders(orderQuery).get(0);
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.LIMIT, order.getType());

    // cancel order
    order = orderService.cancelOrder(new OrderID(market, order.getOrderId()));
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.LIMIT, order.getType());

    // ensure it's not in Open Orders anymore
    assertEquals(0, orderService.getOpenOrders(orderQuery).size());

    // check if it's present in orders history (cancelled orders are not visible in history)
    final Map<Market, List<Order>> ordersHistory = orderService.getOrdersHistory(OrderDetailsRequest.all());
    ordersHistory.forEach((k, v) -> v.forEach(TestUtils::validateOrder));

    // no deals are expected for this order
    final List<Deal> dealsHistory = orderService.getDealsHistory(new MarketFilter(market));
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
    assertEquals(0, orderService.getOpenOrders(orderQuery).size());

    // post reversed (sell) order
    orderRequest = NewOrderRequest.marketOrderBaseAsset(market, OrderSide.SELL, order.getFilledQty(), clientOrderId + "-reverse");
    order = service.createOrder(orderRequest);
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.MARKET_STOCK, order.getType());

    // ensure it's not in Open Orders anymore
    assertEquals(0, orderService.getOpenOrders(orderQuery).size());

    // check if it's present in orders history (cancelled orders are not visible in history)
    final Map<Market, List<Order>> ordersHistory = orderService.getOrdersHistory(OrderDetailsRequest.byClientOrderId(market, order.getClientOrderId()));
    assertEquals(1, ordersHistory.get(market).size());
    ordersHistory.forEach((k, v) -> v.forEach(TestUtils::validateOrder));

    // check deals this order
    final List<Deal> dealsHistory = orderService.getDealsHistory(new MarketFilter(market));
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
    order = orderService.getOpenOrders(orderQuery).get(0);
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.STOP_LIMIT, order.getType());

    // cancel order
    order = orderService.cancelOrder(new OrderID(market, order.getOrderId()));
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.STOP_LIMIT, order.getType());

    // ensure it's not in Open Orders anymore
    assertEquals(0, orderService.getOpenOrders(orderQuery).size());

    // check if it's present in orders history (cancelled orders are not visible in history)
    final Map<Market, List<Order>> ordersHistory = orderService.getOrdersHistory(OrderDetailsRequest.all());
    ordersHistory.forEach((k, v) -> v.forEach(TestUtils::validateOrder));

    // no deals are expected for this order
    final List<Deal> dealsHistory = orderService.getDealsHistory(new MarketFilter(market));
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
    Order order = orderService.cancelOrder(new OrderID(market, stopOrder.getOrderId()));
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.STOP_MARKET, order.getType());
  }
}
