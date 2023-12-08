package io.warender.skycinema.orders;

import io.warender.skycinema.concession_picks.ConcessionPick;
import io.warender.skycinema.movie_tickets.Ticket;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_orders")
public final class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "client_session_id")
  private String clientSessionId;

  @Column(name = "movie_session_id")
  private UUID movieSessionId;

  @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Ticket> tickets;

  @OneToMany(mappedBy = "order", orphanRemoval = true)
  private Set<ConcessionPick> concessions;

  @Column(name = "total_amount_cents")
  private Integer totalAmountCents;

  @Column(name = "created_at")
  private Instant createdAt;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private OrderStatus status;
}
