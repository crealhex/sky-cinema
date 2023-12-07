package io.warender.skycinema.orders;

import io.warender.skycinema.movie_sessions.MovieSessionStorage;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public final class OrderTimeoutProcessor {

  private final OrderTimer orderTimer;
  private final MovieSessionStorage movieSessionStorage;
  private final ExecutorService executorService = Executors.newSingleThreadExecutor();
  private final OrderStorage orderStorage;

  @PostConstruct
  public void init() {
    executorService.execute(
        () -> {
          //      while (true) {
          try {
            log.info("Waiting for the ticket to be released");

            DelayedOrder delayedOrder = orderTimer.getDelayQueue().take();
            final var order =
                orderStorage
                    .findById(delayedOrder.getOrder().getId())
                    .orElseThrow(EntityNotFoundException::new);
            if (order.getStatus().equals(OrderStatus.PAID)) {
              return;
            }
            order.getTickets().forEach(ticket -> ticket.setReserved(false));

            var movieSession =
                movieSessionStorage
                    .findById(order.getMovieSessionId())
                    .orElseThrow(EntityNotFoundException::new);
            movieSession.setReservedSeatsCount(
                movieSession.getReservedSeatsCount() - order.getTickets().size());
            movieSessionStorage.save(movieSession);

            order.setStatus(OrderStatus.CANCELLED);
            orderStorage.save(order);

            log.info("Ticket {} has been released", delayedOrder.getOrder().getId());
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
          //      }
        });
  }
}
