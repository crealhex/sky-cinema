package io.warender.skycinema.movies;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "movies")
public final class Movie {

  @Id
  @UuidGenerator
  private UUID id;

  @Column(name = "title")
  private String title;

  @Column(name = "language")
  private String language;

  @Column(name = "duration")
  private Integer duration;

  @Column(name = "age_restriction")
  private Integer ageRestriction;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private MovieStatus status;
}
