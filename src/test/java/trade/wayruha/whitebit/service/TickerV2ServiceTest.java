package trade.wayruha.whitebit.service;

import org.junit.Test;
import trade.wayruha.whitebit.TestConstants;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.dto.AvailableTicker;
import trade.wayruha.whitebit.dto.MarketActivity;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TickerV2ServiceTest {
  WBConfig config = TestConstants.getSimpleConfig();
  TickerV2Service service = new TickerV2Service(config);

  @Test
  public void test_getAvailableTickers() {
    final  List<MarketActivity>  marketActivityList = service.getAvailableTickers();
    assertNotNull(marketActivityList);
    assertTrue(marketActivityList.size() > 10);
    MarketActivity listEntity = marketActivityList.get(0);
    assertNotNull(listEntity.getBaseVolume24h());
    assertNotNull(listEntity.getHighestBid());
    assertNotNull(listEntity.getLowestAsk());
    assertNotNull(listEntity.getQuoteVolume24h());
    assertNotNull(listEntity.getLastPrice());
    assertNotNull(listEntity.getLastUpdateTimestamp());
  }

  @Test
  public void test_getAvailableTicker() {
    MarketActivity ticker = service.getAvailableTicker("1INCH_BTC");

    assertNotNull(ticker);
    assertNotNull(ticker.getLastPrice());
  }
}
