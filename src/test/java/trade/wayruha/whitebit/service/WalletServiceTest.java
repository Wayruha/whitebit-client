package trade.wayruha.whitebit.service;

import org.junit.Test;
import trade.wayruha.whitebit.TestConstants;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.AssetBalance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WalletServiceTest {
  WBConfig config = TestConstants.getSimpleConfig();
  WalletService service = new WalletService(config);

  @Test
  public void test_getTradeBalance() {
    final String usdt = "USDT";
    final AssetBalance balance = service.getTradeBalance(usdt);
    assertEquals(usdt, balance.getAsset());
    assertNotNull(balance.getAvailable());
    assertNotNull(balance.getFreeze());

  }
}
