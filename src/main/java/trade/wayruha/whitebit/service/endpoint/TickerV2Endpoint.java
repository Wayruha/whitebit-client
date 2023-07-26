package trade.wayruha.whitebit.service.endpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import trade.wayruha.whitebit.dto.*;

import java.util.List;
import java.util.Map;

public interface TickerV2Endpoint {
    @GET("/api/v2/public/ticker")
    Call<V1Response<List<MarketActivity>>> getAvailableTickers();
}
