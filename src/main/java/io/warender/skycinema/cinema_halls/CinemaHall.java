package io.warender.skycinema.cinema_halls;

import io.warender.skycinema.seats.Seat;
import jakarta.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cinema_halls")
public final class CinemaHall {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name")
  private String name;

  @Column(name = "max_capacity")
  private int maxCapacity;

  @Column(name = "seats_per_row")
  private int seatsPerRow;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private CinemaHallStatus status;

  @Column(name = "seats_count")
  private int seatsCount;

  @OneToMany(mappedBy = "cinemaHall")
  private Set<Seat> seats;
}
