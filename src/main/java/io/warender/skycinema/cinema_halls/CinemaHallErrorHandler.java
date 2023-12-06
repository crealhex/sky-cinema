package io.warender.skycinema.cinema_halls;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public final class CinemaHallErrorHandler {

  @ExceptionHandler(CinemaHallCapacityFull.class)
  public ResponseEntity<ProblemDetail> handleRoomCapacityFull(CinemaHallCapacityFull ex) {
    var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
  }

  @ExceptionHandler(RepeatedSeatInCinemaHall.class)
  public ResponseEntity<ProblemDetail> handleNotUniqueSeatPerScreeningRoom(RepeatedSeatInCinemaHall ex) {
    var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
  }
}
