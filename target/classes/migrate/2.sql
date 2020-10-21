USE jstl;

CREATE TABLE cupcake (
cupcake_id INT NOT NULL AUTO_INCREMENT,
cupcake_name VARCHAR(45) NOT NULL,
PRIMARY KEY (cupcake_id));

-- Husk at update jeres database version.
UPDATE properties
SET value = '2'
WHERE name = "version";
