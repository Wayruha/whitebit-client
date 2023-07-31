package trade.wayruha.whitebit.service;

import org.junit.Test;
import trade.wayruha.whitebit.TestConstants;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.dto.*;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TickerServiceTest {
  WBConfig config = TestConstants.getSimpleConfig();
  TickerServiceV1 service = new TickerServiceV1(config);
  TickerServiceV2 serviceV2 = new TickerServiceV2(config);
  TickerServiceV4 serviceV4 = new TickerServiceV4(config);
  final Market symbol = Market.parse("1INCH_BTC");

  @Test
  public void test_getMarketActivity(){
    Map<Market, MarketActivityV1> response = service.getAvailableTickers();
    assertNotNull(response);

    MarketActivityV1 ticker = response.get(symbol);
    assertNotNull(ticker.getAt());
    assertNotNull(ticker.getTicker());

    MarketActivityV1.Ticker marketActivity = ticker.getTicker();

    assertNotNull(marketActivity.getAsk());
    assertNotNull(marketActivity.getBid());
    assertNotNull(marketActivity.getLow());
  }

  @Test
  public void test_getSingleMarketActivity(){
    MarketActivityV1.Ticker response = service.getAvailableTicker(Market.parse("ETH_BTC"));
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

  //V2
  @Test
  public void test_getAvailableTickersV2() {
    final  List<MarketActivity>  marketActivityList = serviceV2.getAvailableTickers();
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
  public void test_getAvailableTickerV2() {
    MarketActivity ticker = serviceV2.getAvailableTicker(symbol);
    assertNotNull(ticker);
    assertNotNull(ticker.getLastPrice());
  }

  //V4
  @Test
  public void test_getAvailableTickers() {
    Map<Market, MarketActivityV4> response = serviceV4.getAvailableTickers();
    assertNotNull(response);
    MarketActivityV4 ticker = response.get(symbol);
    assertNotNull(ticker);
    assertNotNull(ticker.getBaseVolume());
  }

  @Test
  public void test_getAvailableTicker() {
    MarketActivityV4 ticker = serviceV4.getAvailableTicker(symbol);
    assertNotNull(ticker);
    assertNotNull(ticker.getBaseVolume());
  }
}
