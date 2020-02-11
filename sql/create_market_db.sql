-- ==============================================================
-- ST4Example DB creation script for MySQL
-- ==============================================================

SET NAMES utf8;

DROP DATABASE IF EXISTS armadiodb;
CREATE DATABASE armadiodb CHARACTER SET utf8 COLLATE utf8_bin;

USE armadiodb;
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
INSERT INTO items VALUES(DEFAULT, 'Bomber For Friends', 222.44, current_date,'For Friends', 1);
INSERT INTO items VALUES(DEFAULT, 'Bomber Licolle', 500.99, current_date,'Licolle', 1);
INSERT INTO items VALUES(DEFAULT, 'Jacket D&G', 59.99, current_date,'D&G', 1);
INSERT INTO items VALUES(DEFAULT, 'Jacket D&G', 1059.99, current_date,'D&G', 1);
INSERT INTO items VALUES(DEFAULT, 'Bomber Gucci', 128.99, current_date,'Gucci', 1);
INSERT INTO items VALUES(DEFAULT, 'Bomber Moscino', 360.99, current_date,'Moscino', 1);
INSERT INTO items VALUES(DEFAULT, 'Jacket Drupal', 359.99, current_date,'Drupal', 1);
INSERT INTO items VALUES(DEFAULT, 'Bomber Programmer', 102.99, current_date,'Programmer', 1);
INSERT INTO items VALUES(DEFAULT, 'Jacket ItGuru', 100.99, current_date,'ItGuru', 1);
INSERT INTO items VALUES(DEFAULT, 'Jacket Summertime', 1030.99, current_date,'Summertime', 1);
INSERT INTO items VALUES(DEFAULT, 'Jacket Lano', 1500.99, current_date,'Lano', 1);
INSERT INTO items VALUES(DEFAULT, 'Bomber Cinderella', 600.99, current_date,'Cinderella', 1);
INSERT INTO items VALUES(DEFAULT, 'Bomber Dunder Mifflin', 400.99, current_date,'DM', 1);
INSERT INTO items VALUES(DEFAULT, 'Jacket Office', 880.99, current_date,'Office', 1);
INSERT INTO items VALUES(DEFAULT, 'Shirts Moscino', 40.00 , current_date,'Moscino', 4);


-- --------------------------------------------------------------
-- PRODUCTS
-- --------------------------------------------------------------
CREATE TABLE products(

-- id has the INTEGER type (other name is INT), it is the primary key
	id INTEGER NOT NULL auto_increment PRIMARY KEY,

	product_name VARCHAR(30) NOT NULL,

	available INTEGER NOT NULL,

	product_size ENUM('XXS', 'XS', 'S', 'M', 'L', 'XL', 'XXL', 'XXXL'),

	colour ENUM('RED','GREEN','BLUE', 'WHITE', 'BLACK', 'YELLOW', 'PINK'),


	item_id INTEGER NOT NULL REFERENCES items(id)
		ON DELETE CASCADE ON UPDATE RESTRICT

	);

-- --------------------------------------------------------------
INSERT INTO products VALUES(DEFAULT,'Bomber For Friends весна', 3, 'L', 'BLACK',1);
INSERT INTO products VALUES(DEFAULT,'Bomber For Friends осень', 1, 'S', 'PINK', 1);
INSERT INTO products VALUES(DEFAULT,'Bomber Licolle Prestigue', 2, 'XL', 'BLUE', 2);
INSERT INTO products VALUES(DEFAULT,'Jacket D&G classic black', 1, 'L', 'GREEN',3);
INSERT INTO products VALUES(DEFAULT,'Jacket D&G VIP', 1, 'XL', 'PINK', 3);
INSERT INTO products VALUES(DEFAULT,'Jacket D&G VIP', 1, 'L', 'BLUE', 4);
INSERT INTO products VALUES(DEFAULT,'Bomber Gucci Pride', 1, 'XXXL', 'BLACK',5);
INSERT INTO products VALUES(DEFAULT,'Bomber Gucci Pride', 1, 'XXL', 'BLACK',5);
INSERT INTO products VALUES(DEFAULT,'Bomber Moscino Emo Autumn', 1, 'S', 'PINK', 6);
INSERT INTO products VALUES(DEFAULT,'Jacket Drupal Selestia', 1, 'XL', 'BLUE', 7);
INSERT INTO products VALUES(DEFAULT,'Bomber Programmer IT', 1, 'L', 'BLACK',8);
INSERT INTO products VALUES(DEFAULT,'Jacket ItGuru Junior', 1, 'S', 'PINK', 9);
INSERT INTO products VALUES(DEFAULT,'Jacket Summertime from Lana Del Rey', 1, 'XL', 'YELLOW', 10);
INSERT INTO products VALUES(DEFAULT,'Jacket Summertime from Lana Del Rey', 1, 'XL', 'BLACK', 10);
INSERT INTO products VALUES(DEFAULT,'Jacket Lano Bo Banano', 1, 'L', 'BLACK',11);
INSERT INTO products VALUES(DEFAULT,'Bomber Cinderella For Parties', 1, 'L', 'YELLOW', 12);
INSERT INTO products VALUES(DEFAULT,'Bomber Dunder Mifflin Jim+Pam', 1, 'XL', 'BLUE', 13);
INSERT INTO products VALUES(DEFAULT,'Jacket Office s3', 1, 'L', 'GREEN',14);
INSERT INTO products VALUES(DEFAULT,'Bomber Ferrari autumn', 1, 'XXS', 'RED', 16);
INSERT INTO products VALUES(DEFAULT,'Bomber Ferrari winter is coming', 1, 'XL', 'WHITE', 17);


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
INSERT INTO materials VALUES(DEFAULT, 'cotton', 75, 3);
INSERT INTO materials VALUES(DEFAULT, 'silk', 25, 3);
INSERT INTO materials VALUES(DEFAULT, 'wool', 100, 4);
INSERT INTO materials VALUES(DEFAULT, 'corduroy',100, 5);
INSERT INTO materials VALUES(DEFAULT, 'velvet', 65, 6);
INSERT INTO materials VALUES(DEFAULT, 'silk', 35, 6);
INSERT INTO materials VALUES(DEFAULT, 'leather ', 100, 7);
INSERT INTO materials VALUES(DEFAULT, 'denim',100, 8);
INSERT INTO materials VALUES(DEFAULT, 'linen', 50, 9);
INSERT INTO materials VALUES(DEFAULT, 'suede', 50, 9);
INSERT INTO materials VALUES(DEFAULT, 'wool', 100, 10);
INSERT INTO materials VALUES(DEFAULT, 'silk',100, 11);
INSERT INTO materials VALUES(DEFAULT, 'cotton', 70, 12);
INSERT INTO materials VALUES(DEFAULT, 'corduroy', 30, 12);
INSERT INTO materials VALUES(DEFAULT, 'wool', 100, 13);
INSERT INTO materials VALUES(DEFAULT, 'velvet', 80, 14);
INSERT INTO materials VALUES(DEFAULT, 'silk', 20, 14);
INSERT INTO materials VALUES(DEFAULT, 'linen', 50, 16);
INSERT INTO materials VALUES(DEFAULT, 'leather', 25, 16);
INSERT INTO materials VALUES(DEFAULT, 'cotton', 25, 16);
INSERT INTO materials VALUES(DEFAULT, 'silk',100, 17);

-- --------------------------------------------------------------
-- IMAGES
-- --------------------------------------------------------------
CREATE TABLE images(

	id INTEGER NOT NULL auto_increment PRIMARY KEY,
	img_name VARCHAR(35) NOT NULL UNIQUE,
    product_id INTEGER NOT NULL REFERENCES armadiodb.products(id)
    ON DELETE CASCADE
	ON UPDATE RESTRICT
);


-- --------------------------------------------------------------
INSERT INTO armadiodb.images VALUES(DEFAULT, 'bomber_for_friends_blk',1);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'bomber_for_friends_pink',2);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'bomber_licolle_prestigue_bl',3);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'jacket_dg_cb_grn',4);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'jacket_dg_cb_pink',5);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'jacket_dg_vip_bl',6);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'bomber_gucci_pride_blk',7);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'bomber_moscino_ea_pink',9);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'jacket_drupal_selestia_bl',10);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'bomber_programmer_it_bl',11);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'jacket_itguru_junior_pink',12);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'jacket_summertime_ld_yell',13);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'jacket_summertime_ld_blk',14);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'jacket_lano_bb_blk',15);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'bomber_cinderella_fp_yell',16);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'bomber_dm_jp_bl',17);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'jacket_office_s3_grn',18);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'bomber_ferrari_autumn_red',19);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'bomber_ferrari_wic_wh',20);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'cardigan_moscino_es_red',21);


-- --------------------------------------------------------------


SELECT * FROM users;
SELECT * FROM roles;
