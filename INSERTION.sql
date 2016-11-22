/* INITIAL INSERTION */

/* Insertion in DB Errors */
INSERT INTO bs_db_errors VALUES ('ERRNO', 'Error Number must be like "E\d\d\d"'),
  ('E0000', 'Passport Number must be numeric'),
  ('E0001', 'VATIN must be numeric'),
  ('E0002', 'First Name must be alphabetical'),
  ('E0003', 'Last Name must be alphabetical'),
  ('E0004', 'Organization VATIN must be numeric'),
  ('E0005', 'Login must be alphanumeric'),
  ('E0006', 'Password must be alphanumeric'),
  ('E0007', 'Unavailable to write business customer into individuals'),
  ('E0008', 'Unavailable to write individual customer into business'),
  ('E0009', 'Phone must be numeric'),
  ('E0010', 'Account number must be numeric'),
  ('E0011', 'Insufficient funds');

/* Insertion in Person */
INSERT INTO bs_person VALUES (DEFAULT, 'Admin', 'Adminovich', '4270456978', 'M', '1992-03-07', '427045697823'),
  (DEFAULT, 'Tester', 'Testirovkin', '4271456978', 'F', '1982-04-07', '427145697854'),
  (DEFAULT, 'George', 'Abraham', '4272456978', 'M', '1957-05-07', '427245697857'),
  (DEFAULT, 'Ales', 'Keina', '4273456978', 'F', '1987-06-09', '427345697898'),
  (DEFAULT, 'Andrew', 'Gref', '4274456978', 'M', '1995-02-18', '427445697875'),
  (DEFAULT, 'Emir', 'Zherich', '4275456978', 'M', '1979-03-07', '427545697836'),
  (DEFAULT, 'Venya', 'Tresko', '4276456978', 'M', '1965-11-15', '427645697825'),
  (DEFAULT, 'Lenya', 'Abruman', '4277456978', 'M', '2001-09-11', '427745697827'),
  (DEFAULT, 'Trevor', 'Divos', '4278456978', 'M', '1983-12-07', '427845697885'),
  (DEFAULT, 'Gripa', 'Jar', '4279456978', 'F', '1981-07-20', '427945697887');

/* Insertion in Address */
INSERT INTO bs_address VALUES (DEFAULT, 'Tatarstan', 'Innopolis', 'Sportivnaya St', '108', '25'),
  (DEFAULT, 'Tatarstan', 'Innopolis', 'Sportivnaya St', '110.1', '12'),
  (DEFAULT, 'Tatarstan', 'Innopolis', 'Sportivnaya St', '110.3', '65'),
  (DEFAULT, 'Tatarstan', 'Innopolis', 'Sportivnaya St', '114.3', '60'),
  (DEFAULT, 'Tatarstan', 'Innopolis', 'Sportivnaya St', '100.1', '17'),
  (DEFAULT, 'Tatarstan', 'Innopolis', 'Sportivnaya St', '112.1', '5'),
  (DEFAULT, 'Tatarstan', 'Innopolis', 'Sportivnaya St', '100.2', '45'),
  (DEFAULT, 'Tatarstan', 'Innopolis', 'Sportivnaya St', '106', '30'),
  (DEFAULT, 'Tatarstan', 'Innopolis', 'Sportivnaya St', '107', DEFAULT),
  (DEFAULT, 'Tatarstan', 'Innopolis', 'Universitetskaya St', '1', DEFAULT),
  (DEFAULT, 'Tatarstan', 'Innopolis', 'Universitetskaya St', '7', DEFAULT);

/* Insertion in Customer, Individual, Account */

INSERT INTO bs_customer VALUES (DEFAULT, 'S', 1),
  (DEFAULT, 'I', 2);
INSERT INTO bs_individual VALUES (2,16);
INSERT INTO bs_account VALUES (DEFAULT, '4526365745298642', 1, '2016-11-21', NULL, TRUE, 2000),
  (DEFAULT, '4526365745298643', 1, '2016-11-21', NULL, TRUE, 3000);

/* make_transaction: Insertion in Transaction */
/* SELECT make_transaction('4526365745298643', '4526365745298642', 1501.5); */