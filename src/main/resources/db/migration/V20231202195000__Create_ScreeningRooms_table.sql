CREATE TABLE screening_rooms
(
  id          SERIAL PRIMARY KEY,
  seats_count INTEGER NOT NULL DEFAULT 0,
  capacity    INTEGER NOT NULL,
  status      VARCHAR(50)      DEFAULT 'OPEN',
  CHECK (status IN ('OPEN', 'CLOSED', 'MAINTENANCE'))
);
