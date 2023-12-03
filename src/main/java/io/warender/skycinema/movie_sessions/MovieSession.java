package io.warender.skycinema.movie_sessions;

import io.warender.skycinema.movies.Language;
import io.warender.skycinema.movies.Movie;
import io.warender.skycinema.screening_rooms.ScreeningRoom;
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
  @JoinColumn(name = "screening_room_id")
  private ScreeningRoom screeningRoom;

  @ManyToOne
  @JoinColumn(name = "language_code")
  private Language language;

  @Column(name = "start_time")
  private Instant startTime;

  @Column(name = "end_time")
  private Instant endTime;

  @Column(name = "price_in_cents")
  private Integer priceInCents;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private MovieSessionStatus status;
}
