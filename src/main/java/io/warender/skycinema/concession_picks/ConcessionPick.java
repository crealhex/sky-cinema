package io.warender.skycinema.concession_picks;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.warender.skycinema.concessions.Concession;
import io.warender.skycinema.orders.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "concession_picks")
public final class ConcessionPick {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne
  @JoinColumn(name = "concession_id")
  private Concession concession;

  @Column(name = "quantity")
  private int quantity;
}
