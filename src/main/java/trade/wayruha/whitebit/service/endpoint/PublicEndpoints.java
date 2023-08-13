package trade.wayruha.whitebit.service.endpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.dto.*;

import java.util.List;
import java.util.Map;

public interface PublicEndpoints {

    /** rate limit: 2000 requests/10sec */
    @GET("/api/v4/public/time")
    Call<Time> getServerTime();

    /** rate limit: 2000 requests/10sec */
    @GET("/api/v4/public/ping")
    Call<ValueWrapper<String>> getServerStatus();

    /** rate limit: 2000 requests/10 sec */
    @GET("/api/v4/public/fee")
    Call<Map<String, FeeInfo>> getFees();

    /** rate limit: 2000 requests/10 sec */
    @GET("/api/v4/public/assets")
    Call<Map<String, AssetInfo>> getAssets();

    /** rate limit: 2000 requests/10sec */
    @GET("/api/v4/public/trades/{market}")
    Call<List<Trade>> getTrades(@Path("market") Market symbol, @Query("type") String type);

    /** rate limit: 2000 requests/10sec */
    @GET("/api/v4/public/markets")
    Call<List<MarketInfo>> getMarkets();

    /** rate limit: 600 requests/10sec */
    @GET("/api/v4/public/orderbook/{market}")
    Call<OrderBook> getOrderBook(@Path("market") Market symbol, @Query("limit") Integer depth, @Query("level") Integer aggregationLevel);

    /** rate limit: 2000 requests/10sec */
    @GET("/api/v4/public/collateral/markets")
    Call<V1Response<List<Market>>> getCollateralMarkets();

    /** rate limit: 2000 requests/10sec */
    @GET("/api/v4/public/futures")
    Call<V1Response<List<FuturesMarket>>> getFuturesMarkets();

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
