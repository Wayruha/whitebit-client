package trade.wayruha.whitebit.service;

import org.junit.Test;
import trade.wayruha.whitebit.TestConstants;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.AssetBalance;
import trade.wayruha.whitebit.domain.MarginAssetBalance;
import trade.wayruha.whitebit.domain.enums.Account;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WalletServiceTest {
  WBConfig config = TestConstants.getSimpleConfig();
  WalletServiceV4 service = new WalletServiceV4(config);
  final String usdt = "USDT";
  final String wbt = "WBT";
  final String eth = "ETH";

  @Test
  public void test_getTradeBalance() {
    final AssetBalance balance = service.getBalance(Account.TRADE, usdt);
    System.out.println("Balance: " + balance);
    assertEquals(usdt, balance.getAsset());
    assertNotNull(balance.getAvailable());
    assertNotNull(balance.getFreeze());

    final Map<String, AssetBalance> balances = (Map<String, AssetBalance>) service.getBalances(Account.TRADE);
    System.out.println("Balances: " + balances);
    assertNotNull(balances);
  }

  @Test
  public void test_getCollateralAvailableBalance() {
    final AssetBalance balance = service.getCollateralAvailableBalance(eth);
    System.out.println("Balance: " + balance);
    assertEquals(eth, balance.getAsset());
    assertNotNull(balance.getAvailable());
    assertNotNull(balance.getFreeze());

    final Map<String, AssetBalance> balances = service.getCollateralAvailableBalances();
    System.out.println("Balances: " + balances);
    assertNotNull(balances);
  }

  @Test
  public void test_getCollateralBalanceSummary() {
    final BigDecimal availableBalance = service.getCollateralAvailableBalance(eth).getAvailable();
    final MarginAssetBalance balance = service.getCollateralBalance(eth);
    System.out.println("Balance: " + balance);
    assertEquals(eth, balance.getAsset());
    assertNotNull(balance.getAvailable());
    assertNotNull(balance.getFreeze());
    assertEquals(availableBalance, balance.getAvailable());

    final Map<String, MarginAssetBalance> balances = service.getCollateralBalances();
    System.out.println("Balances: " + balances);
    assertNotNull(balances);
  }
}
