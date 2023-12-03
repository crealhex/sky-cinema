ALTER TABLE screening_rooms RENAME COLUMN capacity TO max_capacity;
ALTER TABLE screening_rooms ADD COLUMN seats_per_row INTEGER NOT NULL DEFAULT 2;
