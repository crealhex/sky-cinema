package io.warender.skycinema.screening_rooms;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public final class ScreeningRoomErrorHandler {

  @ExceptionHandler(ScreeningRoomCapacityFull.class)
  public ResponseEntity<ProblemDetail> handleRoomCapacityFull(ScreeningRoomCapacityFull ex) {
    var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
  }
}
