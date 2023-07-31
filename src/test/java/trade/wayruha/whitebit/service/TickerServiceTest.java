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
    Map<String, SingleTicker> response = service.getAvailableTickers();
    assertNotNull(response);

    SingleTicker ticker = response.get("1INCH_BTC");
    assertNotNull(ticker.getAt());
    assertNotNull(ticker.getTicker());

    SingleMarketActivity marketActivity = ticker.getTicker();

    assertNotNull(marketActivity.getAsk());
    assertNotNull(marketActivity.getBid());
    assertNotNull(marketActivity.getLow());
  }

  @Test
  public void test_getSingleMarketActivity(){
    SingleMarketActivity response = service.getAvailableTicker("ETH_BTC");
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




}
