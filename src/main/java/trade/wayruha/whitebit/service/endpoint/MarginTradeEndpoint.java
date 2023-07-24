package trade.wayruha.whitebit.service.endpoint;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import trade.wayruha.whitebit.APIConstant;
import trade.wayruha.whitebit.domain.MarginPosition;
import trade.wayruha.whitebit.domain.OCOOrder;
import trade.wayruha.whitebit.domain.Order;
import trade.wayruha.whitebit.domain.OrderID;
import trade.wayruha.whitebit.dto.request.*;

import java.util.List;

public interface MarginTradeEndpoint {
  /**  rate limit: 10000 requests/10sec */
  @POST("/api/v4/order/collateral/limit")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Order> createMarginLimitOrder(@Body NewOrderRequest order);

  /**  rate limit: 10000 requests/10sec */
  @POST("/api/v4/order/collateral/market")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Order> createMarginMarketOrder(@Body NewOrderRequest order);

  /**  rate limit: 10000 requests/10sec */
  @POST("/api/v4/order/collateral/stop-limit")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Order> createMarginStopLimitOrder(@Body NewOrderRequest order);

  /**  rate limit: 10000 requests/10sec */
  @POST("/api/v4/order/collateral/trigger-market")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<Order> createMarginStopMarketOrder(@Body NewOrderRequest order);

  /**  rate limit: 12000 requests/10sec */
  @POST("/api/v4/collateral-account/positions/open")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<List<MarginPosition>> getOpenCollateralPositions(@Body MarketParameter request);

  /**  rate limit: 12000 requests/10sec */
  @POST("/api/v4/collateral-account/positions/history")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<List<MarginPosition>> getCollateralPositionsHistory(@Body PositionsRequest request);


  /**
   * Please note: Leverages of 50x and 100x are applicable only for futures trading.
   * When applied to margin trading, the maximum leverage applied will be 20x.
   * The leverage value is applied to the entire account, so if you choose a new leverage value below 50x, it will be applied to both margin and futures trading.
   * Additionally, we would like to draw your attention to the fact that calculations for futures positions with 50x and 100x leverage are done considering brackets (see endpoint futures).
   * You can familiarize yourself with the bracket mechanics for 50x and 100x leverage on the Trading Rules page.
   */
  /**  rate limit: 12000 requests/10sec */
  @POST("/api/v4/collateral-account/leverage")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<ChangeLeverageRequest> changeAccountLeverage(@Body ChangeLeverageRequest leverage);

  // OCO

  /**  rate limit: 10000 requests/10sec */
  @POST("/api/v4/order/collateral/oco")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<OCOOrder> createOCOOrder(@Body OCORequest order);


  /**  rate limit: 1000 requests/10sec */
  @POST("/api/v4/oco-orders")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<List<OCOOrder>> getOpenOCOOrders(@Body OrderDetailsRequest order);

  /**  rate limit: 10000 requests/10sec */
  @POST("/api/v4/order/oco-cancel")
  @Headers(APIConstant.ENDPOINT_SECURITY_SIGNED_HEADER)
  Call<OCOOrder> cancelOCOOrder(@Body OrderID order);
}
