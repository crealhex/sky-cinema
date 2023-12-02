package io.warender.skycinema.coworkers;

import io.warender.skycinema.auth.UserIdentity;
import io.warender.skycinema.auth.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "coworkers")
@Inheritance(strategy = InheritanceType.JOINED)
public final class Coworker extends UserIdentity {

  public Coworker(String email, String password, UserRole role, boolean verified) {
    super(email, password, role, verified);
  }
}
