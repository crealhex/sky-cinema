CREATE TABLE movie_language
(
  movie_id      UUID       NOT NULL,
  language_code VARCHAR(2) NOT NULL,
  PRIMARY KEY (movie_id, language_code),
  FOREIGN KEY (movie_id)
    REFERENCES movies (id) ON DELETE CASCADE,
  FOREIGN KEY (language_code)
    REFERENCES languages (code) ON DELETE CASCADE
);
