package trade.wayruha.whitebit.service;

import org.junit.Test;
import trade.wayruha.whitebit.TestConstants;
import trade.wayruha.whitebit.TestUtils;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.Deal;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.domain.Order;
import trade.wayruha.whitebit.dto.request.DealsRequest;
import trade.wayruha.whitebit.dto.request.MarketFilter;
import trade.wayruha.whitebit.dto.request.OrderDealsRequest;
import trade.wayruha.whitebit.dto.request.OrderDetailsRequest;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class OrderServiceTest {
  final WBConfig config = TestConstants.getSimpleConfig();
  final Market market = Market.parse("WBT_USDT");
  final OrderService orderService = new OrderService(config);

  @Test
  public void test_getOrders() {
    Long existingOrderId = 259258397657L; // it should be your id

    // fetch it from Open Orders
    final OrderDetailsRequest orderQuery = OrderDetailsRequest.byMarket(market);
    List<Order> orders = orderService.getOpenOrders(orderQuery);
    assertNotNull(orders);
    orders.forEach(TestUtils::validateOrder);
    System.out.println("Open orders: " + orders);

    Map<Market, List<Order>> ordersHistory = orderService.getOrdersHistory(OrderDetailsRequest.all());
    System.out.println("OrdersHistory All: " + ordersHistory);
    ordersHistory.forEach((k, v) -> v.forEach(TestUtils::validateOrder));

    ordersHistory = orderService.getOrdersHistory(OrderDetailsRequest.byMarket(market));
    System.out.println("OrdersHistory by Market: " + ordersHistory);
    ordersHistory.forEach((k, v) -> v.forEach(TestUtils::validateOrder));

    ordersHistory = orderService.getOrdersHistory(OrderDetailsRequest.byMarket(new Market("QWERTY", "USDT")));
    System.out.println("OrdersHistory by non-existing Market: " + ordersHistory);
    ordersHistory.forEach((k, v) -> v.forEach(TestUtils::validateOrder));

    ordersHistory = orderService.getOrdersHistory(OrderDetailsRequest.builder().orderId(100L).limit(10).build());
    System.out.println("OrdersHistory by non-existing Id: " + ordersHistory);
    ordersHistory.forEach((k, v) -> v.forEach(TestUtils::validateOrder));

    if (existingOrderId != null) {
      ordersHistory = orderService.getOrdersHistory(OrderDetailsRequest.builder().orderId(existingOrderId).limit(10).build());
      System.out.println("OrdersHistory by existing Id: " + ordersHistory);
      ordersHistory.forEach((k, v) -> v.forEach(TestUtils::validateOrder));
    }

    String nonExistingClientOrderId = "test-id";
    assertThrows(Exception.class, () -> orderService.getOrdersHistory(OrderDetailsRequest.builder().clientOrderId(nonExistingClientOrderId).build()));
  }

  @Test
  public void test_getDeals() {
    final String yourClientOrderId = "test-id-27-reverse";
    final long yourOrderId = 266641212026L;
    List<Deal> dealsHistory = orderService.getDealsHistory(new MarketFilter(market));
    System.out.println("Deals by Market: " + dealsHistory);
    dealsHistory.forEach(TestUtils::validateDeal);

    Map<Market, List<Deal>> dealsMap = orderService.getDealsHistory(DealsRequest.byClientOrderId(yourClientOrderId));
    System.out.println("Deals by existing clientOrderId: " + dealsMap);
    dealsMap.forEach((k, v) -> v.forEach(TestUtils::validateDeal));

    //check querying for non-existing clientOrderId
    assertThrows(Exception.class, () -> orderService.getDealsHistory(DealsRequest.byClientOrderId("none")));

    dealsMap = orderService.getDealsHistory(DealsRequest.all());
    System.out.println("Deals All: " + dealsMap);
    dealsMap.forEach((k, v) -> v.forEach(TestUtils::validateDeal));

    final List<Deal> orderDeals = orderService.getOrderDeals(new OrderDealsRequest(yourOrderId)).getRecords();
    System.out.println("Deals by orderId: " + orderDeals);
    orderDeals.forEach(TestUtils::validateDeal);
  }
}
