package io.warender.skycinema.orders;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStorage extends JpaRepository<Order, Long> {

  Optional<Order> findByClientSessionId(String clientSessionId);
}
