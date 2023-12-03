package io.warender.skycinema.screening_rooms;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.warender.skycinema.shared.ApiVersions;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public final class ScreeningRoomPostController {

  private final ScreeningRoomStorage screeningRoomStorage;

  @Tag(name = "Screening Rooms")
  @Operation(
      summary = "Create a screening room",
      description =
          """
      Screening rooms are needed to allocate movie sessions and seats for customers. This will have
      a fixed capacity and status.

      Permissions:
      - ROLE_ADMIN
      """)
  @PostMapping(ApiVersions.ONE + "/backoffice/screening-rooms")
  public ResponseEntity<ScreeningRoom> createScreeningRoom(@RequestBody Request request) {
    var screeningRoom = new ScreeningRoom();
    screeningRoom.setCapacity(request.capacity());
    screeningRoom.setStatus(request.status());
    return ResponseEntity.status(HttpStatus.CREATED).body(screeningRoomStorage.save(screeningRoom));
  }

  public record Request(Integer capacity, ScreeningRoomStatus status) {}
}
