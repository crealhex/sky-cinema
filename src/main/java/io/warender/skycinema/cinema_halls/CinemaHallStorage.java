package io.warender.skycinema.cinema_halls;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaHallStorage extends JpaRepository<CinemaHall, Integer> {}
