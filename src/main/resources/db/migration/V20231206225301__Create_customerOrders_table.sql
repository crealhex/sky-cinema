
CREATE TABLE customer_orders
(
  id                 BIGSERIAL PRIMARY KEY,
  client_session_id  VARCHAR(255) NOT NULL,
  customer_id        UUID,
  movie_session_id   UUID         NOT NULL,
  total_amount_cents INTEGER      NOT NULL,
  created_at         TIMESTAMP    NOT NULL DEFAULT NOW(),
  status             VARCHAR(50)  NOT NULL DEFAULT 'PENDING',
  CONSTRAINT fk_orders_customer_id
    FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE,
  CONSTRAINT fk_orders_movie_session_id
    FOREIGN KEY (movie_session_id) REFERENCES movie_sessions (id) ON DELETE CASCADE
);
