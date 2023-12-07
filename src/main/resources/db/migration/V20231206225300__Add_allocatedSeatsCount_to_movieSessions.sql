ALTER TABLE movie_sessions
  ADD COLUMN allocated_seats_count INTEGER NOT NULL DEFAULT 0;
