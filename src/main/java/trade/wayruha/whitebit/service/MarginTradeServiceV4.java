package trade.wayruha.whitebit.service;

import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.client.WBResponse;
import trade.wayruha.whitebit.domain.*;
import trade.wayruha.whitebit.dto.request.*;
import trade.wayruha.whitebit.service.endpoint.OrdersEndpoint;
import trade.wayruha.whitebit.service.endpoint.TradeEndpoint;

import java.util.List;

import static trade.wayruha.whitebit.APIConstant.MARGIN_LEVERAGE_ACCEPTABLE_VALUES;

public class MarginTradeServiceV4 extends ServiceBase {
  private final TradeEndpoint tradeApi;
  private final OrdersEndpoint ordersApi;

  public MarginTradeServiceV4(WBConfig config) {
    super(config);
    this.tradeApi = createService(TradeEndpoint.class);
    this.ordersApi = createService(OrdersEndpoint.class);
  }

  public Order createOrder(NewOrderRequest o) {
    switch (o.getOrderType()) {
      case MARGIN_LIMIT:
        return client.executeSync(tradeApi.createMarginLimitOrder(o)).getData();
      case MARGIN_MARKET:
        return client.executeSync(tradeApi.createMarginMarketOrder(o)).getData();
      default:
        throw new IllegalArgumentException("Unexpected order type: " + o.getOrderType());
    }
  }

  public StopOrder createStopOrder(StopOrderRequest o) {
    switch (o.getOrderType()) {
      case MARGIN_STOP_LIMIT:
        return client.executeSync(tradeApi.createMarginStopLimitOrder(o)).getData();
      case MARGIN_STOP_MARKET:
        return client.executeSync(tradeApi.createMarginStopMarketOrder(o)).getData();
      default:
        throw new IllegalArgumentException("Unexpected order type: " + o.getOrderType());
    }
  }

  public List<MarginPosition> getOpenPositions(Market market) {
    final WBResponse<List<MarginPosition>> response = client.executeSync(ordersApi.getOpenCollateralPositions(new MarketParameter(market)));
    return response.getData();
  }

  public List<MarginPosition> getPositionsHistory(PositionsRequest request) {
    final WBResponse<List<MarginPosition>> response = client.executeSync(ordersApi.getCollateralPositionsHistory(request));
    return response.getData();
  }

  /**
   * Please note: Leverages of 50x and 100x are applicable only for futures trading.
   * When applied to margin trading, the maximum leverage applied will be 20x.
   * The leverage value is applied to the entire account, so if you choose a new leverage value below 50x, it will be applied to both margin and futures trading.
   * Additionally, we would like to draw your attention to the fact that calculations for futures positions with 50x and 100x leverage are done considering brackets (see endpoint futures).
   * You can familiarize yourself with the bracket mechanics for 50x and 100x leverage on the Trading Rules page.
   */
  public AccountLeverageInfo changeAccountLeverage(int leverage) {
    if (!MARGIN_LEVERAGE_ACCEPTABLE_VALUES.contains(leverage))
      throw new IllegalArgumentException(leverage + " is not acceptable leverage.");
    return client.executeSync(tradeApi.changeAccountLeverage(new AccountLeverageInfo(leverage))).getData();
  }

  public OCOOrder createOCOOrder(OCORequest req) {
    return client.executeSync(tradeApi.createOCOOrder(req)).getData();
  }

  public List<OCOOrder> getOpenOCOOrders(OrderDetailsRequest req) {
    return client.executeSync(tradeApi.getOpenOCOOrders(req)).getData();
  }

  public OCOOrder cancelOCO(OrderID req) {
    return client.executeSync(tradeApi.cancelOCOOrder(req)).getData();
  }
}
