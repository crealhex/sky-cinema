package io.warender.skycinema.movie_sessions;

import io.warender.skycinema.movie_tickets.Ticket;
import io.warender.skycinema.movie_tickets.TicketStorage;
import io.warender.skycinema.shared.ApiVersions;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public final class MovieTicketsGetController {

  private final TicketStorage ticketStorage;

  @GetMapping(ApiVersions.ONE + "/backoffice/movie-sessions/{movieSessionId}/tickets")
  public ResponseEntity<Map<String, List<Ticket>>> getAllTickets(@PathVariable UUID movieSessionId) {
    var tickets = ticketStorage.findAllByMovieSessionId(movieSessionId);
    return ResponseEntity.ok(Map.of("tickets", tickets));
  }
}
