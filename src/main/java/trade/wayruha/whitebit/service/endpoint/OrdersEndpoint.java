package trade.wayruha.whitebit.service.endpoint;

import com.fasterxml.jackson.databind.JsonNode;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import trade.wayruha.whitebit.APIConstant;
import trade.wayruha.whitebit.domain.Deal;
import trade.wayruha.whitebit.domain.MarginPosition;
import trade.wayruha.whitebit.domain.Order;
import trade.wayruha.whitebit.domain.OrderID;
import trade.wayruha.whitebit.dto.OrderDealsResponse;
import trade.wayruha.whitebit.dto.request.*;

import java.util.List;

public interface OrdersEndpoint {
  // rate limit: 10000 requests/10sec
  @POST("/api/v4/order/cancel")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Order> cancelOrder(@Body OrderID order);

  // rate limit: 10000 requests/10sec
  @POST("/api/v4/orders")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<List<Order>> getActiveOrders(@Body OrderDetailsRequest order);

  // rate limit: 12000 requests/10sec
  @POST("/api/v4/trade-account/order/history")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<JsonNode> getExecutedOrders(@Body OrderDetailsRequest order);

  // rate limit: 12000 requests/10sec
  @POST("/api/v4/trade-account/executed-history")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<List<Deal>> dealsHistory(@Body MarketFilter req);

  @POST("/api/v4/trade-account/executed-history")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<JsonNode> dealsHistory(@Body DealsRequest req);

  // rate limit: 12000 requests/10sec
  @POST("/api/v4/trade-account/order")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<OrderDealsResponse> getOrderDeals(@Body OrderDealsRequest order);

  // margin

  // rate limit: 12000 requests/10sec
  @POST("/api/v4/collateral-account/positions/open")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<List<MarginPosition>> getOpenCollateralPositions(@Body MarketParameter request);

  /**  rate limit: 12000 requests/10sec */
  @POST("/api/v4/collateral-account/positions/history")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<List<MarginPosition>> getCollateralPositionsHistory(@Body PositionsRequest request);
}
