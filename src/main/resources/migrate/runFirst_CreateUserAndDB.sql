DROP USER IF EXISTS 'testuser'@'localhost';
DROP DATABASE IF EXISTS jstl;


CREATE DATABASE IF NOT EXISTS jstl;
CREATE USER 'testuser'@'localhost';
grant INSERT, SELECT, DELETE, UPDATE privileges on jstl.* TO 'testuser'@'localhost';

-- CREATE DATABASE IF NOT EXISTS jstl;
-- CREATE USER 'testuser'@'localhost';
-- grant  INSERT, SELECT, DELETE, UPDATE, DROP, CREATE on jstl.* TO 'testuser'@'localhost';