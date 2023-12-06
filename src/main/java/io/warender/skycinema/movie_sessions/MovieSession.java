package io.warender.skycinema.movie_sessions;

import io.warender.skycinema.cinema_halls.CinemaHall;
import io.warender.skycinema.movies.Language;
import io.warender.skycinema.movies.Movie;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@Entity
@Table(name = "movie_sessions")
public final class MovieSession {

  @Id
  @UuidGenerator
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "movie_id")
  private Movie movie;

  @ManyToOne
  @JoinColumn(name = "cinema_hall_id")
  private CinemaHall cinemaHall;

  @ManyToOne
  @JoinColumn(name = "language_code")
  private Language language;

  @Column(name = "start_time")
  private Instant startTime;

  @Column(name = "end_time")
  private Instant endTime;

  @Column(name = "price_in_cents")
  private Integer priceInCents;

  @Column(name = "reserved_seats_count")
  private int reservedSeatsCount;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private MovieSessionStatus status;
}
