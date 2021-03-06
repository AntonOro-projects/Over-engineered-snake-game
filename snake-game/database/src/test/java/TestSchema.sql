CREATE DATABASE IF NOT EXISTS snekdb_test;
USE snekdb_test;
CREATE TABLE IF NOT EXISTS USERS(username VARCHAR(30) PRIMARY KEY, password VARCHAR(70) NOT NULL);
CREATE TABLE IF NOT EXISTS HIGHSCORE(id INT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(30) NOT NULL, score INT NOT NULL, add_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL, FOREIGN KEY (username) REFERENCES USERS(username) ON DELETE CASCADE, INDEX time_ind (score, add_timestamp));
