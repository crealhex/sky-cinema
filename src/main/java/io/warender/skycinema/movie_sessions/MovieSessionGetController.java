package io.warender.skycinema.movie_sessions;

import io.warender.skycinema.shared.ApiVersions;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
