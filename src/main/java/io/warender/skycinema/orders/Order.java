package io.warender.skycinema.orders;

import io.warender.skycinema.customers.Customer;
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
@Table(name = "customer_orders")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public final class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "client_session_id")
  private String clientSessionId;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @Column(name = "movie_session_id")
  private UUID movieSessionId;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Ticket> tickets;

  @Column(name = "total_amount_cents")
  private Integer totalAmountCents;

  @Column(name = "created_at")
  private Instant createdAt;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private OrderStatus status;
}
