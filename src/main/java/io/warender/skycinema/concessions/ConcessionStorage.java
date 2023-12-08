package io.warender.skycinema.concessions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcessionStorage extends JpaRepository<Concession, Long> {}
