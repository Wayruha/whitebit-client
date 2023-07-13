package trade.wayruha.whitebit.ws;

import okhttp3.Response;

import java.util.concurrent.atomic.AtomicInteger;

class TestCallback implements WebSocketCallback {
  final AtomicInteger wsOpenCounter = new AtomicInteger();
  final AtomicInteger wsResponseCounter = new AtomicInteger();
  final AtomicInteger wsClosedCounter = new AtomicInteger();

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
