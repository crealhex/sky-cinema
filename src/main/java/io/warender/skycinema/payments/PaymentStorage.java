package io.warender.skycinema.payments;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentStorage extends JpaRepository<Payment, UUID> {}
