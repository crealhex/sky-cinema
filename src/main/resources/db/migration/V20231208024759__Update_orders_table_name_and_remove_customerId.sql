ALTER TABLE customer_orders
  RENAME TO user_orders;
ALTER TABLE user_orders
  DROP COLUMN customer_id CASCADE;
