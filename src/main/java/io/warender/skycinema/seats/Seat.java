package io.warender.skycinema.seats;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.warender.skycinema.cinema_halls.CinemaHall;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@Entity
@Table(name = "cinema_hall_seats")
@NoArgsConstructor
@AllArgsConstructor
public final class Seat {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "cinema_hall_id")
  private CinemaHall cinemaHall;

  @Column(name = "enabled")
  private boolean enabled;

  @Column(name = "row")
  private String row;

  @Column(name = "number")
  private int number;

  public Seat(String row, Integer number, CinemaHall cinemaHall) {
    this.row = row;
    this.number = number;
    this.cinemaHall = cinemaHall;
  }
}
