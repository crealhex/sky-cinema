package io.warender.skycinema.auth;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "user_identities")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserIdentity {

  @Id @UuidGenerator private UUID id;

  private String email;
  private String password;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private UserRole role;

  private boolean verified;

  public UserIdentity(String email, String password, UserRole role, boolean verified) {
    this.email = email;
    this.password = password;
    this.role = role;
    this.verified = verified;
  }
}
