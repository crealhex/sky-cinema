package io.warender.skycinema.payments;

import io.warender.skycinema.orders.Order;
import io.warender.skycinema.orders.OrderTimer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderTimerTest {

  @Autowired
  private OrderTimer orderTimer;

  Logger log = LoggerFactory.getLogger(OrderTimerTest.class);

  @Test
  void reserveTicket() throws InterruptedException {
    Order ticket = new Order();
    orderTimer.startCountingTime(ticket);
//    log.info("The ticket has been reserved for 10 seconds: currentState = {}", ticket.isReserved());
    Thread.sleep(1000 * 11);
//    log.info("The ticket has been released: currentState = {}", ticket.isReserved());
//    assertFalse(ticket.isReserved());
  }
}
