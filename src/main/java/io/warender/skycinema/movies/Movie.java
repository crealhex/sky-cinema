package io.warender.skycinema.movies;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@Entity
@Table(name = "movies")
public final class Movie {

  @Id @UuidGenerator private UUID id;

  @Column(name = "title")
  private String title;

  @ManyToMany
  @JoinTable(
      name = "movie_language",
      joinColumns = @JoinColumn(name = "movie_id"),
      inverseJoinColumns = @JoinColumn(name = "language_code"))
  private Set<Language> languages = new HashSet<>();

  @Column(name = "duration_in_minutes")
  private Integer durationInMinutes;

  @Column(name = "age_restriction")
  private Integer ageRestriction;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private MovieStatus status;
}
