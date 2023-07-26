package trade.wayruha.whitebit.service;

import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.dto.*;
import trade.wayruha.whitebit.service.endpoint.TickerEndpoint;

import java.util.List;
import java.util.Map;

public class TickerService extends ServiceBase {
  private final TickerEndpoint api;

  public TickerService(WBConfig config) {
    super(config);
    this.api = createService(TickerEndpoint.class);
  }

  public Map<String, SingleTicker>  getAvailableTickers() {
    V1Response<Map<String, SingleTicker>> response = client.executeSync(api.getAvailableTickers()).getData();
    return response.getResult();
  }

  public SingleMarketActivity getAvailableTicker(String symbol) {
    V1Response<SingleMarketActivity> response = client.executeSync(api.getAvailableTicker(symbol)).getData();
    return response.getResult();
  }
}
