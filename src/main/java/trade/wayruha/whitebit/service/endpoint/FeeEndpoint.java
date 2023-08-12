package trade.wayruha.whitebit.service.endpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import trade.wayruha.whitebit.dto.Fee;

import java.util.Map;

public interface FeeEndpoint {
    /** rate limit: 2000 requests/10 sec */
    @GET("/api/v4/public/fee")
    Call<Map<String, Fee>> getFees();
}
