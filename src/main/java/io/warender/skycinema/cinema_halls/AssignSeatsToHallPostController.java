package io.warender.skycinema.cinema_halls;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.warender.skycinema.seats.Seat;
import io.warender.skycinema.seats.SeatStorage;
import io.warender.skycinema.shared.ApiVersions;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public final class AssignSeatsToHallPostController {

  private final SeatStorage seatStorage;
  private final CinemaHallStorage cinemaHallStorage;

  @Tag(name = "Seats")
  @PostMapping(ApiVersions.ONE + "/backoffice/cinema-halls/{cinemaHallId}/seats")
  public ResponseEntity<List<Seat>> assignSeatsToCinemaHall(
    @PathVariable Integer cinemaHallId, @RequestBody List<Request> request) {
    var cinemaHall =
        cinemaHallStorage.findById(cinemaHallId).orElseThrow(EntityNotFoundException::new);

    if (cinemaHall.getMaxCapacity() < request.size()) {
      return ResponseEntity.badRequest().build();
    }
    if (cinemaHall.getMaxCapacity() <= cinemaHall.getSeatsCount()) {
      throw new CinemaHallCapacityFull();
    }

    var seats = request.stream().map(r -> new Seat(r.row(), r.number(), cinemaHall)).toList();
    cinemaHall.getSeats().addAll(seats);

    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(seatStorage.saveAll(seats));
    } catch (DataIntegrityViolationException e) {
      if (e.contains(PSQLException.class)) {
        PSQLException psqlException = (PSQLException) e.getMostSpecificCause();
        if ("23505".equals(psqlException.getSQLState())) {
          throw new RepeatedSeatInCinemaHall();
        }
      }
      throw e;
    }
  }

  public record Request(
    String row,
    Integer number) {}
}
