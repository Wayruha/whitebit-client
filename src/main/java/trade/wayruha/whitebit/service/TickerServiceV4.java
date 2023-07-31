package trade.wayruha.whitebit.service;

import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.dto.*;
import trade.wayruha.whitebit.service.endpoint.TickerEndpoint;

import java.util.Map;

public class TickerServiceV4 extends ServiceBase {
  private final TickerEndpoint api;

  public TickerServiceV4(WBConfig config) {
    super(config);
    this.api = createService(TickerEndpoint.class);
  }

  public Map<Market, MarketActivityV4> getAvailableTickers() {
    return client.executeSync(api.getAvailableTickersV4()).getData();
  }

  public MarketActivityV4 getAvailableTicker(Market symbol) {
    Map<Market, MarketActivityV4>  tickersResponse =  client.executeSync(api.getAvailableTickersV4()).getData();
    return tickersResponse.get(symbol);
  }
}
