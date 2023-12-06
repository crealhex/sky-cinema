package io.warender.skycinema.movie_tickets;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketStorage extends JpaRepository<Ticket, Long> {

  List<Ticket> findAllByMovieSessionId(UUID movieSessionId);
}
