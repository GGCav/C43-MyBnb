DROP DATABASE IF EXISTS MyBnB;
CREATE DATABASE MyBnB DEFAULT CHARACTER SET = 'utf8mb4';
USE MyBnB;

DROP TABLE IF EXISTS Addresses;
CREATE TABLE Addresses(
    latitude DOUBLE,
    longitude DOUBLE,
    postal_code VARCHAR(255),
    city VARCHAR(255),
    country VARCHAR(255),
    PRIMARY KEY (latitude, longitude)
) COMMENT '';

DROP TABLE IF EXISTS Users;
CREATE TABLE Users(
    uid int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'Primary Key',
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    username VARCHAR(255),
    password VARCHAR(255),
    phone VARCHAR(255),
    date_of_birth DATE,
    SIN VARCHAR(255),
    occupation VARCHAR(255),
    latitude DOUBLE,
    longitude DOUBLE,
    Foreign Key (latitude, longitude) REFERENCES Addresses(latitude, longitude) ON DELETE CASCADE,
    is_admin BOOLEAN
) COMMENT '';

DROP TABLE IF EXISTS Renters;
CREATE TABLE Renters(
    uid int,
    Foreign Key (uid) REFERENCES Users(uid) ON DELETE CASCADE,
    card_number VARCHAR(255),
    primary key (uid)
) COMMENT '';

DROP TABLE IF EXISTS Hosts;
CREATE TABLE Hosts(
    uid int,
    Foreign Key (uid) REFERENCES Users(uid) ON DELETE CASCADE,
    number_of_listings int,
    primary key (uid)
) COMMENT '';

DROP TABLE IF EXISTS Listings;
CREATE TABLE Listings(
    lid int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'Primary Key',
    uid int,
    Foreign Key (uid) REFERENCES Hosts(uid) ON DELETE CASCADE,
    type VARCHAR(255),
    latitude DOUBLE,
    longitude DOUBLE,
    suggested_price FLOAT,
    Foreign Key (latitude, longitude) REFERENCES Addresses(latitude, longitude) ON DELETE CASCADE
) COMMENT '';

DROP TABLE IF EXISTS Amentity;
CREATE TABLE Amentity(
    type VARCHAR(255),
    lid int,
    Foreign Key (lid) REFERENCES Listings(lid) ON DELETE CASCADE,
    PRIMARY KEY (type, lid)
) COMMENT '';

DROP TABLE IF EXISTS Availabilities;
CREATE TABLE Availabilities(
    lid int,
    Foreign Key (lid) REFERENCES Listings(lid) ON DELETE CASCADE,
    Date DATE,
    price FLOAT,
    PRIMARY KEY (lid, DATE)
) COMMENT '';

DROP TABLE IF EXISTS Bookings;
CREATE TABLE Bookings(
    lid int,
    Foreign Key (lid) REFERENCES Listings(lid) ON DELETE CASCADE,
    uid int,
    Foreign Key (uid) REFERENCES Renters(uid) ON DELETE CASCADE,
    start_date DATE,
    end_date DATE,
    cancelled BOOLEAN,
    cancelled_by int,
    PRIMARY KEY (lid, uid, start_date, end_date)
) COMMENT '';

DROP TABLE IF EXISTS RenterComments;
CREATE TABLE RenterComments(
    cid int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'Primary Key',
    uid1 int,
    Foreign Key (uid1) REFERENCES Renters(uid) ON DELETE CASCADE,
    uid2 int,
    Foreign Key (uid2) REFERENCES Hosts(uid) ON DELETE CASCADE,
    content VARCHAR(255),
    rating int
) COMMENT '';

DROP TABLE IF EXISTS HostComments;
CREATE TABLE HostComments(
    cid int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'Primary Key',
    uid1 int,
    Foreign Key (uid1) REFERENCES Hosts(uid) ON DELETE CASCADE,
    uid2 int,
    Foreign Key (uid2) REFERENCES Renters(uid) ON DELETE CASCADE,
    content VARCHAR(255),
    rating int
) COMMENT '';

DROP TABLE IF EXISTS ListingComments;
CREATE TABLE ListingComments(
    cid int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'Primary Key',
    uid int,
    Foreign Key (uid) REFERENCES Renters(uid) ON DELETE CASCADE,
    lid int,
    Foreign Key (lid) REFERENCES Listings(lid) ON DELETE CASCADE,
    content VARCHAR(255),
    rating int
) COMMENT '';
