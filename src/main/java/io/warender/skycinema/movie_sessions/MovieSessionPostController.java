package io.warender.skycinema.movie_sessions;

import io.warender.skycinema.movies.Language;
import io.warender.skycinema.movies.MovieStorage;
import io.warender.skycinema.screening_rooms.ScreeningRoom;
import io.warender.skycinema.shared.ApiVersions;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public final class MovieSessionPostController {

  private final MovieSessionStorage movieSessionStorage;
  private final MovieStorage movieStorage;

  @PostMapping(ApiVersions.ONE + "/backoffice/movie-sessions")
  public ResponseEntity<MovieSession> registerMovieSession(@Valid @RequestBody Request request) {
    var screeningRoom = new ScreeningRoom();
    screeningRoom.setId(request.screeningRoomId());

    var movie = movieStorage.findById(request.movieId()).orElseThrow(EntityNotFoundException::new);

    var language = new Language();
    language.setCode(request.languageCode());

    var movieSession = new MovieSession();
    movieSession.setMovie(movie);
    movieSession.setScreeningRoom(screeningRoom);
    movieSession.setLanguage(language);
    movieSession.setStartTime(request.startTime());

    var endTime = request.startTime().plus(Duration.ofMinutes(movie.getDurationInMinutes()));
    movieSession.setEndTime(endTime);

    movieSession.setPriceInCents(request.priceInCents());
    movieSession.setStatus(MovieSessionStatus.UPCOMING);

    var createdMovieSession = movieSessionStorage.save(movieSession);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdMovieSession);
  }

  public record Request(
      UUID movieId,
      Integer screeningRoomId,
      String languageCode,
      Instant startTime,
      Integer priceInCents) {}
}
