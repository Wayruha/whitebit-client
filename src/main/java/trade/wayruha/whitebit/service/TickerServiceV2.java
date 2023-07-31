package trade.wayruha.whitebit.service;

import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.dto.MarketActivity;
import trade.wayruha.whitebit.dto.V1Response;
import trade.wayruha.whitebit.service.endpoint.TickerEndpoint;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class TickerServiceV2 extends ServiceBase {
  private final TickerEndpoint api;

  public TickerServiceV2(WBConfig config) {
    super(config);
    this.api = createService(TickerEndpoint.class);
  }

  public List<MarketActivity> getAvailableTickers() {
    V1Response<List<MarketActivity>> response  =  client.executeSync(api.getAvailableTickersV2()).getData();
    return response.getResult();
  }

  public MarketActivity getAvailableTicker(Market symbol) {
    requireNonNull(symbol);
    return getAvailableTickers().stream()
        .filter(activity -> symbol.equals(activity.getTradingPairs()))
        .findFirst().orElse(null);
  }
}
