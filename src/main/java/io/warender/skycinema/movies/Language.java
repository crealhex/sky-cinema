package io.warender.skycinema.movies;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "languages")
public class Language {

  @Id private String code;

  @Column(name = "name")
  private String name;
}
