package io.warender.skycinema.seats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatStorage extends JpaRepository<Seat, Integer> {}
