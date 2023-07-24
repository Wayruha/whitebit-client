package trade.wayruha.whitebit.service;

import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.service.endpoint.MarginTradeEndpoint;

public class MarginTradeServiceV4 extends ServiceBase {
  private final MarginTradeEndpoint api;

  public MarginTradeServiceV4(WBConfig config) {
    super(config);
    this.api = createService(MarginTradeEndpoint.class);
  }

}
