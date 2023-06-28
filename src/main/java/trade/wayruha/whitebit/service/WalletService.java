package trade.wayruha.whitebit.service;

import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.AssetBalance;
import trade.wayruha.whitebit.dto.request.TickerParam;
import trade.wayruha.whitebit.service.endpoint.WalletEndpoint;

import java.util.Map;

public class WalletService extends ServiceBase {
  private final WalletEndpoint api;

  public WalletService(WBConfig config) {
    super(config);
    this.api = createService(WalletEndpoint.class);
  }

  public AssetBalance getTradeBalance(String asset) {
    asset = asset.toUpperCase();
    final TickerParam ticker = new TickerParam(asset);
    final AssetBalance balance = client.executeSync(api.getTradeBalance(ticker)).getData();
    balance.setAsset(asset);
    return balance;
  }

  public Map<String, AssetBalance> getTradeBalances() {
    final Map<String, AssetBalance> balances = client.executeSync(api.getTradeBalances()).getData();
    populateAssetBalance(balances);
    return balances;
  }

  private static void populateAssetBalance(Map<String, AssetBalance> balancesMap) {
    balancesMap.forEach((key, value) -> value.setAsset(key));
  }
}
