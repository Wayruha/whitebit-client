package trade.wayruha.whitebit.service;

import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.dto.*;
import trade.wayruha.whitebit.service.endpoint.TickerEndpoint;

import java.util.Map;

public class TickerServiceV1 extends ServiceBase {
  private final TickerEndpoint api;

  public TickerServiceV1(WBConfig config) {
    super(config);
    this.api = createService(TickerEndpoint.class);
  }

  public Map<Market, MarketActivityV1>  getAvailableTickers() {
    V1Response<Map<Market, MarketActivityV1>> response = client.executeSync(api.getAvailableTickersV1()).getData();
    return response.getResult();
  }

  public MarketActivityV1.Ticker getAvailableTicker(Market symbol) {
    V1Response<MarketActivityV1.Ticker> response = client.executeSync(api.getAvailableTickerV1(symbol)).getData();
    return response.getResult();
  }
}
