package io.warender.skycinema.seats;

public final class NotUniqueSeatPerScreeningRoom extends RuntimeException {

  public NotUniqueSeatPerScreeningRoom() {
    super("Seat has been assigned to this screening room already");
  }
}
