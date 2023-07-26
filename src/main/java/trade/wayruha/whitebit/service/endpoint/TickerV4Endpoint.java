package trade.wayruha.whitebit.service.endpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import trade.wayruha.whitebit.dto.*;

import java.util.Map;

public interface TickerV4Endpoint {
    @GET("/api/v4/public/ticker")
    Call<Map<String, AvailableTicker>> getAvailableTickers();
}
