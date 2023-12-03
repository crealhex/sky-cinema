package io.warender.skycinema.screening_rooms;

import io.warender.skycinema.seats.Seat;
import jakarta.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "screening_rooms")
public final class ScreeningRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "max_capacity")
  private int setMaxCapacity;

  @Column(name = "seats_per_row")
  private int seatsPerRow;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private ScreeningRoomStatus status;

  @Column(name = "seats_count")
  private int seatsCount;

  @OneToMany(mappedBy = "screeningRoom")
  private Set<Seat> seats;
}
