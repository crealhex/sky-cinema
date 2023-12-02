package io.warender.skycinema.screening_rooms;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public final class ScreeningRoomCapacityFull extends RuntimeException {

  public ScreeningRoomCapacityFull() {
    super("Screening room capacity is full");
  }
}
