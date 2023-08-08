package trade.wayruha.whitebit.service;

import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.AssetBalance;
import trade.wayruha.whitebit.domain.MarginAssetBalance;
import trade.wayruha.whitebit.dto.CollateralSummary;
import trade.wayruha.whitebit.dto.request.TickerParameter;
import trade.wayruha.whitebit.service.endpoint.WalletEndpoint;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WalletServiceV4 extends ServiceBase {
  private final WalletEndpoint api;

  public WalletServiceV4(WBConfig config) {
    super(config);
    this.api = createService(WalletEndpoint.class);
  }

  public AssetBalance getTradeBalance(String asset) {
    final AssetBalance balance = client.executeSync(api.getTradeBalance(new TickerParameter(asset))).getData();
    balance.setAsset(asset);
    return balance;
  }

  public Map<String, AssetBalance> getTradeBalances() {
    final Map<String, AssetBalance> balances = client.executeSync(api.getTradeBalances()).getData();
    preProcessBalances(balances);
    return balances;
  }

  public MarginAssetBalance getCollateralBalance(String asset) {
    final List<MarginAssetBalance> data = client.executeSync(api.getCollateralBalance(new TickerParameter(asset))).getData();
    return data.stream().findFirst().orElse(null);
  }

  public Map<String, MarginAssetBalance> getCollateralBalances() {
    final List<MarginAssetBalance> balances = client.executeSync(api.getCollateralBalance(new TickerParameter())).getData();
    return balanceMapFromList(balances);
  }

  public CollateralSummary getCollateralAccountSummary() {
    return client.executeSync(api.getCollateralSummary()).getData();
  }

  /**
   * For each asset it returns a single number: available balance
   * Therefore, `locked` is considered null;
   */
  public Map<String, AssetBalance> getCollateralAvailableBalances() {
    final Map<String, BigDecimal> availableBalanceMap = client.executeSync(api.getCollateralAvailableBalances()).getData();
    return availableToBalance(availableBalanceMap);
  }

  /**
   * For each asset it returns a single number: available balance
   * Therefore, `locked` is considered null;
   */
  public AssetBalance getCollateralAvailableBalance(String asset) {
    final Map<String, BigDecimal> availableBalanceMap = client.executeSync(api.getCollateralAvailableBalance(new TickerParameter(asset))).getData();
    return new AssetBalance(asset, availableBalanceMap.get(asset), null);
  }

  //helper methods
  private static void preProcessBalances(Map<String, AssetBalance> balancesMap) {
    balancesMap.values().removeIf(AssetBalance::isEmpty);
    balancesMap.forEach((key, value) -> value.setAsset(key));
  }

  private static <T extends AssetBalance> Map<String, T> balanceMapFromList(List<T> balances) {
    return balances.stream()
        .filter(bal -> !bal.isEmpty())
        .collect(Collectors.toMap(AssetBalance::getAsset, Function.identity()));
  }

  private static Map<String, AssetBalance> availableToBalance(Map<String, BigDecimal> availableBalancesMap) {
    return availableBalancesMap.entrySet().stream()
        .filter(e -> e.getValue().signum() != 0)
        .collect(Collectors.toMap(Map.Entry::getKey, e -> new AssetBalance(e.getKey(), e.getValue(), null)));
  }
}
