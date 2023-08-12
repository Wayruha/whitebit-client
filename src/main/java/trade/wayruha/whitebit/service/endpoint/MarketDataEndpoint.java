package trade.wayruha.whitebit.service.endpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.dto.Time;
import trade.wayruha.whitebit.dto.ValueWrapper;
import trade.wayruha.whitebit.dto.MarketInfo;
import trade.wayruha.whitebit.dto.OrderBook;

import java.util.List;

public interface MarketDataEndpoint {

  /**
   * rate limit: 2000 requests/10sec
   */
  @GET("/api/v4/public/time")
  Call<Time> getServerTime();

  /**
   * rate limit: 2000 requests/10sec
   */
  @GET("/api/v4/public/ping")
  Call<ValueWrapper<String>> getServerStatus();

  /**
   * rate limit: 2000 requests/10sec
   */
  @GET("/api/v4/public/markets")
  Call<List<MarketInfo>> getMarkets();

  /**
   * rate limit: 600 requests/10sec
   */
  @GET("/api/v4/public/orderbook/{market}")
  Call<OrderBook> getOrderBook(@Path("market") Market symbol, @Query("limit") Integer depth, @Query("level") Integer aggregationLevel);
}
