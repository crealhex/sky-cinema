package io.warender.skycinema.concession_picks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcessionPickStorage extends JpaRepository<ConcessionPick, Long> {}
