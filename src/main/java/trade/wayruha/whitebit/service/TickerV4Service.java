package trade.wayruha.whitebit.service;

import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.dto.*;
import trade.wayruha.whitebit.service.endpoint.TickerEndpoint;
import trade.wayruha.whitebit.service.endpoint.TickerV4Endpoint;

import java.util.List;
import java.util.Map;

public class TickerV4Service extends ServiceBase {
  private final TickerV4Endpoint api;

  public TickerV4Service(WBConfig config) {
    super(config);
    this.api = createService(TickerV4Endpoint.class);
  }

  public Map<String, AvailableTicker> getAvailableTickers() {
    return client.executeSync(api.getAvailableTickers()).getData();
  }

  public AvailableTicker getAvailableTicker(String symbol) {
    Map<String, AvailableTicker>  tickersResponse =  client.executeSync(api.getAvailableTickers()).getData();
    return tickersResponse.get(symbol);
  }
}
