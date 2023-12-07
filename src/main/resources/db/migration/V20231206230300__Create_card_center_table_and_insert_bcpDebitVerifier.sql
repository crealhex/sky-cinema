CREATE TABLE card_center
(
  id               UUID PRIMARY KEY,
  bin_number       VARCHAR(255) NOT NULL,
  issuing_bank     VARCHAR(255) NOT NULL,
  card_brand       VARCHAR(255) NOT NULL,
  card_type        VARCHAR(255) NOT NULL,
  iso_country_name VARCHAR(255) NOT NULL
);

INSERT INTO card_center (id, bin_number, issuing_bank, card_brand, card_type, iso_country_name)
VALUES ('0e8e4fec-106b-4149-8776-bafe22c26bca', '428079', 'Banco de Credito (BCP)', 'Visa', 'Debit', 'Peru');
