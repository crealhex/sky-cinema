package io.warender.skycinema.movies;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.warender.skycinema.shared.ApiVersions;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@RequiredArgsConstructor
public final class MoviePostController {

  private final MovieStorage movieStorage;

  @Tag(name = "Movies")
  @PostMapping(ApiVersions.ONE + "/backoffice/movies")
  public ResponseEntity<Movie> createMovie(@RequestBody Request request) {
    var movie = new Movie();
    movie.setTitle(request.title());
    movie.setLanguage(request.language());
    movie.setDuration(request.duration());
    movie.setAgeRestriction(request.ageRestriction());
    movie.setStatus(request.status());
    return ResponseEntity.ok(movieStorage.save(movie));
  }

  public record Request(
    String title,
    String language,
    Integer duration,
    Integer ageRestriction,
    MovieStatus status
  ) {}
}
