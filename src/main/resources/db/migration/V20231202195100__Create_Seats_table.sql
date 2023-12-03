CREATE TABLE seats
(
  id                SERIAL PRIMARY KEY,
  screening_room_id INTEGER    NOT NULL,
  row               VARCHAR(1) NOT NULL,
  number            INTEGER    NOT NULL,
  CONSTRAINT fk_screening_room_id FOREIGN KEY (screening_room_id)
    REFERENCES screening_rooms (id) ON DELETE CASCADE
);
