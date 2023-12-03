ALTER TABLE movies
  ADD CONSTRAINT uq_movies_title UNIQUE (title);
ALTER TABLE movies
  RENAME COLUMN duration TO duration_in_minutes;
