package trade.wayruha.whitebit.service.endpoint;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import trade.wayruha.whitebit.client.APIConstant;
import trade.wayruha.whitebit.dto.WBRequest;
import trade.wayruha.whitebit.dto.request.OrderRequest;
import trade.wayruha.whitebit.dto.response.OrderResponse;

public interface TradeEndpoint {
  String CREATE_ORDER_URL = "/api/v4/order/new";

  /**
   * rate limit: 10000 requests/10sec
   */
  @POST(CREATE_ORDER_URL)
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<OrderResponse> createOrder(@Body WBRequest<OrderRequest> order);
}
