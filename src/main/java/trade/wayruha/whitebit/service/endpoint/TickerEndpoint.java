package trade.wayruha.whitebit.service.endpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import trade.wayruha.whitebit.dto.*;

import java.util.List;
import java.util.Map;

public interface TickerEndpoint {
    @GET("/api/v1/public/ticker")
    Call<V1Response<SingleMarketActivity>> getAvailableTicker(@Query("market") String symbol);

    @GET("/api/v1/public/tickers")
    Call<V1Response<Map<String, SingleTicker>>> getAvailableTickers();
}
