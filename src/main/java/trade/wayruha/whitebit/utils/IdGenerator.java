package trade.wayruha.whitebit.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
  private static final AtomicInteger COUNTER = new AtomicInteger();
  private static final Set<Long> TIME_SET = new HashSet<>();

  public static int getNextId(){
    final Long time = System.currentTimeMillis();

    if (!TIME_SET.contains(time)) {
      COUNTER.set(0);
      TIME_SET.clear();
      TIME_SET.add(time);
    }

    return COUNTER.addAndGet(1);
  }
}
