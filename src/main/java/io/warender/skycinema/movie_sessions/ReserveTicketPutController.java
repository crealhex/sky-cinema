package io.warender.skycinema.movie_sessions;

import io.warender.skycinema.movie_tickets.Ticket;
import io.warender.skycinema.orders.OrderStorage;
import io.warender.skycinema.shared.ApiVersions;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReserveTicketPutController {

  private final OrderStorage orderStorage;
  private final MovieSessionStorage movieSessionStorage;

  @Transactional
  @PutMapping(ApiVersions.ONE + "/movie-sessions/tickets")
  public ResponseEntity<List<Ticket>> reserveTicketForClientSession(@RequestBody Request request) {
    var order =
        orderStorage
            .findByClientSessionId(request.clientSessionId())
            .orElseThrow(EntityNotFoundException::new);
    final var movieSession =
        movieSessionStorage
            .findById(order.getMovieSessionId())
            .orElseThrow(EntityNotFoundException::new);

    log.info("Seats the customer wants to purchase {}", request.desirableSeats());

    var tickets =
        movieSession.getTickets().stream()
            .filter(
                ticket ->
                    request.desirableSeats().contains(ticket.getId())
                        && !ticket.isReserved()
                        && !ticket.isAllocated())
            .toList();

    log.info("Tickets of the movie session {}", movieSession.getTickets());
    log.info("Size of the tickets {}", tickets.size());
    log.info("Size of the desirable seats {}", request.desirableSeats().size());

    if (tickets.size() != request.desirableSeats().size()) {
      log.info("Some of the seats are not available");
      return ResponseEntity.badRequest().build();
    }

    log.info("All the seats are available");
    log.info("Reserving the seats for the customer");
    tickets.forEach(seat -> seat.setReserved(true));
    movieSession.setReservedSeatsCount(movieSession.getReservedSeatsCount() + tickets.size());
    movieSessionStorage.save(movieSession);

    log.info("These are all the seats chosen by the customer {}", tickets);
    log.info("Now we have to create the tickets for the customer and " +
      "store then in their order using their client session id");

    tickets.forEach(ticket -> ticket.setOrder(order));

    log.info("Fill the information of the order and save it with the tickets");
    order.setTotalAmountCents(movieSession.getPriceInCents() * tickets.size());
    order.getTickets().addAll(tickets);
    orderStorage.save(order);

    return ResponseEntity.ok(tickets);
  }

  public record Request(String clientSessionId, Set<Long> desirableSeats) {}
}
