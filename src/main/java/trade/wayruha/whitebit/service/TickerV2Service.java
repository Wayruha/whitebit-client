package trade.wayruha.whitebit.service;

import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.dto.AvailableTicker;
import trade.wayruha.whitebit.dto.MarketActivity;
import trade.wayruha.whitebit.dto.V1Response;
import trade.wayruha.whitebit.service.endpoint.TickerV2Endpoint;
import trade.wayruha.whitebit.service.endpoint.TickerV4Endpoint;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TickerV2Service extends ServiceBase {
  private final TickerV2Endpoint api;

  public TickerV2Service(WBConfig config) {
    super(config);
    this.api = createService(TickerV2Endpoint.class);
  }

  public List<MarketActivity> getAvailableTickers() {
    V1Response<List<MarketActivity>> response  =  client.executeSync(api.getAvailableTickers()).getData();
    return response.getResult();
  }

  public MarketActivity getAvailableTicker(String symbol) {
    V1Response<List<MarketActivity>> response  =  client.executeSync(api.getAvailableTickers()).getData();
    List<MarketActivity> marketActivityList = response.getResult();
    for(MarketActivity ma : marketActivityList) {
      if(Objects.equals(ma.getTradingPairs(), symbol)) return ma;
    }
    return new MarketActivity();
  }
}
