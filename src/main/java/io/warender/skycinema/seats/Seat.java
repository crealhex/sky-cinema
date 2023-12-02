package io.warender.skycinema.seats;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.warender.skycinema.screening_rooms.ScreeningRoom;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "seats")
public final class Seat {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "screening_room_id")
  private ScreeningRoom screeningRoom;

  private String row;
  private int number;
}
