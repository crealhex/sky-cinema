package io.warender.skycinema.coworkers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CoworkerStorage extends JpaRepository<Coworker, UUID> {}
