package trade.wayruha.whitebit.ws;

import lombok.SneakyThrows;
import org.junit.Test;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.Market;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PublicWSSubscriptionTest {
    final WBConfig config = new WBConfig("", "");
    final WSClientFactory factory = new WSClientFactory(config);
    final int WAIT_MS = 2_000;
    final Market btc_usdt = new Market("BTC", "USDT");
    final Market wbt_usdt = new Market("WBT", "USDT");

    @Test
    public void test_OrderBookSubscription() {
        final TestCallback callback = new TestCallback();
        final WebSocketSubscriptionClient ws = factory.orderBookSubscription(Set.of(btc_usdt, wbt_usdt), 10, callback);
        sleep(WAIT_MS);
        assertTrue(callback.wsOpenCounter.get() > 0);
        assertTrue(callback.wsResponseCounter.get() > 0);
        ws.close();
    }

    @Test
    public void test_lastPriceSubscription() {
        final TestCallback callback = new TestCallback();
        final WebSocketSubscriptionClient ws = factory.lastPriceSubscription(Set.of(btc_usdt, wbt_usdt), callback);
        sleep(WAIT_MS);
        assertTrue(callback.wsOpenCounter.get() > 0);
        assertTrue(callback.wsResponseCounter.get() > 0);
        assertEquals(0, callback.wsClosedCounter.get());
        ws.close();
    }

    @Test
    public void test_candlesSubscription() {
        final TestCallback callback = new TestCallback();
        final WebSocketSubscriptionClient ws = factory.candlesSubscription(Set.of(btc_usdt, wbt_usdt), 60, callback);
        sleep(WAIT_MS);
        assertTrue(callback.wsOpenCounter.get() > 0);
        assertTrue(callback.wsResponseCounter.get() > 0);
        assertEquals(0, callback.wsClosedCounter.get());
        ws.close();
    }

    @Test
    public void test_tradesSubscription() {
        final TestCallback callback = new TestCallback();
        final WebSocketSubscriptionClient ws = factory.marketTradesSubscription(Set.of(btc_usdt, wbt_usdt), callback);
        sleep(WAIT_MS);
        assertTrue(callback.wsOpenCounter.get() > 0);
        assertTrue(callback.wsResponseCounter.get() > 0);
        assertEquals(0, callback.wsClosedCounter.get());
        ws.close();
    }

    // @Test disabled due to long execution time
    public void test_channelIsKeptAlive() {
        final TestCallback callback = new TestCallback();
        final WebSocketSubscriptionClient ws = factory.orderBookSubscription(Set.of(new Market("BTC", "USDT")), 10, callback);
        sleep(120_000);
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
