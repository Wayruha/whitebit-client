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

  public List<MarketActivity> getMarketActivity() {
    V1Response<List<MarketActivity>> response  =  client.executeSync(api.getMarketActivity()).getData();
    return response.getResult();
  }

  public SingleMarketActivity getSingleMarketActivity(String symbol) {
    V1Response<SingleMarketActivity> response = client.executeSync(api.getSingleMarketActivity(symbol)).getData();
    return response.getResult();
  }
  public Map<String, AvailableTicker> getAvailableTickers() {
    return client.executeSync(api.getAvailableTickers()).getData();
  }

  public Map<String, SingleTicker> getTickers() {
    V1Response<Map<String, SingleTicker>> response = client.executeSync(api.getTickers()).getData();
    return response.getResult();
  }
}
