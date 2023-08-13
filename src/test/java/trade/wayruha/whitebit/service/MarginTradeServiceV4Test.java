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

public class MarginTradeServiceV4Test {
  final WBConfig config = TestConstants.getSimpleConfig();
  final Market market = Market.parse("XRP_USDT");
  final MarginTradeServiceV4 marginService = new MarginTradeServiceV4(config);
  final OrderService orderService = new OrderService(config);

  /**
   * WARNING: real order is going to be created
   */
  @Ignore
  @Test
  public void test_createLimitOrder() {
    //create limit order
    final BigDecimal baseQty = new BigDecimal("10");
    final BigDecimal price = new BigDecimal("0.55");
    final NewOrderRequest orderRequest = NewOrderRequest.marginLimitOrder(market, OrderSide.BUY, baseQty, price);
    Order order = marginService.createOrder(orderRequest);

    //assert correct response format
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.MARGIN_LIMIT, order.getType());

    // fetch it from Open Orders
    final OrderDetailsRequest orderQuery = OrderDetailsRequest.byId(order.getMarket(), order.getOrderId());
    order = orderService.getOpenOrders(orderQuery).get(0);
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.MARGIN_LIMIT, order.getType());

    //check for open position
    final MarginPosition position = marginService.getOpenPositions(order.getMarket()).get(0);
    TestUtils.validateOpenPosition(position);
    assertNull(position.getBasePrice());
    assertNull(position.getLiquidationPrice());
    assertNull(position.getPnl());

    // cancel order
    order = orderService.cancelOrder(new OrderID(market, order.getOrderId()));
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.MARGIN_LIMIT, order.getType());

    // ensure it's not in Open Orders and positions anymore
    assertEquals(0, orderService.getOpenOrders(orderQuery).size());
    assertEquals(0, marginService.getOpenPositions(order.getMarket()).size());

    // check if it's present in orders history (cancelled orders are not visible in history)
    final Map<Market, List<Order>> ordersHistory = orderService.getOrdersHistory(OrderDetailsRequest.all());
    ordersHistory.forEach((k, v) -> v.forEach(TestUtils::validateOrder));
  }

  /**
   * WARNING: real order is going to be created
   */
  @Ignore
  @Test
  public void test_createStockMarketOrder() {
    //create market order
    final BigDecimal baseQty = new BigDecimal("10");
    final BigDecimal price = new BigDecimal("0.55");
    final String clientOrderId = "test-margin-" + (int) (Math.random() * 100);
    NewOrderRequest orderRequest = NewOrderRequest.marginMarketOrder(market, OrderSide.BUY, baseQty, clientOrderId);
    Order order = marginService.createOrder(orderRequest);

    //assert correct response format
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.MARGIN_MARKET, order.getType());

    // fetch it from Open Orders
    final OrderDetailsRequest orderQuery = OrderDetailsRequest.byClientOrderId(order.getMarket(), order.getClientOrderId());
    assertEquals(0, orderService.getOpenOrders(orderQuery).size());

    //check for open position
    final MarginPosition position = marginService.getOpenPositions(order.getMarket()).get(0);
    TestUtils.validateOpenPosition(position);
    TestUtils.validateOpenExecutedPosition(position);

    // post reversed (sell) order
    orderRequest = NewOrderRequest.marginMarketOrder(market, OrderSide.SELL, order.getFilledQty(), clientOrderId + "-reverse");
    order = marginService.createOrder(orderRequest);
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.MARGIN_MARKET, order.getType());

    // ensure it's not in Open Orders and positions anymore
    assertEquals(0, orderService.getOpenOrders(orderQuery).size());
    assertEquals(0, marginService.getOpenPositions(order.getMarket()).size());

    final List<MarginPosition> history = marginService.getPositionsHistory(PositionsRequest.byId(position.getPositionId()));
    history.forEach(TestUtils::validateClosedPosition);
    System.out.println("Position history: " + position);
  }

  @Ignore
  @Test
  public void test_createStopLimitOrder() {
    //create limit order
    final String clientOrderId = "test-margin-stop-" + (int) (Math.random() * 100);
    final BigDecimal baseQty = new BigDecimal("10");
    final BigDecimal price = new BigDecimal("0.55");
    final StopOrderRequest orderRequest = StopOrderRequest.marginStopLimitOrder(market, OrderSide.BUY, baseQty, price, price, clientOrderId);
    Order order = marginService.createStopOrder(orderRequest);

    //assert correct response format
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.MARGIN_STOP_LIMIT, order.getType());

    // fetch it from Open Orders
    final OrderDetailsRequest orderQuery = OrderDetailsRequest.byId(order.getMarket(), order.getOrderId());
    order = orderService.getOpenOrders(orderQuery).get(0);
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.MARGIN_STOP_LIMIT, order.getType());

    //check for open position
    final List<MarginPosition> positions = marginService.getOpenPositions(order.getMarket());
    assertEquals(0, positions.size());

    // cancel order
    order = orderService.cancelOrder(new OrderID(market, order.getOrderId()));
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.MARGIN_STOP_LIMIT, order.getType());

    // ensure it's not in Open Orders and positions anymore
    assertEquals(0, orderService.getOpenOrders(orderQuery).size());
    assertEquals(0, marginService.getOpenPositions(order.getMarket()).size());

    // check if it's present in orders history (cancelled orders are not visible in history)
    final Map<Market, List<Order>> ordersHistory = orderService.getOrdersHistory(OrderDetailsRequest.all());
    ordersHistory.forEach((k, v) -> v.forEach(TestUtils::validateOrder));
  }

  @Ignore
  @Test
  public void test_createStopMarketOrder() {
    //create limit order
    final String clientOrderId = "test-margin-stop-" + (int) (Math.random() * 100);
    final BigDecimal baseQty = new BigDecimal("10");
    final BigDecimal activationPrice = new BigDecimal("0.55");
    final StopOrderRequest orderRequest = StopOrderRequest.marginStopMarketOrder(market, OrderSide.BUY, baseQty, activationPrice, clientOrderId);
    StopOrder stopOrder = marginService.createStopOrder(orderRequest);

    //assert correct response format
    compareOrderWithRequested(orderRequest, stopOrder);
    assertEquals(activationPrice, stopOrder.getActivationPrice());
    assertEquals(ActivationCondition.LTE, stopOrder.getActivationCondition());
    assertEquals(OrderType.MARGIN_STOP_MARKET, stopOrder.getType());

    Order order = orderService.cancelOrder(new OrderID(market, stopOrder.getOrderId()));
    compareOrderWithRequested(orderRequest, order);
    assertEquals(OrderType.MARGIN_STOP_MARKET, order.getType());
  }

  @Test
  public void test_getOrders() {
    Long positionId = 2780276L; // it should be your id

    // fetch it from Open Orders
    List<MarginPosition> positions = marginService.getOpenPositions(market);
    System.out.println("Positions open: " + positions);
    positions.forEach(TestUtils::validateOpenPosition);

    positions = marginService.getPositionsHistory(PositionsRequest.byMarket(market));
    System.out.println("Positions history by market: " + positions);
    positions.forEach(TestUtils::validateClosedPosition);

    positions = marginService.getPositionsHistory(PositionsRequest.byId(positionId));
    System.out.println("Positions history by id: " + positions);
    positions.forEach(TestUtils::validateClosedPosition);
  }

  @Test
  public void test_changeLeverage() {
    final AccountLeverageInfo response = marginService.changeAccountLeverage(2);
    System.out.println("Leverage changed: " + response);
  }
}
