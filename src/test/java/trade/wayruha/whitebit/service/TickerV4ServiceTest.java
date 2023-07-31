package trade.wayruha.whitebit.service;

import org.junit.Test;
import trade.wayruha.whitebit.TestConstants;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.dto.AvailableTicker;
import trade.wayruha.whitebit.dto.MarketActivity;
import trade.wayruha.whitebit.dto.SingleMarketActivity;
import trade.wayruha.whitebit.dto.SingleTicker;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TickerV4ServiceTest {
  WBConfig config = TestConstants.getSimpleConfig();
  TickerV4Service service = new TickerV4Service(config);

  @Test
  public void test_getAvailableTickers() {
    Map<String, AvailableTicker> response = service.getAvailableTickers();
    assertNotNull(response);

    AvailableTicker ticker = response.get("1INCH_BTC");
    assertNotNull(ticker);
    assertNotNull(ticker.getBaseVolume());
  }

  @Test
  public void test_getAvailableTicker() {
    AvailableTicker ticker = service.getAvailableTicker("1INCH_BTC");

    assertNotNull(ticker);
    assertNotNull(ticker.getBaseVolume());
  }
}
