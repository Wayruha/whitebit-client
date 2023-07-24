package trade.wayruha.whitebit.service.endpoint;

import com.fasterxml.jackson.databind.JsonNode;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import trade.wayruha.whitebit.APIConstant;
import trade.wayruha.whitebit.domain.*;
import trade.wayruha.whitebit.dto.KillSwitchStatus;
import trade.wayruha.whitebit.dto.OrderCreationStatus;
import trade.wayruha.whitebit.dto.OrderDealsResponse;
import trade.wayruha.whitebit.dto.request.*;
import trade.wayruha.whitebit.domain.Order;

import java.util.List;

public interface SpotTradeEndpoint {
  /**  rate limit: 10000 requests/10sec */
  @POST("/api/v4/order/new")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Order> createLimitOrder(@Body NewOrderRequest order);

  /**  rate limit: 10000 requests/10sec */
  @POST("/api/v4/order/market")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Order> createMarketOrder(@Body NewOrderRequest order);

  /**  rate limit: 10000 requests/10sec */
  @POST("/api/v4/order/stock_market")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Order> createStockMarketOrder(@Body NewOrderRequest order);

  /**  rate limit: 10000 requests/10sec */
  @POST("/api/v4/order/stop_limit")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Order> createStopLimitOrder(@Body NewOrderRequest order);

  /**  rate limit: 10000 requests/10sec */
  @POST("/api/v4/order/stop_market")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Order> createStopMarketOrder(@Body NewOrderRequest order);

  /**  rate limit: 10000 requests/10sec */
  @POST(" /api/v4/order/bulk")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<List<OrderCreationStatus>> createBulkLimitOrders(@Body BulkOrdersCreation order);

  /**  rate limit: 10000 requests/10sec */
  @POST("/api/v4/order/cancel")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Order> cancelOrder(@Body OrderID order);

  /**  rate limit: 10000 requests/10sec */
  @POST("/api/v4/orders")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<List<Order>> getActiveOrders(@Body OrderDetailsRequest order);

  /**  rate limit: 12000 requests/10sec */
  @POST("/api/v4/trade-account/order/history")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<JsonNode> getExecutedOrders(@Body OrderDetailsRequest order);

  /**  rate limit: 12000 requests/10sec */
  @POST("/api/v4/trade-account/executed-history")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<List<Deal>> dealsHistory(@Body MarketFilter req);

  @POST("/api/v4/trade-account/executed-history")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<JsonNode> dealsHistory(@Body DealsRequest req);

  /**  rate limit: 12000 requests/10sec */
  @POST("/api/v4/trade-account/order")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<OrderDealsResponse> getOrderDeals(@Body OrderDealsRequest order);

  // Kill-switch

  /**  rate limit: 10000 requests/10sec */
  @POST("/api/v4/order/kill-switch")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<KillSwitchStatus> createKillSwitch(@Body KillSwitchRequest request);

  /**  rate limit: 10000 requests/10sec */
  @POST("/api/v4/order/kill-switch/status")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<List<KillSwitchStatus>> getKillSwitchStatus(@Body MarketParameter order);
}
