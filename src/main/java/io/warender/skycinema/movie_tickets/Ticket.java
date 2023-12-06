package io.warender.skycinema.movie_tickets;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.warender.skycinema.movie_sessions.MovieSession;
import io.warender.skycinema.orders.Order;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "movie_tickets")
public final class Ticket {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "movie_name")
  private String movieName;

  @Column(name = "movie_start_time")
  private Instant movieStartTime;

  @Column(name = "cinema_hall_name")
  private String cinemaHallName;

  @Column(name = "seat_number")
  private String seatNumber;

  @Column(name = "ticket_price_cents")
  private Integer ticketPriceCents;

  @Column(name = "duration_minutes")
  private Integer durationMinutes;

  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "customer_order_id")
  private Order order;

  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "movie_session_id")
  private MovieSession movieSession;

  @Column(name = "allocated")
  private boolean allocated;

  @Column(name = "reserved")
  private boolean reserved;
}
