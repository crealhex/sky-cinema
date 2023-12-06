package io.warender.skycinema.cinema_halls;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.warender.skycinema.shared.ApiVersions;
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
public final class CinemaHallPostController {

  private final CinemaHallStorage cinemaHallStorage;

  @Tag(name = "Cinema Halls")
  @Operation(
      summary = "Create a screening room",
      description =
          """
      Cinema halls are needed to allocate movie sessions and seats for customers. This will have
      a fixed capacity and status.

      Permissions:
      - ROLE_ADMIN
      """)
  @PostMapping(ApiVersions.ONE + "/backoffice/cinema-halls")
  public ResponseEntity<CinemaHall> createScreeningRoom(@RequestBody Request request) {
    var screeningRoom = new CinemaHall();
    screeningRoom.setMaxCapacity(request.maxCapacity());
    screeningRoom.setStatus(request.status());
    screeningRoom.setSeatsPerRow(request.seatsPerRow());
    var createdScreeningRoom = cinemaHallStorage.save(screeningRoom);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdScreeningRoom);
  }

  public record Request(Integer maxCapacity, Integer seatsPerRow, CinemaHallStatus status) {}
}
