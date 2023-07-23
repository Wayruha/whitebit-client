package trade.wayruha.whitebit.ws;

import lombok.SneakyThrows;
import org.junit.Test;
import trade.wayruha.whitebit.TestConstants;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.dto.request.ExecutedOrdersFilter;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * for some tests to pass successfully you would need to do account manipulations
 * E.g., pendingOrders subscription
 */
public class PrivateWSSubscriptionTest {
  final WBConfig config = new WBConfig(TestConstants.API_KEY, TestConstants.API_SECRET);
  final WSClientFactory factory = new WSClientFactory(config);
  final int WAIT_MS = 2_000;
  final Market btc_usdt = Market.parse("BTC_USDT");
  final Market wbt_usdt = Market.parse("WBT_USDT");

  @Test
  public void test_spotBalanceSubscription() {
    final TestCallback callback = new TestCallback();
    final WebSocketPrivateClient ws = factory.spotBalanceSubscription(Set.of("WBT", "ETH", "USDT", "USDC", "SSLX", "ETHW"), callback);
    sleep(WAIT_MS);
    assertTrue(callback.wsOpenCounter.get() > 0);
    assertTrue(callback.wsResponseCounter.get() > 0);
    assertEquals(0, callback.wsClosedCounter.get());
    ws.close();
  }

  @Test
  public void test_marginBalanceSubscription() {
    final TestCallback callback = new TestCallback();
    final WebSocketPrivateClient ws = factory.spotBalanceSubscription(Set.of("WBT", "ETH", "USDT", "USDC", "SSLX", "ETHW"), callback);
    sleep(WAIT_MS);
    assertTrue(callback.wsOpenCounter.get() > 0);
    assertTrue(callback.wsResponseCounter.get() > 0);
    assertEquals(0, callback.wsClosedCounter.get());
    ws.close();
  }

  @Test
  public void test_pendingOrdersSubscription() {
    final TestCallback callback = new TestCallback();
    final WebSocketPrivateClient ws = factory.pendingOrdersSubscription(Set.of(btc_usdt), callback);
    sleep(WAIT_MS);
    assertTrue(callback.wsOpenCounter.get() > 0);
    assertTrue(callback.wsResponseCounter.get() > 0);
    assertEquals(0, callback.wsClosedCounter.get());
    ws.close();
  }

  @Test
  public void test_executedOrdersSubscription() {
    final TestCallback callback = new TestCallback();
    final WebSocketPrivateClient ws = factory.executedOrdersSubscription(Set.of(wbt_usdt, btc_usdt), ExecutedOrdersFilter.MARKET, callback);
    sleep(WAIT_MS);
    assertTrue(callback.wsOpenCounter.get() > 0);
    assertTrue(callback.wsResponseCounter.get() > 0);
    assertEquals(0, callback.wsClosedCounter.get());
    ws.close();
  }

  @Test
  public void test_dealsSubscription() {
    final TestCallback callback = new TestCallback();
    final WebSocketPrivateClient ws = factory.dealsSubscription(Set.of(wbt_usdt, btc_usdt), callback);
    sleep(50_000);
    assertTrue(callback.wsOpenCounter.get() > 0);
    assertTrue(callback.wsResponseCounter.get() > 0);
    assertEquals(0, callback.wsClosedCounter.get());
    ws.close();
  }

  @SneakyThrows
  private static void sleep(int sleepMs){
    Thread.sleep(sleepMs);
  }
}
