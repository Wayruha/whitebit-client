package trade.wayruha.whitebit.service;

import org.junit.Test;
import trade.wayruha.whitebit.TestConstants;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.dto.*;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TickerServiceTest {
  WBConfig config = TestConstants.getSimpleConfig();
  TickerService service = new TickerService(config);

  @Test
  public void test_getMarketActivity(){
    final  List<MarketActivity>  marketActivityList = service.getMarketActivity();
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
  public void test_getSingleMarketActivity(){
    SingleMarketActivity response = service.getSingleMarketActivity("ETH_BTC");
    assertNotNull(response);

    assertNotNull(response.getOpen());
    assertNotNull(response.getAsk());
    assertNotNull(response.getBid());
    assertNotNull(response.getLow());
    assertNotNull(response.getHigh());
    assertNotNull(response.getLast());
    assertNotNull(response.getVolume());
    assertNotNull(response.getDeal());
    assertNotNull(response.getChange());
  }

  @Test
  public void test_getAvailableTickers() {
    Map<String, AvailableTicker> response = service.getAvailableTickers();
    assertNotNull(response);

    AvailableTicker ticker = response.get("1INCH_BTC");
    assertNotNull(ticker);
    assertNotNull(ticker.getBaseVolume());
  }

  @Test
  public void test_getTickers() {
    Map<String, SingleTicker> response = service.getTickers();
    assertNotNull(response);

    SingleTicker ticker = response.get("1INCH_BTC");
    assertNotNull(ticker.getAt());
    assertNotNull(ticker.getTicker());

    SingleMarketActivity marketActivity = ticker.getTicker();

    assertNotNull(marketActivity.getAsk());
    assertNotNull(marketActivity.getBid());
    assertNotNull(marketActivity.getLow());
  }
}
