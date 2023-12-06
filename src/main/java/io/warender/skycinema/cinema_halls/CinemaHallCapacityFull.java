package io.warender.skycinema.cinema_halls;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public final class CinemaHallCapacityFull extends RuntimeException {

  public CinemaHallCapacityFull() {
    super("Screening room capacity is full");
  }
}
