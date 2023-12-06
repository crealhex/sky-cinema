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
public class ReserveSeatPutController {

  private final OrderStorage orderStorage;
  private final MovieSessionStorage movieSessionStorage;

  @Transactional
  @PutMapping(ApiVersions.ONE + "/movie-sessions/seats")
  public ResponseEntity<List<Ticket>> reserveSeatForClientSession(@RequestBody Request request) {
    var order =
        orderStorage
            .findByClientSessionId(request.clientSessionId())
            .orElseThrow(EntityNotFoundException::new);
    final var movieSession =
        movieSessionStorage
            .findById(order.getMovieSessionId())
            .orElseThrow(EntityNotFoundException::new);

    log.info("Seats the customer wants to purchase {}", request.desirableSeats());

    var seats =
        movieSession.getCinemaHall().getSeats().stream()
            .filter(
                seat ->
                    request.desirableSeats().contains(seat.getId())
                        && !seat.isReserved()
                        && !seat.isAllocated())
            .toList();

    if (seats.size() != request.desirableSeats().size()) {
      log.info("Some of the seats are not available");
      return ResponseEntity.badRequest().build();
    }

    log.info("All the seats are available");
    log.info("Reserving the seats for the customer");
    seats.forEach(seat -> seat.setReserved(true));
    movieSession.setReservedSeatsCount(movieSession.getReservedSeatsCount() + seats.size());
    movieSessionStorage.save(movieSession);

    log.info("These are all the seats chosen by the customer {}", seats);
    log.info("Now we have to create the tickets for the customer and " +
      "store then in their order using their client session id");

    var tickets = seats.stream()
      .map(seat -> {
        var ticket = new Ticket();
        ticket.setMovieName("My Simple Movie name");
        ticket.setMovieStartTime(movieSession.getStartTime());
        ticket.setCinemaHallName("SALA 9");
        ticket.setSeatNumber(seat.getRow() + seat.getNumber());
        ticket.setTicketPriceCents(movieSession.getPriceInCents());
        ticket.setDurationMinutes(movieSession.getMovie().getDurationInMinutes());
        ticket.setOrder(order);
        return ticket;
      })
      .toList();

    log.info("Fill the information of the order and save it with the tickets");
    order.setTotalAmountCents(movieSession.getPriceInCents() * tickets.size());
    order.getTickets().addAll(tickets);
    orderStorage.save(order);

    return ResponseEntity.ok(tickets);
  }

  public record Request(String clientSessionId, Set<Integer> desirableSeats) {}
}
