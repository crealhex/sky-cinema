package io.warender.skycinema.movies;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.warender.skycinema.shared.ApiVersions;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;
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
public final class MoviePostController {

  private final MovieStorage movieStorage;

  @Tag(name = "Movies")
  @Operation(
      summary = "Create a movie",
      description =
          """
    You can create movies with multiple languages assigned. You should provide a list with
    at least one language code. These languages will not affect the listing when creating
    a movie session.
    """)
  @PostMapping(ApiVersions.ONE + "/backoffice/movies")
  public ResponseEntity<Movie> createMovie(@Valid @RequestBody Request request) {
    log.info("[MOVIES] Creating movie: {}", request.title());
    var languages = request.languages.stream().map(languageCode -> {
      var language = new Language();
      language.setCode(languageCode);
      return language;
    }).collect(Collectors.toSet());

    var movie = new Movie();
    movie.setTitle(request.title());
    movie.setLanguages(languages);
    movie.setDurationInMinutes(request.durationInMinutes());
    movie.setAgeRestriction(request.ageRestriction());
    movie.setStatus(request.status());
    var createdMovie = movieStorage.save(movie);
    log.info("[MOVIES] Created movie: {}", createdMovie.getTitle());
    return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
  }

  public record Request(
      String title,
      @NotEmpty(message = "At least one language code must be provided")
      Set<String> languages,
      Integer durationInMinutes,
      Integer ageRestriction,
      MovieStatus status) {}
}
