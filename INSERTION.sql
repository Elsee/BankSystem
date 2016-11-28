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
  ('E0011', 'Insufficient funds'),
   ('E0012', 'Incorrect login'),
 ('E0013', 'Incorrect pass'),
 ('E0014', 'No such person'),
 ('E0015', 'Person already exists or Wrong inputs'),
 ('E0016', 'Transaction failed'),
 ('E0017', 'Organisation already exists or Wrong inputs'),
 ('E0018', 'Unable to create transaction pattern'),
 ('E0019', 'No such orgnization'),
 ('E0020', 'Wrong inputs');

INSERT INTO bs_person VALUES (DEFAULT, 'Admin', 'Adminovich', '4270456978', 'M', '1992-03-07', '427045697823');
INSERT INTO bs_user VALUES (DEFAULT, 1, 'admin', 'admin', 'E');
INSERT INTO bs_address VALUES (DEFAULT, 'Tatarstan', 'Innopolis', 'Universitetskaya St', '1', DEFAULT);
INSERT INTO bs_customer VALUES (DEFAULT, 'S', 1);
INSERT INTO bs_transaction_category VALUES (DEFAULT, 1, 'food'),
  (DEFAULT, 1, 'clothes'),
  (DEFAULT, 1, 'entertainment'),
  (DEFAULT, 1, 'medicine'),
  (DEFAULT, 1, 'utilities'),
  (DEFAULT, 1, 'travels'),
  (DEFAULT, 1, 'booking'),
  (DEFAULT, 1, 'service'),
  (DEFAULT, 1, 'other');

SELECT (customer_individual_creator('Tester', 'Testirovkin', '1121111111', 'F', '1992-01-01', '112111111111', 'Tatarstan', 'Innopolis', 'Sportivnaya St', '108', '25', 'test', 'test', '2000', '1111111121'));
SELECT customer_business_creator('3698521477', 'Tatarstan', 'Innopolis', 'Universitetskaya St', '1', '150000', '789526436', 'food');



