package trade.wayruha.whitebit.service.endpoint;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import trade.wayruha.whitebit.client.APIConstant;
import trade.wayruha.whitebit.dto.request.OrderRequest;
import trade.wayruha.whitebit.dto.response.OrderResponse;

public interface TradeEndpoint {

  /**
   * rate limit: 10000 requests/10sec
   */
  @POST("/api/v4/order/new")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<OrderResponse> createOrder(@Body OrderRequest order);
}
