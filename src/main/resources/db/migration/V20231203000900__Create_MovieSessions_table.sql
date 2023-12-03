CREATE TABLE movie_sessions
(
  id                UUID PRIMARY KEY,
  movie_id          UUID       NOT NULL,
  screening_room_id INTEGER    NOT NULL,
  language_code     VARCHAR(2) NOT NULL,
  start_time        TIMESTAMP  NOT NULL,
  end_time          TIMESTAMP  NOT NULL,
  price_in_cents    INTEGER    NOT NULL,
  status            VARCHAR(50) DEFAULT 'UPCOMING',
  CHECK (status IN ('UPCOMING', 'NOW_PLAYING', 'FINISHED')),
  CONSTRAINT fk_movie_sessions_movie_id
    FOREIGN KEY (movie_id)
      REFERENCES movies (id) ON DELETE CASCADE,
  CONSTRAINT fk_movie_sessions_screening_room_id
    FOREIGN KEY (screening_room_id)
      REFERENCES screening_rooms (id) ON DELETE CASCADE,
  CONSTRAINT fk_movie_sessions_language_code
    FOREIGN KEY (language_code)
      REFERENCES languages (code) ON DELETE CASCADE
);
