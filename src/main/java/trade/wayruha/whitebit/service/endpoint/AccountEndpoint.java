package trade.wayruha.whitebit.service.endpoint;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import trade.wayruha.whitebit.APIConstant;
import trade.wayruha.whitebit.dto.ws.WSTokenWrapper;

public interface AccountEndpoint {
  @POST("/api/v4/profile/websocket_token")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<WSTokenWrapper> getWebSocketToken();
}
