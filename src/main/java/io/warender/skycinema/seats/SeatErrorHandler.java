package io.warender.skycinema.seats;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public final class SeatErrorHandler {

  @ExceptionHandler(RepeatedSeatInScreeningRoom.class)
  public ResponseEntity<ProblemDetail> handleNotUniqueSeatPerScreeningRoom(RepeatedSeatInScreeningRoom ex) {
    var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
  }
}
