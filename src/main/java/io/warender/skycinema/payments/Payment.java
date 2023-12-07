package io.warender.skycinema.payments;

import io.warender.skycinema.orders.Order;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@Entity
@Table(name = "payments")
public final class Payment {

  @Id
  @UuidGenerator
  private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(name = "user_document_type")
  private UserDocumentType userDocumentType;

  @Column(name = "national_id")
  private String nationalId;

  @Column(name = "customer_fullname")
  private String customerFullname;

  @Column(name = "customer_email")
  private String customerEmail;

  @Column(name = "terms_and_conditions")
  private boolean termsAndConditions;

  @ManyToOne
  @JoinColumn(name = "customer_order_id")
  private Order order;

  @Column(name = "amount_cents")
  private int amountCents;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private PaymentStatus status;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_method")
  private PaymentMethod paymentMethod;

  @CreationTimestamp
  @Column(name = "created_at")
  private Instant createdAt;
}
