CREATE OR REPLACE FUNCTION increment_seats_count()
  RETURNS TRIGGER AS $$
BEGIN
  UPDATE screening_rooms
  SET seats_count = seats_count + 1
  WHERE id = NEW.screening_room_id;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER increment_seats_count_trigger
  AFTER INSERT ON seats
  FOR EACH ROW
EXECUTE PROCEDURE increment_seats_count();
