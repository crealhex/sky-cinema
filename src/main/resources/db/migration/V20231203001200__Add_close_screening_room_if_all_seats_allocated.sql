CREATE OR REPLACE FUNCTION close_screening_room_if_all_seats_allocated()
  RETURNS TRIGGER AS $$
BEGIN
  IF (SELECT COUNT(*) FROM seats WHERE screening_room_id = NEW.screening_room_id AND allocated = false) = 0 THEN
    UPDATE screening_rooms SET status = 'CLOSED' WHERE id = NEW.screening_room_id;
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER close_screening_room_if_all_seats_allocated
  AFTER UPDATE OF allocated ON seats
  FOR EACH ROW
EXECUTE PROCEDURE close_screening_room_if_all_seats_allocated();
