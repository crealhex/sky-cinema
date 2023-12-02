package io.warender.skycinema.screening_rooms;

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
public final class ScreeningRoomGetController {

  private final ScreeningRoomStorage screeningRoomStorage;

  @Tag(name = "Screening Rooms")
  @GetMapping(ApiVersions.ONE + "/backoffice/screening-rooms/{screeningRoomId}")
  public ResponseEntity<ScreeningRoom> getScreeningRooms(@PathVariable Integer screeningRoomId) {
    return ResponseEntity.ok(
        screeningRoomStorage.findById(screeningRoomId).orElseThrow(EntityNotFoundException::new));
  }
}
