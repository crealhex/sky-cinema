package io.warender.skycinema.seats;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.warender.skycinema.screening_rooms.ScreeningRoom;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "seats")
@NoArgsConstructor
@AllArgsConstructor
public final class Seat {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "screening_room_id")
  private ScreeningRoom screeningRoom;

  @Column(name = "allocated")
  private boolean allocated;

  @Column(name = "row")
  private String row;

  @Column(name = "number")
  private int number;

  public Seat(String row, Integer number, ScreeningRoom screeningRoom) {
    this.row = row;
    this.number = number;
    this.screeningRoom = screeningRoom;
  }
}
