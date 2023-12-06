package io.warender.skycinema.cinema_halls;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.warender.skycinema.shared.ApiVersions;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public final class CinemaHallGetController {

  private final CinemaHallStorage cinemaHallStorage;

  @Tag(name = "Cinema Halls")
  @Operation(summary = "Get a screening room by id")
  @GetMapping(ApiVersions.ONE + "/backoffice/cinema-halls/{cinemaHallId}")
  public ResponseEntity<CinemaHall> getScreeningRooms(@PathVariable Integer cinemaHallId) {
    return ResponseEntity.ok(
        cinemaHallStorage.findById(cinemaHallId).orElseThrow(EntityNotFoundException::new));
  }
}
