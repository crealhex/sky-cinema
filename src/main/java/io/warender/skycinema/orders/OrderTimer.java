package io.warender.skycinema.orders;

import java.util.concurrent.DelayQueue;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public final class OrderTimer {

  private final DelayQueue<DelayedOrder> delayQueue = new DelayQueue<>();

  public void startCountingTime(Order order) {
    long delayInMilliseconds = 1000 * 60 * 2; // 2 minutes
    delayQueue.put(new DelayedOrder(order, delayInMilliseconds));
  }
}
