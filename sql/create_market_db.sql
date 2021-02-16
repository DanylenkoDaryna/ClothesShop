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
    acc_status ENUM('LOCKED','UNLOCKED'),
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
INSERT INTO users VALUES(DEFAULT, 'admin', 'admin', 'Ivan', 'Ivanov', 'UNLOCKED', 0);
-- id = 2
INSERT INTO users VALUES(DEFAULT, 'client', 'client', 'Petr', 'Petrov', 'UNLOCKED', 1);
-- id = 3
INSERT INTO users VALUES(DEFAULT, 'петров', 'петров', 'Иван', 'Петров', 'UNLOCKED', 1);
-- id = 4
INSERT INTO users VALUES(DEFAULT, 'dead', 'inside', 'Dead', 'Inside', 'UNLOCKED', 0);

-- --------------------------------------------------------------
-- USER INFO
-- --------------------------------------------------------------
CREATE TABLE user_info(

	id INTEGER NOT NULL auto_increment PRIMARY KEY,

	country VARCHAR(20),
	birthday DATE,
	email VARCHAR(15),
	telephone VARCHAR(14),

	user_id INTEGER NOT NULL,

	FOREIGN KEY (user_id) REFERENCES users(id)

		ON DELETE CASCADE
		ON UPDATE RESTRICT
);

-- id = 2
INSERT INTO user_info VALUES(DEFAULT, 'Ukraine',
'1995-07-05', 'client@i.ua', '+380994516667', 2);
-- id = 3
INSERT INTO user_info VALUES(DEFAULT, 'Ukraine',
'1990-08-10', 'петров@i.ua', '+380974533337', 3);

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
	id INTEGER NOT NULL auto_increment PRIMARY KEY,
	name VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO catalogue VALUES(DEFAULT, 'For Women'); -- женская одежда
INSERT INTO catalogue VALUES(DEFAULT, 'For Men'); -- мужская одежда
INSERT INTO catalogue VALUES(DEFAULT, 'For Girls'); -- детская одежда для девочек
INSERT INTO catalogue VALUES(DEFAULT, 'For Boys'); -- детская одежда для мальчиков
-- INSERT INTO catalogue VALUES (DEFAULT,'For Baby');
-- --------------------------------------------------------------
-- CATEGORIES
-- categories names of catalogue
-- --------------------------------------------------------------
CREATE TABLE categories(
	id INTEGER NOT NULL auto_increment PRIMARY KEY,
	catalogue_id INTEGER NOT NULL,
	name VARCHAR(25) NOT NULL,
	FOREIGN KEY (catalogue_id) REFERENCES catalogue(id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT
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

	item_name VARCHAR(30) NOT NULL,

	price DOUBLE NOT NULL,

	release_date DATE NOT NULL,

	brand VARCHAR(15) NOT NULL,

	colour ENUM('RED','GREEN','BLUE', 'WHITE', 'BLACK', 'GREY', 'YELLOW', 'PINK'),

	category_id INTEGER NOT NULL REFERENCES categories(id)
		ON DELETE CASCADE ON UPDATE RESTRICT
	);

-- --------------------------------------------------------------
INSERT INTO items VALUES(DEFAULT, 'Bomber For Friends', 222.44, current_date,'For Friends','BLACK', 1);
INSERT INTO items VALUES(DEFAULT, 'Bomber Licolle', 500.99, current_date,'Licolle','BLUE', 1);
INSERT INTO items VALUES(DEFAULT, 'Jacket D&G', 59.99, current_date,'D&G','GREEN', 1);
INSERT INTO items VALUES(DEFAULT, 'Jacket D&G', 1059.99, current_date,'D&G','BLUE', 1);
INSERT INTO items VALUES(DEFAULT, 'Bomber Gucci', 128.99, current_date,'Gucci','GREY', 1);
INSERT INTO items VALUES(DEFAULT, 'Bomber Moscino', 360.99, current_date,'Moscino','PINK', 1);
INSERT INTO items VALUES(DEFAULT, 'Jacket Drupal', 359.99, current_date,'Drupal','BLUE', 1);
INSERT INTO items VALUES(DEFAULT, 'Bomber Programmer', 102.99, current_date,'Programmer','BLACK', 1);
INSERT INTO items VALUES(DEFAULT, 'Jacket ItGuru', 100.99, current_date,'ItGuru','PINK', 1);
INSERT INTO items VALUES(DEFAULT, 'Jacket Summertime', 1030.99, current_date,'Summertime','YELLOW', 1);
INSERT INTO items VALUES(DEFAULT, 'Jacket Lano', 1500.99, current_date,'Lano','BLACK', 1);
INSERT INTO items VALUES(DEFAULT, 'Bomber Cinderella', 600.99, current_date,'Cinderella','YELLOW', 1);
INSERT INTO items VALUES(DEFAULT, 'Bomber Dunder Mifflin', 400.99, current_date,'DM','GREEN', 1);
INSERT INTO items VALUES(DEFAULT, 'Jacket Office', 880.99, current_date,'Office','GREEN', 1);
INSERT INTO items VALUES(DEFAULT, 'Shirts Moscino', 40.00 , current_date,'Moscino','WHITE', 4);


-- --------------------------------------------------------------
-- PRODUCTS
-- --------------------------------------------------------------
CREATE TABLE products(

-- id has the INTEGER type (other name is INT), it is the primary key
	id INTEGER NOT NULL auto_increment PRIMARY KEY,

	product_name VARCHAR(30) NOT NULL,

	available INTEGER NOT NULL,

	product_size ENUM('XXS', 'XS', 'S', 'M', 'L', 'XL', 'XXL', 'XXXL') UNIQUE,

	item_id INTEGER NOT NULL REFERENCES items(id)
		ON DELETE CASCADE ON UPDATE RESTRICT

	);

-- --------------------------------------------------------------
INSERT INTO products VALUES(DEFAULT,'Bomber For Friends весна', 10, 'L',1);
INSERT INTO products VALUES(DEFAULT,'Bomber For Friends осень', 1, 'S', 1);
INSERT INTO products VALUES(DEFAULT,'Bomber Licolle Prestigue', 2, 'XL', 2);
INSERT INTO products VALUES(DEFAULT,'Jacket D&G classic black', 1, 'L',3);
INSERT INTO products VALUES(DEFAULT,'Jacket D&G VIP', 1, 'XL', 3);
INSERT INTO products VALUES(DEFAULT,'Jacket D&G VIP', 1, 'L', 4);
INSERT INTO products VALUES(DEFAULT,'Bomber Gucci Pride', 1, 'XXXL',5);
INSERT INTO products VALUES(DEFAULT,'Bomber Gucci Pride', 6, 'XXL',5);
INSERT INTO products VALUES(DEFAULT,'Bomber Moscino Emo Autumn', 1, 'S',6);
INSERT INTO products VALUES(DEFAULT,'Jacket Drupal Selestia', 1, 'XL',7);
INSERT INTO products VALUES(DEFAULT,'Bomber Programmer IT', 1, 'L',8);
INSERT INTO products VALUES(DEFAULT,'Jacket ItGuru Junior', 7, 'S',9);
INSERT INTO products VALUES(DEFAULT,'Jacket Summertime from Lana Del Rey', 1, 'XL',10);
INSERT INTO products VALUES(DEFAULT,'Jacket Summertime from Lana Del Rey', 1, 'XL',10);
INSERT INTO products VALUES(DEFAULT,'Jacket Lano Bo Banano', 1, 'L',11);
INSERT INTO products VALUES(DEFAULT,'Bomber Cinderella For Parties', 1, 'L',12);
INSERT INTO products VALUES(DEFAULT,'Bomber Dunder Mifflin Jim+Pam', 1, 'XL',13);
INSERT INTO products VALUES(DEFAULT,'Jacket Office s3', 1, 'L',14);
INSERT INTO products VALUES(DEFAULT,'Bomber Ferrari autumn', 8, 'XXS',15);
INSERT INTO products VALUES(DEFAULT,'Bomber Ferrari winter is coming', 1, 'XL',15);


-- --------------------------------------------------------------
-- MATERIALS
-- --------------------------------------------------------------

CREATE TABLE armadiodb.materials(

-- id has the INTEGER type (other name is INT), it is the primary key
	id INTEGER NOT NULL auto_increment PRIMARY KEY,

	material VARCHAR(30) NOT NULL,

    percent INTEGER,

	item_id INTEGER NOT NULL REFERENCES items(id)
	ON DELETE CASCADE ON UPDATE RESTRICT
	);

-- --------------------------------------------------------------
INSERT INTO materials VALUES(DEFAULT, 'cotton', 75, 1);
INSERT INTO materials VALUES(DEFAULT, 'silk', 25, 1);
INSERT INTO materials VALUES(DEFAULT, 'wool', 100, 1);
INSERT INTO materials VALUES(DEFAULT, 'silk',98, 2);
INSERT INTO materials VALUES(DEFAULT, 'cotton', 75, 2);
INSERT INTO materials VALUES(DEFAULT, 'silk', 25, 2);
INSERT INTO materials VALUES(DEFAULT, 'wool', 100, 3);
INSERT INTO materials VALUES(DEFAULT, 'corduroy',100, 3);
INSERT INTO materials VALUES(DEFAULT, 'velvet', 65, 4);
INSERT INTO materials VALUES(DEFAULT, 'silk', 35, 4);
INSERT INTO materials VALUES(DEFAULT, 'leather ', 100, 5);
INSERT INTO materials VALUES(DEFAULT, 'denim',100, 5);
INSERT INTO materials VALUES(DEFAULT, 'linen', 50, 6);
INSERT INTO materials VALUES(DEFAULT, 'suede', 50, 6);
INSERT INTO materials VALUES(DEFAULT, 'wool', 100, 7);
INSERT INTO materials VALUES(DEFAULT, 'silk',100, 8);
INSERT INTO materials VALUES(DEFAULT, 'cotton', 70, 9);
INSERT INTO materials VALUES(DEFAULT, 'corduroy', 30, 9);
INSERT INTO materials VALUES(DEFAULT, 'wool', 100, 10);
INSERT INTO materials VALUES(DEFAULT, 'velvet', 80, 10);
INSERT INTO materials VALUES(DEFAULT, 'silk', 20, 10);
INSERT INTO materials VALUES(DEFAULT, 'linen', 50, 11);
INSERT INTO materials VALUES(DEFAULT, 'leather', 25, 11);
INSERT INTO materials VALUES(DEFAULT, 'cotton', 25, 11);
INSERT INTO materials VALUES(DEFAULT, 'silk',100, 12);
INSERT INTO materials VALUES(DEFAULT, 'linen',100, 13);
INSERT INTO materials VALUES(DEFAULT, 'linen',100, 14);
INSERT INTO materials VALUES(DEFAULT, 'linen',100, 15);

-- --------------------------------------------------------------
-- IMAGES
-- --------------------------------------------------------------
CREATE TABLE images(

	id INTEGER NOT NULL auto_increment PRIMARY KEY,
	img_name VARCHAR(40) NOT NULL,
    product_id INTEGER NOT NULL REFERENCES armadiodb.products(id)
    ON DELETE CASCADE
	ON UPDATE RESTRICT
);


-- --------------------------------------------------------------
INSERT INTO armadiodb.images VALUES(DEFAULT, 'bomber_for_friends_blk.jpg',1);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'bomber_for_friends_pink.jpg',2);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'bomber_licolle_prestigue_bl.jpg',3);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'jacket_dg_cb_grn.jpg',4);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'jacket_dg_cb_pink.jpg',5);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'jacket_dg_vip_bl.jpg',6);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'bomber_gucci_pride_blk.jpg',7);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'bomber_gucci_pride_grey.jpg',8);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'bomber_moscino_ea_pink.jpg',9);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'jacket_drupal_selestia_bl.jpg',10);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'bomber_programmer_it_bl.jpg',11);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'jacket_itguru_junior_pink.jpg',12);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'jacket_summertime_ld_yell.jpg',13);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'jacket_summertime_ld_blk.jpg',14);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'jacket_lano_bb_blk.jpg',15);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'bomber_cinderella_fp_yell.jpg',16);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'bomber_dm_jp_bl.jpg',17);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'jacket_office_s3_grn.jpg',18);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'bomber_ferrari_autumn_red.jpg',19);
INSERT INTO armadiodb.images VALUES(DEFAULT, 'bomber_ferrari_wic_wh.jpg',20);
-- INSERT INTO armadiodb.images VALUES(DEFAULT, 'bomber_dg_sega_black.jpg',21);
-- INSERT INTO armadiodb.images VALUES(DEFAULT, 'bomber_dg_sega_green.jpeg',21);
-- INSERT INTO armadiodb.images VALUES(DEFAULT, 'bomber_dg_sega_pink.jpg',21);


-- --------------------------------------------------------------


SELECT * FROM users;
SELECT * FROM roles;
