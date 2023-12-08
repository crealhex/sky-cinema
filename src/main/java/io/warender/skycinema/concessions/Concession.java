package io.warender.skycinema.concessions;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "concessions")
public final class Concession {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "price_cents")
  private int priceCents;

  @Column(name = "vegetarian")
  private boolean vegetarian;

  @Enumerated(EnumType.STRING)
  @Column(name = "category")
  private ConcessionCategory category;
}
