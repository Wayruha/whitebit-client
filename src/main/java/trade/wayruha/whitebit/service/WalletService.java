package trade.wayruha.whitebit.service;

import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.AssetBalance;
import trade.wayruha.whitebit.dto.WBRequest;
import trade.wayruha.whitebit.dto.WBResponse;
import trade.wayruha.whitebit.dto.request.TickerParam;
import trade.wayruha.whitebit.service.endpoint.WalletEndpoint;

import java.util.Map;

public class WalletService extends ServiceBase{
  private final WalletEndpoint api;

  public WalletService(WBConfig config) {
    super(config);
    this.api = createService(WalletEndpoint.class);
  }

  public AssetBalance getTradeBalance(String asset) {
    asset = asset.toUpperCase();
    final TickerParam ticker = new TickerParam(asset);
    final WBRequest<TickerParam> req = WBRequest.request(api.GET_TRADE_BALANCE_PATH, ticker);
    final Map<String, AssetBalance> balances = client.executeSync(api.getTradeBalance(req)).getData();
    populateAssetBalance(balances);
    return balances.get(asset);
  }

  public Map<String, AssetBalance> getTradeBalances() {
    final WBRequest<TickerParam> req = WBRequest.request(api.GET_TRADE_BALANCE_PATH, null);
    final Map<String, AssetBalance> balances = client.executeSync(api.getTradeBalance(req)).getData();
    populateAssetBalance(balances);
    return balances;
  }

  private static void populateAssetBalance(Map<String, AssetBalance> balancesMap){
    balancesMap.forEach((key, value) -> value.setAsset(key));
  }
}
