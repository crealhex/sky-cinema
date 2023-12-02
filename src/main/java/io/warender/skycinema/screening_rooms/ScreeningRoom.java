package io.warender.skycinema.screening_rooms;

import io.warender.skycinema.seats.Seat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "screening_rooms")
public final class ScreeningRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "capacity")
  private int capacity;

  @Column(name = "status")
  private String status;

  @Column(name = "seats_count")
  private int seatsCount;

  @OneToMany(mappedBy = "screeningRoom")
  private Set<Seat> seats;
}
