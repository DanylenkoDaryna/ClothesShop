-- ==============================================================
-- ST4Example DB creation script for MySQL
-- ==============================================================

SET NAMES utf8;

DROP DATABASE IF EXISTS marketdb;
CREATE DATABASE marketdb CHARACTER SET utf8 COLLATE utf8_bin;

USE marketdb;
-- --------------------------------------------------------------
-- ROLES
-- users roles
-- --------------------------------------------------------------
CREATE TABLE roles(

-- id has the INTEGER type (other name is INT), it is the primary key
	id INTEGER NOT NULL PRIMARY KEY,

-- name has the VARCHAR type - a string with a variable length
-- names values should not be repeated (UNIQUE)
	name VARCHAR(10) NOT NULL UNIQUE
);

-- this two commands insert data into roles table
-- --------------------------------------------------------------
-- ATTENTION!!!
-- we use ENUM as the Role entity, so the numeration must started
-- from 0 with the step equaled to 1
-- --------------------------------------------------------------
INSERT INTO roles VALUES(0, 'admin');
INSERT INTO roles VALUES(1, 'client');
INSERT INTO roles VALUES(2, 'guest');

-- --------------------------------------------------------------
-- USERS
-- --------------------------------------------------------------
CREATE TABLE users(

	id INTEGER NOT NULL auto_increment PRIMARY KEY,

-- 'UNIQUE' means logins values should not be repeated in login column of table
	login VARCHAR(20) NOT NULL UNIQUE,

-- not null string columns
	password VARCHAR(20) NOT NULL,
	first_name VARCHAR(20) NOT NULL,
	last_name VARCHAR(20) NOT NULL,

-- this declaration contains the foreign key constraint
-- role_id in users table is associated with id in roles table
-- role_id of user = id of role
	role_id INTEGER NOT NULL REFERENCES roles(id)

-- removing a row with some ID from roles table implies removing
-- all rows from users table for which ROLES_ID=ID
-- default value is ON DELETE RESTRICT, it means you cannot remove
-- row with some ID from the roles table if there are rows in
-- users table with ROLES_ID=ID
		ON DELETE CASCADE

-- the same as previous but updating is used insted deleting
		ON UPDATE RESTRICT
);

-- id = 1
INSERT INTO users VALUES(DEFAULT, 'admin', 'admin', 'Ivan', 'Ivanov',  0);
-- id = 2
INSERT INTO users VALUES(DEFAULT, 'client', 'client', 'Petr', 'Petrov', 1);
-- id = 3
INSERT INTO users VALUES(DEFAULT, 'петров', 'петров', 'Иван', 'Петров', 1);

-- --------------------------------------------------------------
-- GUESTS
-- --------------------------------------------------------------
CREATE TABLE guests(

	id INTEGER NOT NULL auto_increment PRIMARY KEY,

	first_name VARCHAR(20) NOT NULL,
	last_name VARCHAR(20) NOT NULL,
	basket_id INTEGER NOT NULL REFERENCES baskets(id)

);

-- --------------------------------------------------------------
-- STATUSES
-- statuses for orders
-- --------------------------------------------------------------
CREATE TABLE statuses(
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(10) NOT NULL UNIQUE
);

-- --------------------------------------------------------------
-- ATTENTION!!!
-- we use ENUM as the Status entity, so the numeration must started
-- from 0 with the step equaled to 1
-- --------------------------------------------------------------
INSERT INTO statuses VALUES(0, 'registered');
INSERT INTO statuses VALUES(1, 'paid');
INSERT INTO statuses VALUES(2, 'canceled');
INSERT INTO statuses VALUES(3, 'nonregistered');


-- --------------------------------------------------------------
-- CATALOGUE
-- catalogue names of menu
-- --------------------------------------------------------------
CREATE TABLE catalogue(
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(10) NOT NULL UNIQUE
);

INSERT INTO catalogue VALUES(1, 'For Women'); -- женская одежда
INSERT INTO catalogue VALUES(2, 'For Men'); -- мужская одежда
INSERT INTO catalogue VALUES(3, 'For Girls'); -- детская одежда для девочек
INSERT INTO catalogue VALUES(4, 'For Boys'); -- детская одежда для мальчиков

-- --------------------------------------------------------------
-- CATEGORIES
-- categories names of catalogue
-- --------------------------------------------------------------
CREATE TABLE categories(
	id INTEGER NOT NULL auto_increment PRIMARY KEY,
	catalogue_id INTEGER NOT NULL REFERENCES catalogue(id),
	name VARCHAR(25) NOT NULL
);

-- FOR WOMEN
-- --------------------------------------------------------------

INSERT INTO categories VALUES(DEFAULT, 1, 'Jackets'); -- куртки
INSERT INTO categories VALUES(DEFAULT, 1, 'Dresses'); -- Платья
INSERT INTO categories VALUES(DEFAULT, 1, 'Sweatshirts'); -- Кофты
INSERT INTO categories VALUES(DEFAULT, 1, 'Cardigans'); -- кардиганы
INSERT INTO categories VALUES(DEFAULT, 1, 'Jumpsuits'); -- Комбинезоны
INSERT INTO categories VALUES(DEFAULT, 1, 'Lingerie'); -- Белье
INSERT INTO categories VALUES(DEFAULT, 1, 'Tights'); -- колготки


-- FOR MEN
-- --------------------------------------------------------------

INSERT INTO categories VALUES(DEFAULT, 2, 'Jackets'); -- куртки
INSERT INTO categories VALUES(DEFAULT, 2, 'Hoodies'); -- Толстовки
INSERT INTO categories VALUES(DEFAULT, 2, 'Cardigans'); -- Кардиганы
INSERT INTO categories VALUES(DEFAULT, 2, 'Jumpers'); -- Джемперы
INSERT INTO categories VALUES(DEFAULT, 2, 'Socks'); -- Носки
INSERT INTO categories VALUES(DEFAULT, 2, 'Polos'); -- Поло


-- FOR GIRLS
-- --------------------------------------------------------------

INSERT INTO categories VALUES(DEFAULT, 3, 'Jackets'); -- Жакеты
INSERT INTO categories VALUES(DEFAULT, 3, 'Jumpers'); -- Джемперы
INSERT INTO categories VALUES(DEFAULT, 3, 'Playsuit'); -- комбинезоны
INSERT INTO categories VALUES(DEFAULT, 3, 'School Uniform'); -- Школьная форма
INSERT INTO categories VALUES(DEFAULT, 3, 'T-Shirts'); -- Футболки


-- FOR BOYS
-- --------------------------------------------------------------

INSERT INTO categories VALUES(DEFAULT, 4, 'Jackets'); -- Жакеты
INSERT INTO categories VALUES(DEFAULT, 4, 'Hoodies and Sweatshirts'); -- Толстовки и Кофты
INSERT INTO categories VALUES(DEFAULT, 4, 'Jumpers'); -- Джемперы
INSERT INTO categories VALUES(DEFAULT, 4, 'School Uniform'); -- Школьная форма
INSERT INTO categories VALUES(DEFAULT, 4, 'T-Shirts'); -- Футболки


-- --------------------------------------------------------------
-- ITEMS
-- --------------------------------------------------------------
CREATE TABLE items(

-- id has the INTEGER type (other name is INT), it is the primary key
	id INTEGER NOT NULL auto_increment PRIMARY KEY,

	product_name VARCHAR(30) NOT NULL,

	price DOUBLE NOT NULL,

	release_date DATE NOT NULL,

	brand VARCHAR(15) NOT NULL,

	category_id INTEGER NOT NULL REFERENCES categories(id)
		ON DELETE CASCADE ON UPDATE RESTRICT
	);

-- --------------------------------------------------------------
INSERT INTO items VALUES(DEFAULT, 'Бомбер For Friends', 222.44, current_date,'For Friends', 1);
INSERT INTO items VALUES(DEFAULT, 'Бомбер Licolle', 500.99, current_date,'Licolle', 1);
INSERT INTO items VALUES(DEFAULT, 'Штаны Moscino', 40.00 , current_date,'Moscino', 4);


-- --------------------------------------------------------------
-- PRODUCTS
-- --------------------------------------------------------------
CREATE TABLE products(

-- id has the INTEGER type (other name is INT), it is the primary key
	id INTEGER NOT NULL auto_increment PRIMARY KEY,

	product_name VARCHAR(30) NOT NULL,

	available INTEGER NOT NULL,

	product_size ENUM('XXS', 'XS', 'S', 'M', 'L', 'XL', 'XXL', 'XXXL'),

	colour ENUM('red','green','blue', 'white', 'black', 'yellow', 'pink'),


	item_id INTEGER NOT NULL REFERENCES items(id)
		ON DELETE CASCADE ON UPDATE RESTRICT

	);

-- --------------------------------------------------------------
INSERT INTO products VALUES(DEFAULT,'Бомбер For Friends весна', 3, 'L', 'black',1);
INSERT INTO products VALUES(DEFAULT,'Бомбер For Friends осень', 1, 'S', 'pink', 1);
INSERT INTO products VALUES(DEFAULT,'Штаны For Friends', 2, 'XL', 'blue', 2);


-- --------------------------------------------------------------
-- MATERIALS
-- --------------------------------------------------------------
CREATE TABLE materials(

-- id has the INTEGER type (other name is INT), it is the primary key
	id INTEGER NOT NULL auto_increment PRIMARY KEY,

	material VARCHAR(30) NOT NULL,

    percent INTEGER,

	product_id INTEGER NOT NULL REFERENCES products(id)
	ON DELETE CASCADE ON UPDATE RESTRICT
	);

-- --------------------------------------------------------------
INSERT INTO materials VALUES(DEFAULT, 'cotton', 75, 1);
INSERT INTO materials VALUES(DEFAULT, 'silk', 25, 1);
INSERT INTO materials VALUES(DEFAULT, 'wool', 100, 2);
INSERT INTO materials VALUES(DEFAULT, 'silk',98, 3);

-- --------------------------------------------------------------
-- test database:
-- --------------------------------------------------------------

SELECT * FROM users;
SELECT * FROM roles;
