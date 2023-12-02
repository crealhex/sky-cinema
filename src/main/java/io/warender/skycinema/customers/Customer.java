package io.warender.skycinema.customers;

import io.warender.skycinema.auth.UserIdentity;
import io.warender.skycinema.auth.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "customers")
@Inheritance(strategy = InheritanceType.JOINED)
public final class Customer extends UserIdentity {

  private boolean verified = false;

  public Customer(String email, String password, UserRole role, boolean verified) {
    super(email, password, role, verified);
  }
}
