package trade.wayruha.whitebit.service.endpoint;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import trade.wayruha.whitebit.APIConstant;
import trade.wayruha.whitebit.domain.OCOOrder;
import trade.wayruha.whitebit.domain.Order;
import trade.wayruha.whitebit.domain.OrderID;
import trade.wayruha.whitebit.domain.StopOrder;
import trade.wayruha.whitebit.dto.KillSwitchStatus;
import trade.wayruha.whitebit.dto.OrderCreationStatus;
import trade.wayruha.whitebit.dto.request.*;

import java.util.List;

public interface TradeEndpoint {
  // rate limit: 10000 requests/10sec
  @POST("/api/v4/order/new")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Order> createLimitOrder(@Body NewOrderRequest order);

  // rate limit: 10000 requests/10sec
  @POST("/api/v4/order/market")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Order> createMarketOrder(@Body NewOrderRequest order);

  // rate limit: 10000 requests/10sec
  @POST("/api/v4/order/stock_market")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Order> createStockMarketOrder(@Body NewOrderRequest order);

  // rate limit: 10000 requests/10sec
  @POST("/api/v4/order/stop_limit")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<StopOrder> createStopLimitOrder(@Body StopOrderRequest order);

  // rate limit: 10000 requests/10sec
  @POST("/api/v4/order/stop_market")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<StopOrder> createStopMarketOrder(@Body StopOrderRequest order);

  // rate limit: 10000 requests/10sec
  @POST(" /api/v4/order/bulk")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<List<OrderCreationStatus>> createBulkLimitOrders(@Body BulkOrdersCreation order);

  // Margin
  // rate limit: 10000 requests/10sec
  @POST("/api/v4/order/collateral/limit")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Order> createMarginLimitOrder(@Body NewOrderRequest order);

  // rate limit: 10000 requests/10sec
  @POST("/api/v4/order/collateral/market")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Order> createMarginMarketOrder(@Body NewOrderRequest order);

  // rate limit: 10000 requests/10sec
  @POST("/api/v4/order/collateral/stop-limit")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<StopOrder> createMarginStopLimitOrder(@Body StopOrderRequest order);

  // rate limit: 10000 requests/10sec
  @POST("/api/v4/order/collateral/trigger-market")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<StopOrder> createMarginStopMarketOrder(@Body StopOrderRequest order);

  // rate limit: 12000 requests/10sec
  @POST("/api/v4/collateral-account/leverage")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<AccountLeverageInfo> changeAccountLeverage(@Body AccountLeverageInfo leverage);

  // OCO

  // rate limit: 10000 requests/10sec
  @POST("/api/v4/order/collateral/oco")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<OCOOrder> createOCOOrder(@Body OCORequest order);


  // rate limit: 1000 requests/10sec
  @POST("/api/v4/oco-orders")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<List<OCOOrder>> getOpenOCOOrders(@Body OrderDetailsRequest order);

  // rate limit: 10000 requests/10sec
  @POST("/api/v4/order/oco-cancel")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<OCOOrder> cancelOCOOrder(@Body OrderID order);

  // Kill-switch

  // rate limit: 10000 requests/10sec
  @POST("/api/v4/order/kill-switch")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<KillSwitchStatus> createKillSwitch(@Body KillSwitchRequest request);

  // rate limit: 10000 requests/10sec
  @POST("/api/v4/order/kill-switch/status")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<List<KillSwitchStatus>> getKillSwitchStatus(@Body MarketParameter order);
}
