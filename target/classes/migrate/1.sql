USE jstl;

CREATE TABLE users (
user_id INT NOT NULL AUTO_INCREMENT,
username VARCHAR(45) NOT NULL,
user_email VARCHAR(45) NOT NULL,
user_role VARCHAR(12) NOT NULL DEFAULT 'customer',
user_reg VARCHAR(45) NOT NULL,
salt BINARY(16) NOT NULL,
secret BINARY(32) NOT NULL,
user_active TINYINT NOT NULL DEFAULT 1,
PRIMARY KEY (user_id),
UNIQUE INDEX user_email_UNIQUE (user_email ASC) VISIBLE);

-- Husk at update jeres database version.
UPDATE properties
SET value = '1'
WHERE name = "version";
