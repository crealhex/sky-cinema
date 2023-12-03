CREATE TABLE languages
(
  code VARCHAR(2) PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

ALTER TABLE movies
  DROP COLUMN language;

INSERT INTO languages (code, name)
VALUES ('en', 'English');
INSERT INTO languages (code, name)
VALUES ('es', 'Español');
