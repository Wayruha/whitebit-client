package trade.wayruha.whitebit.service.endpoint;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import trade.wayruha.whitebit.client.APIConstant;
import trade.wayruha.whitebit.domain.AssetBalance;
import trade.wayruha.whitebit.dto.request.TickerParam;

import java.util.Map;

public interface WalletEndpoint {
  /**
   * rate limit: 12000 requests/10sec
   */
  @POST("/api/v4/trade-account/balance")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Map<String, AssetBalance>> getTradeBalances();

  /**
   * rate limit: 12000 requests/10sec
   */
  @POST("/api/v4/trade-account/balance")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<AssetBalance> getTradeBalance(@Body TickerParam req);

}
