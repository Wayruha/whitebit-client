package trade.wayruha.whitebit.service;

import org.jetbrains.annotations.Nullable;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.dto.MarketInfo;
import trade.wayruha.whitebit.dto.OrderBook;
import trade.wayruha.whitebit.dto.Time;
import trade.wayruha.whitebit.service.endpoint.MarketDataEndpoint;

import java.util.List;

public class MarketDataService extends ServiceBase {
  private final MarketDataEndpoint api;

  public MarketDataService(WBConfig config) {
    super(config);
    this.api = createService(MarketDataEndpoint.class);
  }

  public String getServerStatus() {
    return client.executeSync(api.getServerStatus()).getData().getValue();
  }

  public List<MarketInfo> getMarkets() {
    return client.executeSync(api.getMarkets()).getData();
  }

  public OrderBook getOrderBook(Market symbol, @Nullable Integer depth, @Nullable Integer aggregationLevel) {
    return client.executeSync(api.getOrderBook(symbol, depth, aggregationLevel)).getData();
  }

  public Time getServerTime(){
    return client.executeSync(api.getServerTime()).getData();
  }
}
