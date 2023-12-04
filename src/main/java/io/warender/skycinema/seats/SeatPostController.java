package io.warender.skycinema.seats;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.warender.skycinema.screening_rooms.ScreeningRoomCapacityFull;
import io.warender.skycinema.screening_rooms.ScreeningRoomStorage;
import io.warender.skycinema.shared.ApiVersions;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public final class SeatPostController {

  private final SeatStorage seatStorage;
  private final ScreeningRoomStorage screeningRoomStorage;

  // TODO: Verify if the client is sending more seats per row than the max allowed by the screening
  @Tag(name = "Seats")
  @PostMapping(ApiVersions.ONE + "/backoffice/screening-rooms/{screeningRoomId}/seats")
  public ResponseEntity<List<Seat>> assignSeatsToScreeningRoom(
    @PathVariable Integer screeningRoomId, @RequestBody List<Request> request) {
    var screeningRoom =
        screeningRoomStorage.findById(screeningRoomId).orElseThrow(EntityNotFoundException::new);

    if (screeningRoom.getMaxCapacity() < request.size()) {
      return ResponseEntity.badRequest().build();
    }
    if (screeningRoom.getMaxCapacity() <= screeningRoom.getSeatsCount()) {
      throw new ScreeningRoomCapacityFull();
    }

    var seats = request.stream().map(r -> new Seat(r.row(), r.number(), screeningRoom)).toList();
    screeningRoom.getSeats().addAll(seats);

    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(seatStorage.saveAll(seats));
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

  public record Request(
    String row,
    Integer number) {}
}
