package trade.wayruha.whitebit.ws;

import lombok.SneakyThrows;
import okhttp3.Response;
import org.junit.Test;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.Market;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PublicWSSubscriptionTest {
    final WBConfig config = new WBConfig("", "");
    final WSClientFactory factory = new WSClientFactory(config);
    final AtomicInteger wsOpenCounter = new AtomicInteger();
    final AtomicInteger wsResponseCounter = new AtomicInteger();
    final AtomicInteger wsClosedCounter = new AtomicInteger();

    @Test
    public void test_OrderBookSubscription() {
        final Callback callback = new Callback();
        final WebSocketSubscriptionClient ws = factory.orderBookSubscription(Set.of(new Market("BTC", "USDT")), 10, callback);
        sleep(50_000);
        assertTrue(wsOpenCounter.get() > 0);
        assertTrue(wsResponseCounter.get() > 0);
        ws.close();
    }

    @Test
    public void test_channelIsKeptAlive() {
        final Callback callback = new Callback();
        final WebSocketSubscriptionClient ws = factory.orderBookSubscription(Set.of(new Market("BTC", "USDT")), 10, callback);
        sleep(120_000);
        assertTrue(wsOpenCounter.get() > 0);
        assertTrue(wsResponseCounter.get() > 0);
        assertEquals(0, wsClosedCounter.get());
        ws.close();
    }

    @SneakyThrows
    private static void sleep(int sleepMs){
        Thread.sleep(sleepMs);
    }

    class Callback implements WebSocketCallback{

        @Override
        public void onResponse(Object response) {
            System.out.println("Got response:" + response);
            wsResponseCounter.incrementAndGet();
        }

        @Override
        public void onClosed(int code, String reason) {
            wsClosedCounter.incrementAndGet();
            WebSocketCallback.super.onClosed(code, reason);
        }

        @Override
        public void onFailure(Throwable ex, Response response) {
            WebSocketCallback.super.onFailure(ex, response);
        }

        @Override
        public void onOpen(Response response) {
            WebSocketCallback.super.onOpen(response);
            wsOpenCounter.incrementAndGet();
        }
    }
}
