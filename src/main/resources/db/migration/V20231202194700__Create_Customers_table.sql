CREATE TABLE customers
(
  id UUID PRIMARY KEY,
  FOREIGN KEY (id) REFERENCES user_identities (id) ON DELETE CASCADE
);
