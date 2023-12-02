package io.warender.skycinema.screening_rooms;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.warender.skycinema.shared.ApiVersions;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public final class ScreeningRoomPostController {

  private final ScreeningRoomStorage screeningRoomStorage;

  @Tag(name = "Screening Rooms")
  @PostMapping(ApiVersions.ONE + "/backoffice/screening-rooms")
  public ResponseEntity<ScreeningRoom> createScreeningRoom(@RequestBody Request request) {
    var screeningRoom = new ScreeningRoom();
    screeningRoom.setCapacity(request.capacity());
    screeningRoom.setStatus(request.status());
    return ResponseEntity.ok(screeningRoomStorage.save(screeningRoom));
  }

  public record Request(Integer capacity, String status) {}
}
