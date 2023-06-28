package trade.wayruha.whitebit.service;

import org.jetbrains.annotations.Nullable;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.dto.response.MarketInfo;
import trade.wayruha.whitebit.dto.response.OrderBook;
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

  public OrderBook getOrderBook(String symbol, @Nullable Integer depth, @Nullable Integer aggregationLevel) {
    return client.executeSync(api.getOrderBook(symbol, depth, aggregationLevel)).getData();
  }
}
