package io.warender.skycinema.movie_sessions;

import io.warender.skycinema.shared.ApiVersions;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public final class MovieSessionGetController {

  private final MovieSessionStorage movieSessionStorage;

  @GetMapping(ApiVersions.ONE + "/backoffice/movie-sessions")
  public ResponseEntity<List<MovieSession>> getAllMovieSessions() {
    var movieSessions = movieSessionStorage.findAll();
    return ResponseEntity.ok(movieSessions);
  }

  @GetMapping(ApiVersions.ONE + "/backoffice/movie-sessions/{movieSessionId}")
  public ResponseEntity<MovieSession> getAllMovieSessions(@PathVariable UUID movieSessionId) {
    var movieSessions =
        movieSessionStorage.findById(movieSessionId).orElseThrow(EntityNotFoundException::new);
    return ResponseEntity.ok(movieSessions);
  }
}
