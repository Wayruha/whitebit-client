package trade.wayruha.whitebit.service.endpoint;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import trade.wayruha.whitebit.APIConstant;
import trade.wayruha.whitebit.domain.AssetBalance;
import trade.wayruha.whitebit.domain.MarginAssetBalance;
import trade.wayruha.whitebit.dto.CollateralSummary;
import trade.wayruha.whitebit.dto.request.TickerParameter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface WalletEndpoint {
  /** rate limit: 12000 requests/10sec */
  @POST("/api/v4/trade-account/balance")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Map<String, AssetBalance>> getTradeBalances();

  /** rate limit: 12000 requests/10sec */
  @POST("/api/v4/trade-account/balance")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<AssetBalance> getTradeBalance(@Body TickerParameter req);

  /** rate limit: 100 requests/10sec */
  @POST("/api/v4/collateral-account/balance")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Map<String, BigDecimal>> getCollateralAvailableBalance(@Body TickerParameter asset);

  /** rate limit: 100 requests/10sec */
  @POST("/api/v4/collateral-account/balance")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Map<String, BigDecimal>> getCollateralAvailableBalances();

  /** rate limit: 100 requests/10sec */
  @POST("/api/v4/collateral-account/balance-summary")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<List<MarginAssetBalance>> getCollateralBalance(@Body TickerParameter asset);

  /** rate limit: 12000 requests/10sec */
  @POST("/api/v4/collateral-account/summary")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<CollateralSummary> getCollateralSummary();

}
