CREATE TABLE concessions
(
  id          BIGSERIAL PRIMARY KEY,
  name        VARCHAR(255) NOT NULL,
  price_cents INTEGER      NOT NULL,
  vegetarian  BOOLEAN      NOT NULL DEFAULT FALSE,
  category    VARCHAR(255) NOT NULL,
  CONSTRAINT uq_concessions_name
    UNIQUE (name)
);
