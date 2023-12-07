package io.warender.skycinema.card_center;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardCenterStorage extends JpaRepository<CardCenter, UUID> {}
