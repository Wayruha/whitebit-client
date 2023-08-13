package trade.wayruha.whitebit.service;

import org.jetbrains.annotations.Nullable;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.domain.enums.OrderSide;
import trade.wayruha.whitebit.dto.*;
import trade.wayruha.whitebit.service.endpoint.PublicEndpoints;

import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class PublicDataService extends ServiceBase {
  private final PublicEndpoints api;

  public PublicDataService(WBConfig config) {
    super(config);
    this.api = createService(PublicEndpoints.class);
  }

  public long getServerTime(){
    return client.executeSync(api.getServerTime()).getData().getTime();
  }

  public String getServerStatus() {
    return client.executeSync(api.getServerStatus()).getData().getValue();
  }

  public List<MarketInfo> getMarkets() {
    return client.executeSync(api.getMarkets()).getData();
  }

  public Map<String, AssetInfo> getAssets() {
    return client.executeSync(api.getAssets()).getData();
  }

  public Map<String, FeeInfo> getFees() {
    return client.executeSync(api.getFees()).getData();
  }

  public OrderBook getOrderBook(Market symbol, @Nullable Integer depth, @Nullable Integer aggregationLevel) {
    return client.executeSync(api.getOrderBook(symbol, depth, aggregationLevel)).getData();
  }

  public List<Trade> getTrades(Market symbol, OrderSide orderSide) {
    requireNonNull(orderSide);
    return client.executeSync(api.getTrades(symbol, orderSide.getName())).getData();
  }

  public List<Market> getCollateralMarkets(){
    return client.executeSync(api.getCollateralMarkets()).getData().getResult();
  }

  public List<FuturesMarket> getFuturesMarkets(){
    return client.executeSync(api.getFuturesMarkets()).getData().getResult();
  }
}
