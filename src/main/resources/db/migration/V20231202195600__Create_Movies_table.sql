CREATE TABLE movies
(
  id              UUID PRIMARY KEY,
  title           VARCHAR(255) NOT NULL,
  language        VARCHAR(255) NOT NULL,
  CHECK (language IN ('en', 'es')),
  duration        INTEGER      NOT NULL,
  age_restriction INTEGER      NOT NULL,
  status          VARCHAR(50) DEFAULT 'COMING_SOON',
  CHECK (status IN ('COMING_SOON', 'RELEASED', 'ARCHIVED'))
);
