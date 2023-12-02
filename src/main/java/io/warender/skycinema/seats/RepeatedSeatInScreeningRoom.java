package io.warender.skycinema.seats;

public final class RepeatedSeatInScreeningRoom extends RuntimeException {

  public RepeatedSeatInScreeningRoom() {
    super("Seat has been assigned to this screening room already");
  }
}
