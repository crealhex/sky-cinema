package io.warender.skycinema.movie_sessions;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieSessionStorage extends JpaRepository<MovieSession, UUID> {}
