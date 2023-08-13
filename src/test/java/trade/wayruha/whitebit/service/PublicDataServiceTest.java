package trade.wayruha.whitebit.service;

import org.junit.Test;
import trade.wayruha.whitebit.TestConstants;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.domain.enums.OrderSide;
import trade.wayruha.whitebit.dto.*;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class PublicDataServiceTest {
  WBConfig config = TestConstants.getSimpleConfig();
  PublicDataService service = new PublicDataService(config);
  final Market BTC_USDT = Market.parse("BTC_USDT");
  final String btc = "BTC";

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
    OrderBook orderBook = service.getOrderBook(BTC_USDT, depth, null);
    assertEquals(depth, orderBook.getBids().size());
    assertEquals(depth, orderBook.getAsks().size());
    assertEquals(BTC_USDT, orderBook.getTickerId());
    assertNotNull(orderBook.getAsks().get(0).getPrice());
    assertNotNull(orderBook.getAsks().get(0).getAmount());
    assertNotNull(orderBook.getBids().get(0).getPrice());
    assertNotNull(orderBook.getBids().get(0).getAmount());

    final int DEFAULT_DEPTH = 100;
    orderBook = service.getOrderBook(BTC_USDT, null, null);
    assertEquals(DEFAULT_DEPTH, orderBook.getBids().size());
    assertEquals(DEFAULT_DEPTH, orderBook.getBids().size());
  }

  @Test
  public void test_getServerTime() {
    final long serverTime = service.getServerTime();
    assertTrue(serverTime > 0);
  }

  @Test
  public void test_getFees() {
    Map<String, FeeInfo> response = service.getFees();
    assertNotNull(response);

    String ticker = "AAVE";
    FeeInfo fee = response.get("AAVE");

    assertEquals(fee.getTicker(), ticker);
    assertTrue(fee.isDepositable());
    assertNotNull(fee.getWithdraw().getMaxAmount());
  }

  @Test
  public void test_getAssetMap() {
    Map<String, AssetInfo> response = service.getAssets();
    assertNotNull(response);

    AssetInfo asset = response.get(btc);

    assertNotNull(asset.getName());
    assertNotNull(asset.getMaxDeposit());
    assertNotNull(asset.getNetworks().getDefaultNetwork());
    assertNotNull(asset.getLimits().getDeposit());
    assertNotNull(asset.getConfirmations());
    assertTrue(asset.getConfirmations().size() > 0);
  }

  @Test
  public void test_getTrades() {
    List<Trade> response = service.getTrades(BTC_USDT, OrderSide.SELL);

    assertNotNull(response);
    Trade trade = response.get(0);
    assertEquals(trade.getType(), OrderSide.SELL);
  }

  @Test
  public void test_getBuyTrades() {
    List<Trade> response = service.getTrades(BTC_USDT, OrderSide.BUY);

    assertNotNull(response);
    Trade trade = response.get(0);
    assertEquals(trade.getType(), OrderSide.BUY);
  }


  @Test
  public void test_getCollateralMarkets() {
    List<Market> response = service.getCollateralMarkets();

    assertNotNull(response);
    assertTrue(response.size() > 0);
  }


  @Test
  public void test_getFuturesMarkets() {
    final List<FuturesMarket> response = service.getFuturesMarkets();
    assertNotNull(response);
    assertTrue(response.size() > 0);

    final FuturesMarket market = response.get(0);
    assertNotNull(market.getTicker());
    assertNotNull(market.getBaseCurrency());
    assertNotNull(market.getQuoteCurrency());
    assertNotNull(market.getLastPrice());
    assertNotNull(market.getBaseAssetVolume());
    assertNotNull(market.getQuoteAssetVolume());
    assertNotNull(market.getHighestBid());
    assertNotNull(market.getLowestAsk());
    assertNotNull(market.getHighest24HrsPrice());
    assertNotNull(market.getLowest24HrsPrice());
    assertNotNull(market.getProductType());
    assertNotNull(market.getOpenInterest());
    assertNotNull(market.getIndexCurrency());
    assertNotNull(market.getIndexPrice());
    assertNotNull(market.getFundingRate());
    assertTrue(market.getNextFundingRateTimestamp() > 0);
    assertNotNull(market.getBrackets());
    assertTrue(market.getMaxLeverage() > 0);
  }

  @Test
  public void test_getAvailableTickers() {
    Map<Market, MarketActivityV4> response = service.getAvailableTickers();
    assertNotNull(response);
    MarketActivityV4 ticker = response.get(BTC_USDT);
    assertNotNull(ticker);
    assertNotNull(ticker.getBaseVolume());
  }

  @Test
  public void test_getAvailableTicker() {
    MarketActivityV4 ticker = service.getAvailableTicker(BTC_USDT);
    assertNotNull(ticker);
    assertNotNull(ticker.getBaseVolume());
  }
}
