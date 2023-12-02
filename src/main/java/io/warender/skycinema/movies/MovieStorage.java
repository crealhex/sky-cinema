package io.warender.skycinema.movies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MovieStorage extends JpaRepository<Movie, UUID> {}
