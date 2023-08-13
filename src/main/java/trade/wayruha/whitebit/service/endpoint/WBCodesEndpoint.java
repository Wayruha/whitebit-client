package trade.wayruha.whitebit.service.endpoint;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import trade.wayruha.whitebit.APIConstant;
import trade.wayruha.whitebit.dto.PageableResponse;
import trade.wayruha.whitebit.dto.request.Pageable;
import trade.wayruha.whitebit.dto.wbcode.WBCode;
import trade.wayruha.whitebit.dto.wbcode.WBCodeRecord;
import trade.wayruha.whitebit.dto.wbcode.WBCodeResponse;
import trade.wayruha.whitebit.dto.wbcode.WBCodeRequest;

public interface WBCodesEndpoint {
  /** rate limit: 1000 requests/10sec */
  @POST("/api/v4/main-account/codes")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<WBCodeResponse> createCode(@Body WBCodeRequest request);

  /** rate limit: 1000 requests/10sec */
  @POST("/api/v4/main-account/codes/apply")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<WBCodeResponse> applyCode(@Body WBCode request);

  /** rate limit: 1000 requests/10sec */
  @POST("/api/v4/main-account/codes/my")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<PageableResponse<WBCodeRecord>> getMyCodes(@Body Pageable request);

  /** rate limit: 1000 requests/10sec */
  @POST("/api/v4/main-account/codes/my")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<PageableResponse<WBCodeRecord>> getCodesHistory(@Body Pageable request);
}
