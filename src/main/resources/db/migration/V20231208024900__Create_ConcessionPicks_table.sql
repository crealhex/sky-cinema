CREATE TABLE concession_picks
(
  id            BIGSERIAL PRIMARY KEY,
  order_id      BIGINT  NOT NULL,
  concession_id BIGINT  NOT NULL,
  quantity      INTEGER NOT NULL DEFAULT 1,
  CONSTRAINT fk_concession_picks_order_id
    FOREIGN KEY (order_id) REFERENCES user_orders (id) ON DELETE CASCADE,
  CONSTRAINT fk_concession_picks_concession_id
    FOREIGN KEY (concession_id) REFERENCES concessions (id) ON DELETE CASCADE,
  CONSTRAINT uq_concession_picks_order_id_concession_id
    UNIQUE (order_id, concession_id)
);
