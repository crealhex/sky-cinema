package io.warender.skycinema.screening_rooms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningRoomStorage extends JpaRepository<ScreeningRoom, Integer> {}
