package io.warender.skycinema.seats;

public final class RepeatedSeatInCinemaHall extends RuntimeException {

  public RepeatedSeatInCinemaHall() {
    super("Seat has been assigned to this screening room already");
  }
}
