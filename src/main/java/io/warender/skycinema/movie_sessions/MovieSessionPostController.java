package io.warender.skycinema.movie_sessions;

import io.warender.skycinema.cinema_halls.CinemaHallStorage;
import io.warender.skycinema.movie_tickets.Ticket;
import io.warender.skycinema.movies.Language;
import io.warender.skycinema.movies.MovieStorage;
import io.warender.skycinema.shared.ApiVersions;
import jakarta.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public final class MovieSessionPostController {

  private final MovieSessionStorage movieSessionStorage;
  private final MovieStorage movieStorage;
  private final CinemaHallStorage cinemaHallStorage;

  @PostMapping(ApiVersions.ONE + "/backoffice/movie-sessions")
  public ResponseEntity<MovieSession> registerMovieSession(@RequestBody Request request) {
    var cinemaHall = cinemaHallStorage.findById(request.cinemaHallId()).orElseThrow(EntityNotFoundException::new);

    var movie = movieStorage.findById(request.movieId()).orElseThrow(EntityNotFoundException::new);

    var language = new Language();
    language.setCode(request.languageCode());

    var movieSession = new MovieSession();
    movieSession.setMovie(movie);
    movieSession.setCinemaHall(cinemaHall);
    movieSession.setLanguage(language);
    movieSession.setStartTime(request.startTime());

    var endTime = request.startTime().plus(Duration.ofMinutes(movie.getDurationInMinutes()));
    movieSession.setEndTime(endTime);

    movieSession.setPriceInCents(request.priceInCents());
    movieSession.setStatus(MovieSessionStatus.UPCOMING);

    log.info("Create a list of tickets taking the info of the real seats at this moment");
    var tickets = cinemaHall.getSeats().stream().map(seat -> {
      var ticket = new Ticket();
      ticket.setMovieSession(movieSession);
      ticket.setMovieName(movie.getTitle());
      ticket.setMovieStartTime(movieSession.getStartTime());
      ticket.setCinemaHallName(cinemaHall.getName());
      ticket.setSeatNumber(seat.getRow() + seat.getNumber());
      ticket.setTicketPriceCents(movieSession.getPriceInCents());
      ticket.setDurationMinutes(movie.getDurationInMinutes());
      return ticket;
    }).collect(Collectors.toSet());
    movieSession.setTickets(tickets);

    var createdMovieSession = movieSessionStorage.save(movieSession);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdMovieSession);
  }

  public record Request(
      UUID movieId,
      Integer cinemaHallId,
      String languageCode,
      Instant startTime,
      Integer priceInCents) {}
}
