package io.warender.skycinema.seats;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.warender.skycinema.screening_rooms.ScreeningRoomCapacityFull;
import io.warender.skycinema.screening_rooms.ScreeningRoomStorage;
import io.warender.skycinema.shared.ApiVersions;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public final class SeatPostController {

  private final SeatStorage seatStorage;
  private final ScreeningRoomStorage screeningRoomStorage;

  @Tag(name = "Seats")
  @PostMapping(ApiVersions.ONE + "/backoffice/screening-rooms/{screeningRoomId}/seats")
  public ResponseEntity<Seat> createSeat(
      @PathVariable Integer screeningRoomId, @RequestBody Request request) {
    var screeningRoom =
        screeningRoomStorage.findById(screeningRoomId).orElseThrow(EntityNotFoundException::new);

    if (screeningRoom.getCapacity() <= screeningRoom.getSeatsCount()) {
      throw new ScreeningRoomCapacityFull();
    }

    var seat = new Seat();
    seat.setScreeningRoom(screeningRoom);
    seat.setRow(request.row());
    seat.setNumber(request.number());

    try {
      return ResponseEntity.ok(seatStorage.save(seat));
    } catch (DataIntegrityViolationException e) {
      if (e.contains(PSQLException.class)) {
        PSQLException psqlException = (PSQLException) e.getMostSpecificCause();
        if ("23505".equals(psqlException.getSQLState())) {
          throw new RepeatedSeatInScreeningRoom();
        }
      }
      throw e;
    }
  }

  public record Request(String row, Integer number) {}
}
