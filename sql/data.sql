-- Active: 1690494586324@@127.0.0.1@3306@MyBnB
LOCK TABLES Addresses WRITE;
INSERT INTO Addresses VALUES (43.89142600705287, -79.30774320330502, 'L6C 2M4', 'Markham', 'Canada'), 
(43.77384767327859, -79.25546692883539, 'M1P 0B2', 'Toronto', 'Canada'),
(43.649340913053884, -79.38611473677513, 'M5H 0A3', 'Toronto', 'Canada'), 
(49.27160411341567, -123.18665450807939, 'V6Y 1H3', 'Richmond', 'Canada'),
(34.042473115901956, -118.27488774948841, 'CA 90015', 'Los Angeles' , 'USA'), 
(34.04439922410451, -118.27369047407063, 'CA 90015', 'Los Angeles', 'USA'),
(41.879209829711286, -87.63584784596632, 'IL 60606', 'Chicago', 'USA'), 
(34.04535569910752, -118.26312384158183, 'CA 90015', 'Los Angeles', 'USA'), 
(51.50971413389381, -0.1255453890018544, 'WC2N 4DH', 'London', 'UK'), 
(48.857296528781426, 2.352197082206577, '75004', 'Paris', 'France'), 
(43.76303974206687, 11.253947922473245, '50125', 'Firenze', 'Italy'), 
(40.73379665458717, -73.99854080178514, 'NY 10011', 'New York', 'USA'), 
(34.0899187402365, -118.36360049630225, 'CA 90046', 'Los Angeles', 'USA'),
(46.80510493748119, -71.24604289925429, 'G1N 3M8', 'Quebec', 'Canada'), 
(46.8116289090848, -71.2855564737058, 'G1M 3V7', 'Quebec', 'Canada'), 
(34.100097244255245, -118.35532034007466, 'CA 90046', 'Los Angeles', 'USA'), 
(13.755392044092686, 100.49650903167074, '10200', 'Bangkok', 'Thailand'), 
(33.99379046263081, -81.02632347594214, 'SC 29201', 'Columbia', 'USA'), 
(49.154700278737586, -123.14019494989762, 'V6Y 1H3', 'Richmond', 'Canada'); 
UNLOCK TABLES;

LOCK TABLES Users WRITE;
INSERT INTO Users VALUES (1, 'Kalen', 'Smith', 'ksmith', 1234, '3212423', '2001-01-01', '343343454', 'student', 43.89142600705287, -79.30774320330502, false), 
(2, 'Kanye', 'East', 'ye', 1234, '4355653', '1977-06-08', '234444534', 'artist', 34.04439922410451, -118.27369047407063, false), 
(3, 'Taylor', 'Swift', 'ts', '1234', '4346765', '1989-12-13', '434452344', 'singer', 34.042473115901956, -118.27488774948841, false),
(4, 'Big', 'Sean', 'bigs', '1234', '3498219', '1988-03-25', '444542445', 'writer', 51.50971413389381, -0.1255453890018544, false), 
(5, 'Jay', 'Rock', 'jayrock23', '1234', '2372378', '1985-03-31', '764364844', 'engineer', 41.879209829711286, -87.63584784596632, false), 
(6, 'Dominic', 'Fike', 'dominic1', '1234', '3278732', '2002-09-08', '234242108', 'comedian', 48.857296528781426, 2.352197082206577, false), 
(7, 'Jay', 'Chou', 'jchow', '1234', '7434892', '1979-01-18', '445979284', 'engineer', 49.27160411341567, -123.18665450807939, false),
(8, 'J', 'Cole', 'cole24', '1234', '9863438', '1985-01-28', '133249759', 'poet', 43.76303974206687, 11.253947922473245, false), 
(9, 'Sam', 'Smith', 'samsmith00', '1234', '3878479', '1992-05-19', '234534543', 'analyst', 43.77384767327859, -79.25546692883539, false), 
(10, 'Jack', 'Harlow', 'harlowboss', '1234', '6783720', '1998-03-13', '324423445', 'server', 43.649340913053884, -79.38611473677513, false), 
(11, 'Ariana', 'Grande', 'aagrande', '1234', '8378790', '1993-06-26', '434242234', 'teacher', 34.04535569910752, -118.26312384158183, false), 
(12, 'aa', 'bb', 'aabb', '1234', '2323482', '1990-01-01', '232387434', 'admin', 43.89142600705287, -79.30774320330502, true);
UNLOCK TABLES;

LOCK TABLES Renters WRITE;
INSERT INTO Renters VALUES (1, '732897912378'), (2, '278323989223'), (3, '238789123723'), (4, '827368292390'), (5, '762823892303'), (6, '238623823923'), (7, '237891237923'), (8, '576678907268');
UNLOCK TABLES;

LOCK TABLES Hosts WRITE;
INSERT INTO Hosts VALUES (7, 1), (8, 1), (9, 2), (10, 3), (11, 1);
UNLOCK TABLES;

 WRITE;
INSERT INTO Listings VALUES (1, 7, 'House', 40.73379665458717, -73.99854080178514), 
(2, 8, 'Apartment', 34.0899187402365, -118.36360049630225), 
(3, 9, 'House', 46.80510493748119, -71.24604289925429), 
(4, 9, 'Guest House', 46.8116289090848, -71.2855564737058), 
(5, 10, 'Hotel', 34.100097244255245, -118.35532034007466), 
(6, 10, 'Hotel', 13.755392044092686, 100.49650903167074), 
(7, 10, 'Apartment', 33.99379046263081, -81.02632347594214), 
(8, 11, 'House', 49.154700278737586, -123.14019494989762);
UNLOCK TABLES;

LOCK TABLES Amenity WRITE;
INSERT INTO Amenity VALUES ('Indoor fireplace', 1), ('BBQ grill', 1), ('Kitchen', 1), ('Heating', 1), ('Free parking', 1), ('Air conditioning', 2), ('Heating', 2), ('Hair dryer', 2), ('TV', 2), ('Smoke alarm', 2), ('Indoor fireplace', 3), ('BBQ grill', 3), ('Kitchen', 3​​), ('Heating', 3), ('Air conditioning', 3), ('Hair dryer', 3), ('TV', 3), ('Free parking', 3), ('Smoke alarm', 3), ('Waterfront', 3), ('Heating', 4), ('Air conditioning', 4), ('Hair dryer', 4), ('TV', 4), ('Free parking', 4), ('Smoke alarm', 4), ('Wifi', 4), ('Free parking', 5), ('Air conditioning', 5), ('Heating', 5), ('Hair dryer', 5), ('TV', 5), ('Breakfast', 5), ('Gym', 5), ('Smoke alarm', 5), ('Carbon monoxide alarm', 5), ('Wifi', 5), ('Air conditioning', 6), ('Heating', 6), ('Hair dryer', 6), ('TV', 6), ('Breakfast', 6), ('Gym', 6), ('Smoke alarm', 6), ('Carbon monoxide alarm', 6), ('Wifi', 6), ('Wifi', 7), ('Kitchen', 7), ('Washer', 7), ('Dryer', 7​​), ('Heating', 7), ('Pool', 7), ('Smoke alarm', 7), ('Crib', 7), ('Hair dryer', 7), ('Dedicated workspace', 7), ('Iron', 7​​), ('Wifi', 8), ('Kitchen', 8), ('Washer', 8), ('Dryer', 8​​), ('Air conditioning', 8), ('Heating', 8), ('Dedicated workspace', 8), ('TV', 8), ('Hair dryer', 7), ('Pool', 8), ('Hot tub', 8), ('Free parking', 8), ('Gym', 8), ('BBQ grill', 8), ('Indoor fireplace', 8), ('Beachfront', 8), ('Smoke alarm', 8);
UNLOCK TABLES;

LOCK TABLES Availabilities WRITE;
INSERT INTO Availabilities VALUES (1, '2023-09-01', 200), (1, '2023-09-02', 200), (1, '2023-09-03', 250), (1, '2023-09-04', 250), (1, '2023-09-05', 150), (2, '2023-10-01', 100), (2, '2023-10-02', 100), (2, '2023-10-25', 150), (2, '2023-10-26', 150), (2, '2023-10-27', 150), (3, '2023-09-01', 500), (3, '2023-09-02', 500), (3, '2023-09-03', 500), (3, '2023-10-26', 400), (3, '2023-10-27', 400), (6, '2023-12-30', 500), (6, '2023-12-31', 500), (8, '2023-09-05', 1000), (8, '2023-09-06', 1000), (8, '2023-09-07', 1000), (8, '2023-09-08', 400); 
UNLOCK TABLES;

LOCK TABLES Bookings WRITE;
INSERT INTO Bookings VALUES (1, 1, '2023-08-25', '2023-08-30', false, null), (2, 1, '2023-09-15', '2023-09-18', false, null), (3, 2, '2023-10-22', '2023-10-25', true, 9), (4, 3, '2023-11-01', '2023-11-31', false, null), (5, 4, '2023-07-11', '2023-08-02', true, 2), (6, 5, '2023-05-21', '2023-05-26', false, null), (6, 3, '2023-04-22', '2023-12-29', false, null), (7, 6, '2024-01-01', '2024-01-06', false, null), (8, 7, '2022-12-01', '2022-12-31', false, null); 
UNLOCK TABLES;

LOCK TABLES RenterComments WRITE;
INSERT INTO RenterComments VALUES (1, 7, 11, 'very nice guest', 5), (2, 5, 10, null, 4);
UNLOCK TABLES;

LOCK TABLES HostComments WRITE;
INSERT INTO HostComments VALUES (1, 10, 5, 'terrible hotel management!', 2), (2, 11, 7, 'very luxurious house! Host is super nice and helpful! will definitely book again.', 5);


UNLOCK TABLES;

LOCK TABLES ListingComments WRITE;
INSERT INTO listingComments VALUES (1, 5, 6, 'The facilities are good, rooms are clean.', 3), (2, 7, 8, 'very luxurious house! Love the beach view, hot tub and private pool in backyard.', 5);
UNLOCK TABLES;