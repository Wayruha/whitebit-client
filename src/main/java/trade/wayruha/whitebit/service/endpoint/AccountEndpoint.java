package trade.wayruha.whitebit.service.endpoint;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import trade.wayruha.whitebit.APIConstant;
import trade.wayruha.whitebit.dto.*;
import trade.wayruha.whitebit.dto.request.*;
import trade.wayruha.whitebit.dto.ws.WSTokenWrapper;

import java.util.List;

public interface AccountEndpoint {
  @POST("/api/v4/profile/websocket_token")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<WSTokenWrapper> getWebSocketToken();

  // deposits

  /** rate limit: 1000 requests/10sec */
  @POST("/api/v4/main-account/address")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<DepositAddressResponse> getDepositAddress(@Body DepositAddressRequest request);

  /** rate limit: 1000 requests/10sec . Not available by default, please contact support@whitebit.com */
  @POST("/api/v4/main-account/create-new-address")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<DepositAddressResponse> createDepositAddress(@Body DepositAddressRequest request);

  /** rate limit: 1000 requests/10sec */
  @POST("/api/v4/main-account/address")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<FiatDepositAddress> getFiatDepositAddress(@Body FiatDepositAddressRequest request);

  // withdrawals
  /** rate limit: 100 requests/60sec */
  @POST("/api/v4/main-account/withdraw")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Void> withdraw(@Body WithdrawRequest request);

  /** rate limit: 100 requests/60sec */
  @POST("/api/v4/main-account/withdraw")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Void> withdrawFiat(@Body WithdrawFiatRequest request);

  /** rate limit: 1000 requests/10sec */
  @POST("/api/v4/main-account/withdraw-pay")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Void> withdrawFeeExcluded(@Body WithdrawRequest request);

  /** rate limit: 1000 requests/10sec */
  @POST("/api/v4/main-account/withdraw-pay")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Void> withdrawFiatFeeExcluded(@Body WithdrawFiatRequest request);

  /** rate limit: 100 requests/60sec */
  @POST("/api/v4/main-account/history")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<PageableResponse<TransactionRecord>> getWalletHistory(@Body TransactionHistoryRequest request);

  // fees structure

  /** rate limit: 1000 requests/60sec */
  @POST("/api/v4/main-account/fee")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<List<TransactionFees>> getFees();
}
