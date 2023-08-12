package trade.wayruha.whitebit.service.endpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.dto.Asset;
import trade.wayruha.whitebit.dto.Fee;
import trade.wayruha.whitebit.dto.Trade;

import java.util.List;
import java.util.Map;

public interface PublicEndpoints {

    /** rate limit: 2000 requests/10 sec */
    @GET("/api/v4/public/fee")
    Call<Map<String, Fee>> getFees();

    /** rate limit: 2000 requests/10 sec */
    @GET("/api/v4/public/assets")
    Call<Map<String, Asset>> getAssets();

    /** rate limit: 2000 requests/10sec */
    @GET("/api/v4/public/trades/{market}")
    Call<List<Trade>> getTrades(@Path("market") Market symbol, @Query("type") String type);
}
