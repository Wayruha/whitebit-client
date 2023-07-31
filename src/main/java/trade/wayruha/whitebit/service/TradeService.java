package trade.wayruha.whitebit.service;

import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.dto.request.OrderRequest;
import trade.wayruha.whitebit.dto.Order;
import trade.wayruha.whitebit.service.endpoint.TradeEndpoint;

public class TradeService extends ServiceBase {
  private final TradeEndpoint api;

  public TradeService(WBConfig config) {
    super(config);
    this.api = createService(TradeEndpoint.class);
  }

  public Order createOrder(OrderRequest order) {
    return client.executeSync(api.createOrder(order)).getData();
  }
}