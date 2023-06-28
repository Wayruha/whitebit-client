package trade.wayruha.whitebit.service;

import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.dto.WBRequest;
import trade.wayruha.whitebit.dto.request.OrderRequest;
import trade.wayruha.whitebit.dto.response.OrderResponse;
import trade.wayruha.whitebit.service.endpoint.TradeEndpoint;

public class TradeService extends ServiceBase {
  private final TradeEndpoint api;
  public TradeService(WBConfig config) {
    super(config);
    this.api = createService(TradeEndpoint.class);
  }

  public OrderResponse createOrder(OrderRequest order){
    final WBRequest<OrderRequest> request = WBRequest.request(TradeEndpoint.CREATE_ORDER_URL, order);
    return client.executeSync(api.createOrder(request)).getData();
  }
}
