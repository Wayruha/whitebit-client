package trade.wayruha.whitebit.service;

import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.domain.Order;
import trade.wayruha.whitebit.domain.StopOrder;
import trade.wayruha.whitebit.dto.KillSwitchStatus;
import trade.wayruha.whitebit.dto.OrderCreationStatus;
import trade.wayruha.whitebit.dto.request.*;
import trade.wayruha.whitebit.service.endpoint.TradeEndpoint;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class SpotTradeServiceV4 extends ServiceBase {
  private final TradeEndpoint tradeApi;

  public SpotTradeServiceV4(WBConfig config) {
    super(config);
    this.tradeApi = createService(TradeEndpoint.class);
  }

  public Order createOrder(NewOrderRequest req) {
    switch (req.getOrderType()) {
      case LIMIT:
        return client.executeSync(tradeApi.createLimitOrder(req)).getData();
      case MARKET:
        return client.executeSync(tradeApi.createMarketOrder(req)).getData();
      case MARKET_STOCK:
        return client.executeSync(tradeApi.createStockMarketOrder(req)).getData();
      default:
        throw new IllegalArgumentException("Unexpected order type: " + req.getOrderType());
    }
  }

  public StopOrder createStopOrder(StopOrderRequest req) {
    switch (req.getOrderType()) {
      case STOP_LIMIT:
        return client.executeSync(tradeApi.createStopLimitOrder(req)).getData();
      case STOP_MARKET:
        return client.executeSync(tradeApi.createStopMarketOrder(req)).getData();
      default:
        throw new IllegalArgumentException("Unexpected order type: " + req.getOrderType());
    }
  }

  public List<OrderCreationStatus> createBulkLimitOrders(List<NewOrderRequest> orders, boolean stopOnFail) {
    orders.forEach(order -> requireNonNull(order.getAmount()));
    final BulkOrdersCreation req = new BulkOrdersCreation(orders, stopOnFail);
    return client.executeSync(tradeApi.createBulkLimitOrders(req)).getData();
  }

  public KillSwitchStatus createKillSwitch(KillSwitchRequest request) {
    return client.executeSync(tradeApi.createKillSwitch(request)).getData();
  }

  public KillSwitchStatus deleteKillSwitch(Market market) {
    final KillSwitchRequest req = new KillSwitchRequest(market, null);
    return client.executeSync(tradeApi.createKillSwitch(req)).getData();
  }

  public List<KillSwitchStatus> getKillSwitchStatus(Market market) {
    final MarketParameter req = new MarketParameter(market);
    return client.executeSync(tradeApi.getKillSwitchStatus(req)).getData();
  }
}
