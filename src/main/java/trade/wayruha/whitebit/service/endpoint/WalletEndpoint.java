package trade.wayruha.whitebit.service.endpoint;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import trade.wayruha.whitebit.client.APIConstant;
import trade.wayruha.whitebit.domain.AssetBalance;
import trade.wayruha.whitebit.dto.WBRequest;
import trade.wayruha.whitebit.dto.request.TickerParam;

import java.util.Map;

public interface WalletEndpoint {
  String GET_TRADE_BALANCE_PATH = "/api/v4/trade-account/balance";

  /**
   * rate limit: 12000 requests/10sec
   */
  @POST(GET_TRADE_BALANCE_PATH)
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Map<String, AssetBalance>> getTradeBalance(@Body WBRequest<TickerParam> order);

}
