package trade.wayruha.whitebit.service;

import org.junit.Test;
import trade.wayruha.whitebit.TestConstants;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.dto.MarketInfo;
import trade.wayruha.whitebit.dto.OrderBook;

import java.util.List;
import static org.junit.Assert.*;

public class MarketDataServiceTest {
  WBConfig config = TestConstants.getSimpleConfig();
  MarketDataService service = new MarketDataService(config);

  @Test
  public void test_getServerStatus(){
    final String status = service.getServerStatus();
    System.out.println("Server status: " + status);
    assertNotNull(status);
  }

  @Test
  public void test_getMarkets(){
    final List<MarketInfo> markets = service.getMarkets();
    assertTrue(markets.size() > 10);
    final MarketInfo first = markets.get(0);
    assertNotNull(first.getName());
    assertNotNull(first.getBaseAsset());
    assertNotNull(first.getQuoteAsset());
    assertNotNull(first.getBasePrecision());
    assertNotNull(first.getQuotePrecision());
    assertNotNull(first.getType());
  }

  @Test
  public void test_getOrderBook(){
    final int depth = 20;
    OrderBook orderBook = service.getOrderBook(Market.parse("BTC_USDT"), depth, null);
    assertEquals(depth, orderBook.getBids().size());
    assertEquals(depth, orderBook.getAsks().size());
    assertEquals(Market.parse("BTC_USDT"), orderBook.getTickerId());
    assertNotNull(orderBook.getAsks().get(0).getPrice());
    assertNotNull(orderBook.getAsks().get(0).getAmount());
    assertNotNull(orderBook.getBids().get(0).getPrice());
    assertNotNull(orderBook.getBids().get(0).getAmount());

    final int DEFAULT_DEPTH = 100;
    orderBook = service.getOrderBook(Market.parse("BTC_USDT"), null, null);
    assertEquals(DEFAULT_DEPTH, orderBook.getBids().size());
    assertEquals(DEFAULT_DEPTH, orderBook.getBids().size());
  }
}
