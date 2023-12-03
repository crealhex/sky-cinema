ALTER TABLE seats
  ADD CONSTRAINT unique_seat_per_screening_room UNIQUE (screening_room_id, row, number);
