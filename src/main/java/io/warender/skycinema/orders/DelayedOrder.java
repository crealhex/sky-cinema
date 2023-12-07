package io.warender.skycinema.orders;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.NonNull;

public final class DelayedOrder implements Delayed {

  @Getter
  private final Order order;
  private final long releaseTime;

  public DelayedOrder(Order order, long delayInMilliseconds) {
    this.order = order;
    this.releaseTime = System.currentTimeMillis() + delayInMilliseconds;
  }

  @Override
  public long getDelay(@NonNull TimeUnit unit) {
    long delay = releaseTime - System.currentTimeMillis();
    return unit.convert(delay, TimeUnit.MILLISECONDS);
  }

  @Override
  public int compareTo(@NonNull Delayed other) {
    return Long.compare(this.releaseTime, ((DelayedOrder) other).releaseTime);
  }
}
