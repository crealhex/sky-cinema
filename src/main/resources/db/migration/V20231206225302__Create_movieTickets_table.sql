
CREATE TABLE IF NOT EXISTS movie_tickets
(
  id                 BIGSERIAL PRIMARY KEY,
  movie_name         VARCHAR(255) NOT NULL,
  movie_start_time   TIMESTAMP    NOT NULL,
  cinema_hall_name   VARCHAR(255) NOT NULL,
  seat_number        VARCHAR(10)  NOT NULL,
  ticket_price_cents INTEGER      NOT NULL,
  duration_minutes   INTEGER      NOT NULL,
  customer_order_id  BIGSERIAL    NOT NULL,
  movie_session_id   UUID         NOT NULL,
  CONSTRAINT fk_movie_tickets_customer_order_id
    FOREIGN KEY (customer_order_id) REFERENCES customer_orders (id) ON DELETE CASCADE,
  CONSTRAINT fk_movie_tickets_movie_session_id
    FOREIGN KEY (movie_session_id) REFERENCES movie_sessions (id) ON DELETE CASCADE
);
