ALTER TABLE seats
  RENAME TO cinema_hall_seats;

ALTER TABLE screening_rooms
  RENAME TO cinema_halls;

ALTER TABLE movie_sessions
  RENAME COLUMN screening_room_id TO cinema_hall_id;

ALTER TABLE cinema_hall_seats
  RENAME COLUMN screening_room_id TO cinema_hall_id;

ALTER TABLE cinema_hall_seats
  ADD COLUMN desirable BOOLEAN NOT NULL DEFAULT FALSE;

alter function close_screening_room_if_all_seats_allocated() rename to close_cinema_hall_if_all_seats_allocated;

CREATE OR REPLACE FUNCTION close_cinema_hall_if_all_seats_allocated() RETURNS trigger
  LANGUAGE plpgsql
AS
$$
BEGIN
  IF (SELECT COUNT(*) FROM cinema_hall_seats WHERE cinema_hall_id = NEW.cinema_hall_id AND allocated = false) = 0 THEN
    UPDATE cinema_halls SET status = 'CLOSED' WHERE id = NEW.cinema_hall_id;
  END IF;
  RETURN NEW;
END;
$$;

CREATE OR REPLACE FUNCTION increment_seats_count() RETURNS trigger
  LANGUAGE plpgsql
AS
$$
BEGIN
  UPDATE cinema_halls
  SET seats_count = seats_count + 1
  WHERE id = NEW.cinema_hall_id;
  RETURN NEW;
END;
$$;

ALTER TABLE movie_sessions ADD COLUMN reserved_seats_count INTEGER NOT NULL DEFAULT 0;

ALTER TABLE cinema_hall_seats RENAME COLUMN desirable TO reserved;
