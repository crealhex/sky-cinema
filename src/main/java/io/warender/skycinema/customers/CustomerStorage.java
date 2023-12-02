package io.warender.skycinema.customers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerStorage extends JpaRepository<Customer, UUID> {}
