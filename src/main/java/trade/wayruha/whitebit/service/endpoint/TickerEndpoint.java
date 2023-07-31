package trade.wayruha.whitebit.service.endpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.dto.*;

import java.util.List;
import java.util.Map;

//TODO do we need v1 and v2?
public interface TickerEndpoint {
    //V1
    @GET("/api/v1/public/ticker")
    Call<V1Response<MarketActivityV1.Ticker>> getAvailableTickerV1(@Query("market") Market symbol);

    @GET("/api/v1/public/tickers")
    Call<V1Response<Map<Market, MarketActivityV1>>> getAvailableTickersV1();

    //V2
    @GET("/api/v2/public/ticker")
    Call<V1Response<List<MarketActivity>>> getAvailableTickersV2();

    //V4
    @GET("/api/v4/public/ticker")
    Call<Map<Market, MarketActivityV4>> getAvailableTickersV4();
}
