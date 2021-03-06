/* Function checks string consists numeric only */
CREATE OR REPLACE FUNCTION numeric_only(VARCHAR) RETURNS BOOLEAN AS $$
BEGIN
  IF($1 ~ '^[0-9]+$') THEN
    RETURN TRUE;
  ELSE
    RETURN FALSE;
  END IF;
END;
$$
STRICT
LANGUAGE plpgsql IMMUTABLE;

/* Function checks string consists alphabets only */
CREATE OR REPLACE FUNCTION alphabets_only(VARCHAR) RETURNS BOOLEAN AS $$
BEGIN
  IF($1 ~ '^[a-zA-Z]+$') THEN
    RETURN TRUE;
  ELSE
    RETURN FALSE;
  END IF;
END;
$$
STRICT
LANGUAGE plpgsql IMMUTABLE;

/* Function checks string consists alphanumeric only */
CREATE OR REPLACE FUNCTION alphanumeric_only(VARCHAR) RETURNS BOOLEAN AS $$
BEGIN
  IF($1 ~ '^[a-zA-Z0-9]+$') THEN
    RETURN TRUE;
  ELSE
    RETURN FALSE;
  END IF;
END;
$$
STRICT
LANGUAGE plpgsql IMMUTABLE;

CREATE TABLE bs_db_errors(
  err_no VARCHAR(5) UNIQUE CHECK (char_length(err_no) = 5),
  err_message TEXT,
  PRIMARY KEY (err_no)
);

/* Trigger function for Organization table */
CREATE FUNCTION db_errors_checker()
  RETURNS TRIGGER AS $$
BEGIN
  IF (
    NOT alphanumeric_only(NEW.err_no)
  ) THEN
    RAISE EXCEPTION 'ERRNO';
    RETURN NULL;
  ELSE RETURN NEW;
  END IF;
END;
$$ LANGUAGE plpgsql;

/* Insert Trigger for Organisation Table */
CREATE TRIGGER check_db_errors_insert
BEFORE INSERT ON bs_db_errors FOR each ROW
EXECUTE PROCEDURE db_errors_checker();

/* Update Trigger for Organisation Table */
CREATE TRIGGER check_db_errors_update
BEFORE UPDATE ON bs_db_errors FOR each ROW
EXECUTE PROCEDURE db_errors_checker();

CREATE FUNCTION select_db_error_message(VARCHAR)
  RETURNS TABLE (
    err_message TEXT
  )
AS $BODY$
BEGIN
  RETURN QUERY
  SELECT err.err_message FROM bs_db_errors AS err WHERE err.err_no = $1;
END;
$BODY$ LANGUAGE plpgsql;

/* Male/Female Types creation */
CREATE TYPE person_sex AS ENUM ('M', 'F');
/* Business/Individual/Service Types creation */
CREATE TYPE customer_type AS ENUM ('B', 'I', 'S');
/* Employee/Customer Types creation */
CREATE TYPE user_type AS ENUM ('E', 'C');

/* Person entity table creation */
CREATE TABLE bs_person(
  person_id serial PRIMARY KEY,
  first_name VARCHAR(30) NOT NULL,
  last_name VARCHAR(30) NOT NULL,
  passport_number VARCHAR(10) UNIQUE NOT NULL CONSTRAINT passport_number_length CHECK (char_length(passport_number) = 10),
  sex person_sex NOT NULL,
  birth_date DATE NOT NULL,
  person_vatin VARCHAR(12) UNIQUE NOT NULL CONSTRAINT person_vatin_length CHECK (char_length(person_vatin) = 12)
);

/* Trigger function for Person table */
CREATE FUNCTION person_checker()
  RETURNS TRIGGER AS $$
BEGIN
  IF (
    NOT numeric_only(NEW.passport_number)
  ) THEN
    RAISE EXCEPTION 'E0000';
    RETURN NULL;
  ELSEIF (
    NOT numeric_only(NEW.person_vatin)
  ) THEN
    RAISE EXCEPTION 'E0001';
    RETURN NULL;
  ELSEIF (
    NOT alphabets_only(NEW.first_name)
  ) THEN
    RAISE EXCEPTION 'E0002';
    RETURN NULL;
  ELSEIF (
    NOT alphabets_only(NEW.last_name)
  ) THEN
    RAISE EXCEPTION 'E0003';
    RETURN NULL;
  ELSE RETURN NEW;
  END IF;

END;
$$ LANGUAGE plpgsql;

/* Insert Trigger for Person Table */
CREATE TRIGGER check_person_insert
BEFORE INSERT ON bs_person FOR each ROW
EXECUTE PROCEDURE person_checker();

/* Update Trigger for Person Table */
CREATE TRIGGER check_person_update
BEFORE UPDATE ON bs_person FOR each ROW
EXECUTE PROCEDURE person_checker();

/* Address entity table creation */
CREATE TABLE bs_address(
  address_id serial PRIMARY KEY,
  region VARCHAR(20) NOT NULL,
  city VARCHAR(20) NOT NULL,
  street VARCHAR(20) NOT NULL,
  house VARCHAR(20) NOT NULL,
  apartment VARCHAR(20) NOT NULL DEFAULT 0,
  UNIQUE (region, city, street, house, apartment)
);

/* Organisation entity table creation */
CREATE TABLE bs_organization(
  org_id serial PRIMARY KEY,
  org_vatin VARCHAR(12) UNIQUE NOT NULL CONSTRAINT org_vatin_length CHECK (char_length(org_vatin) = 10)
);

/* Trigger function for Organization table */
CREATE FUNCTION organisation_checker()
  RETURNS TRIGGER AS $$
BEGIN
  IF (
    NOT numeric_only(NEW.org_vatin)
  ) THEN
    RAISE EXCEPTION 'E0004';
    RETURN NULL;
  ELSE RETURN NEW;
  END IF;
END;
$$ LANGUAGE plpgsql;

/* Insert Trigger for Organisation Table */
CREATE TRIGGER check_organization_insert
BEFORE INSERT ON bs_organization FOR each ROW
EXECUTE PROCEDURE organisation_checker();

/* Update Trigger for Organisation Table */
CREATE TRIGGER check_organization_update
BEFORE UPDATE ON bs_organization FOR each ROW
EXECUTE PROCEDURE organisation_checker();

/* User entity table creation */
CREATE TABLE bs_user(
  user_id serial PRIMARY KEY,
  person_id int REFERENCES bs_person(person_id) NOT NULL ,
  user_login VARCHAR(20) UNIQUE NOT NULL,
  user_pass VARCHAR(30),
  type_user user_type NOT NULL
);

/* Trigger function for User table */
CREATE FUNCTION user_checker()
  RETURNS TRIGGER AS $$
BEGIN
  IF (
    NOT alphanumeric_only(NEW.user_login)
  ) THEN
    RAISE EXCEPTION 'E0005';
    RETURN NULL;
  ELSEIF (
    NOT alphanumeric_only(NEW.user_pass)
  ) THEN
    RAISE EXCEPTION 'E0006';
    RETURN NULL;
  ELSE RETURN NEW;
  END IF;
END;
$$ LANGUAGE plpgsql;

/* Insert Trigger for User Table */
CREATE TRIGGER check_user_insert
BEFORE INSERT ON bs_user FOR each ROW
EXECUTE PROCEDURE user_checker();

/* Update Trigger for User Table */
CREATE TRIGGER check_user_update
BEFORE UPDATE ON bs_user FOR each ROW
EXECUTE PROCEDURE user_checker();

/* Customer entity table creation */
CREATE TABLE bs_customer(
  customer_id serial PRIMARY KEY,
  type_customer customer_type NOT NULL,
  address_id int REFERENCES bs_address(address_id) NOT NULL
);

/* Individual Customer entity table creation */
CREATE TABLE bs_individual(
  customer_id int REFERENCES bs_customer(customer_id),
  person_id int REFERENCES bs_person(person_id) NOT NULL UNIQUE,
  PRIMARY KEY (customer_id)
);

/* Trigger function for Individual table */
CREATE FUNCTION individual_checker()
  RETURNS TRIGGER AS $$
DECLARE
  type_cus customer_type;
BEGIN
  select type_customer into type_cus
  from bs_customer customer
  where customer.customer_id = NEW.customer_id;
  IF (type_cus = 'I') THEN
    RETURN NEW;
  ELSE
    RAISE EXCEPTION 'E0007';
    RETURN NULL;
  END IF;
END;
$$ LANGUAGE plpgsql;

/* Insert Trigger for Individual Table */
CREATE TRIGGER check_individual_insert
BEFORE INSERT ON bs_individual FOR each ROW
EXECUTE PROCEDURE individual_checker();

/* Update Trigger for Individual Table */
CREATE TRIGGER check_individual_update
BEFORE UPDATE ON bs_individual FOR each ROW
EXECUTE PROCEDURE individual_checker();

/* Business Customer entity table creation */
CREATE TABLE bs_business(
  customer_id int REFERENCES bs_customer(customer_id),
  org_id int REFERENCES bs_organization(org_id) NOT NULL UNIQUE,
  PRIMARY KEY (customer_id)
);

/* Trigger function for Business table */
CREATE FUNCTION business_checker()
  RETURNS TRIGGER AS $$
DECLARE
  type_cus customer_type;
BEGIN
  select type_customer into type_cus
  from bs_customer customer
  where customer.customer_id = NEW.customer_id;
  IF (type_cus = 'B') THEN
    RETURN NEW;
  ELSE
    RAISE EXCEPTION 'E0008';
    RETURN NULL;
  END IF;
END;
$$ LANGUAGE plpgsql;

/* Insert Trigger for Business Table */
CREATE TRIGGER check_business_insert
BEFORE INSERT ON bs_business FOR each ROW
EXECUTE PROCEDURE business_checker();

/* Update Trigger for Business Table */
CREATE TRIGGER check_business_update
BEFORE UPDATE ON bs_business FOR each ROW
EXECUTE PROCEDURE business_checker();

/* Phone entity table creation */
CREATE TABLE bs_phone(
  phone_id serial PRIMARY KEY,
  customer_id int REFERENCES bs_customer(customer_id) NOT NULL ,
  phone_num VARCHAR(20) UNIQUE NOT NULL
);

/* Trigger function for Phone table */
CREATE FUNCTION phone_checker()
  RETURNS TRIGGER AS $$
BEGIN
  IF (
    NOT numeric_only(NEW.phone_num)
  ) THEN
    RAISE EXCEPTION 'E0009';
    RETURN NULL;
  ELSE RETURN NEW;
  END IF;
END;
$$ LANGUAGE plpgsql;

/* Insert Trigger for Phone Table */
CREATE TRIGGER check_phone_insert
BEFORE INSERT ON bs_phone FOR each ROW
EXECUTE PROCEDURE phone_checker();

/* Update Trigger for Phone Table */
CREATE TRIGGER check_phone_update
BEFORE UPDATE ON bs_phone FOR each ROW
EXECUTE PROCEDURE phone_checker();

/* Account entity table creation */
CREATE TABLE bs_account(
  account_id serial PRIMARY KEY,
  account_num VARCHAR(16) UNIQUE CHECK (char_length(account_num) = 16),
  customer_id int REFERENCES bs_customer(customer_id),
  open_date DATE NOT NULL,
  close_date DATE NULL,
  active BOOLEAN,
  balance NUMERIC NULL
);

/* Trigger function for Account table */
CREATE FUNCTION account_checker()
  RETURNS TRIGGER AS $$
BEGIN
  IF (
    NOT numeric_only(NEW.account_num)
  ) THEN
    RAISE EXCEPTION 'E0010';
    RETURN NULL;
  ELSE RETURN NEW;
  END IF;
END;
$$ LANGUAGE plpgsql;

/* Insert Trigger for Account Table */
CREATE TRIGGER check_account_insert
BEFORE INSERT ON bs_account FOR each ROW
EXECUTE PROCEDURE account_checker();

/* Update Trigger for Account Table */
CREATE TRIGGER check_account_update
BEFORE UPDATE ON bs_account FOR each ROW
EXECUTE PROCEDURE account_checker();

/* Transaction entity table creation */
CREATE TABLE bs_transaction(
  txn_id serial PRIMARY KEY,
  account_from_id int REFERENCES bs_account(account_id) NOT NULL,
  account_to_id int REFERENCES bs_account(account_id) NOT NULL,
  amount NUMERIC NOT NULL,
  txn_timestamp TIMESTAMP NOT NULL,
  CHECK (bs_transaction.account_to_id <> bs_transaction.account_from_id)
);

/* Trigger function for Transaction table */
CREATE FUNCTION transaction_checker()
  RETURNS TRIGGER AS $$
DECLARE
  balance NUMERIC;
BEGIN
  balance := (SELECT bsa.balance FROM bs_account AS bsa WHERE bsa.account_id = NEW.account_from_id);
  IF(balance >= NEW.amount) THEN
    RETURN NEW;
  ELSE
    RAISE EXCEPTION 'E0011';
    RETURN NULL;
  END IF;
END;
$$ LANGUAGE plpgsql;

/* Insert Trigger for Transaction Table */
CREATE TRIGGER check_transaction_insert
BEFORE INSERT ON bs_transaction FOR each ROW
EXECUTE PROCEDURE transaction_checker();

/* Update Trigger for Transaction Table */
CREATE TRIGGER check_transaction_update
BEFORE UPDATE ON bs_transaction FOR each ROW
EXECUTE PROCEDURE transaction_checker();

CREATE TABLE bs_template(
  customer_id int REFERENCES bs_customer(customer_id),
  txn_id int REFERENCES bs_transaction(txn_id),
  amount NUMERIC NOT NULL,
  PRIMARY KEY (customer_id, txn_id)
);

CREATE OR REPLACE FUNCTION select_customer_templates(int)
  RETURNS TABLE (
    account_from VARCHAR, account_to VARCHAR, amount VARCHAR
  ) AS
$func$
BEGIN
  RETURN QUERY
  SELECT * FROM
    (SELECT bsa.account_num FROM bs_account AS bsa WHERE bsa.account_id IN (SELECT bstr.account_to_id FROM bs_template AS bste LEFT JOIN bs_transaction AS bstr ON bste.txn_id = bstr.txn_id WHERE customer_id = $1)) AS a,
    (SELECT bsa.account_num FROM bs_account AS bsa WHERE bsa.account_id IN (SELECT bstr.account_from_id FROM bs_template AS bste LEFT JOIN bs_transaction AS bstr ON bste.txn_id = bstr.txn_id WHERE customer_id = $1)) AS b,
    (SELECT bstr.amount::VARCHAR FROM bs_template AS bste LEFT JOIN bs_transaction AS bstr ON bste.txn_id = bstr.txn_id WHERE customer_id = $1) AS c;
END;
$func$  LANGUAGE plpgsql;

/*  SQL Transaction method for Transaction table */
CREATE FUNCTION make_transaction(VARCHAR, VARCHAR, VARCHAR, BOOLEAN)
  RETURNS VOID AS $$
DECLARE
  from_id int;
  to_id int;
  customer int;
  active BOOL;
BEGIN
  active := (SELECT bsa.active FROM bs_account AS bsa WHERE  bsa.account_num = $2);
  IF (active) THEN
    from_id := (SELECT bsa.account_id FROM bs_account AS bsa WHERE bsa.account_num = $1);
    to_id := (SELECT bsa.account_id FROM bs_account AS bsa WHERE bsa.account_num = $2);
    INSERT INTO bs_transaction VALUES (DEFAULT, from_id, to_id, $3::NUMERIC, clock_timestamp());
    UPDATE bs_account SET balance = balance - $3::NUMERIC WHERE account_num = $1;
    UPDATE bs_account SET balance = balance + $3::NUMERIC WHERE account_num = $2;
    IF ($4) THEN
      customer := (SELECT bsa.customer_id FROM bs_account AS bsa WHERE bsa.account_num = $1);
      INSERT INTO bs_template VALUES (customer, (SELECT currval(pg_get_serial_sequence('bs_transaction','txn_id'))), $3::NUMERIC);
    END IF;
  END IF;
  exception when others then
  RAISE EXCEPTION 'E0016';
END;
$$ LANGUAGE plpgsql;

CREATE TABLE bs_txn_type (
  txn_type_id SERIAL,
  txn_type VARCHAR(20),
  PRIMARY KEY (txn_type_id)
);

/* Transaction category entity table creation */
CREATE TABLE bs_transaction_category(
  txn_cat_id serial PRIMARY KEY,
  customer_id int REFERENCES bs_customer(customer_id),
  txn_type_id int REFERENCES bs_txn_type
);

/* Selection of customer categories */
CREATE FUNCTION select_customer_categories(int)
  RETURNS TABLE (
    txn_cat_id int, txn_type VARCHAR
  ) AS
$func$
BEGIN
  RETURN QUERY
  SELECT bstc.txn_cat_id, bsttype.txn_type FROM bs_transaction_category AS bstc NATURAL JOIN bs_txn_type bsttype WHERE bstc.customer_id = $1 OR bstc.customer_id IN (SELECT bsc.customer_id FROM bs_customer AS bsc WHERE bsc.type_customer = 'S');
END;
$func$  LANGUAGE plpgsql;

/* Make Login */
CREATE FUNCTION make_login(VARCHAR, VARCHAR)
  RETURNS TABLE (
    user_id int,
    person_id int,
    type_user user_type
  )
AS $BODY$
BEGIN
  IF EXISTS (SELECT bsu.user_login FROM bs_user AS bsu WHERE bsu.user_login = $1 LIMIT 1) THEN
    IF EXISTS (SELECT bsu.user_pass FROM bs_user AS bsu WHERE bsu.user_login = $1 AND bsu.user_pass = $2 LIMIT 1) THEN
      RETURN QUERY
      SELECT bsu.user_id, bsu.person_id, bsu.type_user FROM bs_user AS bsu WHERE bsu.user_login = $1 AND bsu.user_pass = $2 LIMIT 1;
    ELSE RAISE EXCEPTION 'E0013';
    END IF;
  ELSE RAISE EXCEPTION 'E0012';
  END IF;
END;
$BODY$ LANGUAGE plpgsql;

/*Generator for accounts*/
CREATE OR REPLACE FUNCTION random_account_num_creator()
  RETURNS char(16) AS $$
DECLARE _serial char(16); _i int; _chars char(10) = '0123456789';
BEGIN
  _serial = '';
  FOR _i in 1 .. 16 LOOP
    _serial = _serial || substr(_chars, int4(floor(random() * length(_chars))) + 1, 1);
  END LOOP;
  RETURN _serial;
END;
$$ LANGUAGE plpgsql VOLATILE;

/*Address creator. Returns id*/
CREATE OR REPLACE FUNCTION address_creator(region VARCHAR(20), city VARCHAR(20), street VARCHAR(20), house VARCHAR(20), apartment VARCHAR(20))
  RETURNS SETOF int AS $BODY$
DECLARE
BEGIN
  RETURN QUERY SELECT address_id
               FROM bs_address
               WHERE bs_address.region = $1 AND bs_address.city = $2 AND bs_address.street = $3 AND bs_address.house = $4 AND bs_address.apartment = $5;
  IF NOT FOUND THEN
    RETURN QUERY INSERT INTO bs_address(region, city, street, house, apartment)
    VALUES ($1, $2, $3, $4, $5) RETURNING address_id;
  END IF;
END;
$BODY$ LANGUAGE plpgsql VOLATILE;

/*Creates customers (people)*/
CREATE OR REPLACE FUNCTION customer_individual_creator(fname VARCHAR(30), lname VARCHAR(30), paspnum VARCHAR(10), s VARCHAR(1), bdate VARCHAR, vatin VARCHAR(12), region VARCHAR(20), city VARCHAR(20), street VARCHAR(20), house VARCHAR(20), apartment VARCHAR(20), log VARCHAR(20), pass VARCHAR(30), amount VARCHAR, phone VARCHAR(20))
  RETURNS void AS $function$
BEGIN
  INSERT INTO bs_person (first_name, last_name, passport_number, sex, birth_date, person_vatin)
  VALUES ($1, $2, $3, $4::person_sex, $5::DATE, $6);
  INSERT INTO bs_customer(type_customer, address_id)
  VALUES ('I', (SELECT address_creator FROM address_creator($7, $8, $9, $10, $11)));
  INSERT INTO bs_individual(customer_id, person_id)
  VALUES ((SELECT currval(pg_get_serial_sequence('bs_customer','customer_id'))),(SELECT currval(pg_get_serial_sequence('bs_person','person_id'))));
  INSERT INTO bs_user(person_id, user_login, user_pass, type_user)
  VALUES ((SELECT currval(pg_get_serial_sequence('bs_person','person_id'))), $12, $13, 'C');
  INSERT INTO bs_account(account_num, customer_id, open_date, close_date, active, balance)
  VALUES ((random_account_num_creator()), (SELECT currval(pg_get_serial_sequence('bs_customer','customer_id'))), current_date, NULL, true, $14::NUMERIC);
  INSERT INTO bs_phone(customer_id, phone_num)
  VALUES ((SELECT currval(pg_get_serial_sequence('bs_customer','customer_id'))), $15);
  exception when others then
  RAISE EXCEPTION 'E0015';
END;
$function$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION select_individuals(VARCHAR, VARCHAR)
  RETURNS TABLE(cid INT, reg VARCHAR(20), ci VARCHAR(20), str VARCHAR(20), hou VARCHAR(20), apart VARCHAR(20), custy customer_type, pid INT, fname VARCHAR(30), lname VARCHAR(30), passnum VARCHAR(10), sekas person_sex, bdate DATE, pvatin VARCHAR(12), log VARCHAR, passw VARCHAR, phonen VARCHAR) AS $function$
BEGIN
  IF EXISTS (SELECT bsp.first_name, bsp.last_name
             FROM bs_person AS bsp
             WHERE bsp.first_name = $1 AND bsp.last_name = $2) THEN
    RETURN QUERY
    SELECT bsc.customer_id AS cid, bsa.region AS reg, bsa.city AS ci, bsa.street AS str, bsa.house AS hou, bsa.apartment AS apart, bsc.type_customer AS custy, bsp.person_id AS pid, bsp.first_name AS fname, bsp.last_name AS lname, bsp.passport_number AS passnum, bsp.sex AS sekas, bsp.birth_date AS bdate, bsp.person_vatin AS pvatin, bsu.user_login AS login, bsu.user_pass AS pass, bsph.phone_num AS phone
    FROM bs_customer AS bsc NATURAL JOIN bs_individual AS bsi NATURAL JOIN bs_person AS bsp NATURAL JOIN bs_address AS bsa NATURAL JOIN bs_user AS bsu NATURAL JOIN bs_phone AS bsph
    WHERE bsp.first_name = $1 AND bsp.last_name = $2;
  ELSE RAISE EXCEPTION 'E0014';
  END IF;
END;
$function$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION select_organizations(orgNum VARCHAR)
  RETURNS TABLE(cid INT, reg VARCHAR(20), ci VARCHAR(20), str VARCHAR(20), hou VARCHAR(20), apart VARCHAR(20), custy customer_type, oid INT, ovatin VARCHAR(10), phonen VARCHAR) AS $function$
BEGIN
  IF EXISTS (SELECT bso.org_vatin
             FROM bs_organization AS bso
             WHERE bso.org_vatin = $1) THEN
    RETURN QUERY
    SELECT bsc.customer_id AS cid, bsa.region AS reg, bsa.city AS ci, bsa.street AS str, bsa.house AS hou, bsa.apartment AS apart, bsc.type_customer AS custy, bso.org_id AS oid, bso.org_vatin AS ovatin, bsph.phone_num AS phone
    FROM bs_customer AS bsc NATURAL JOIN bs_business AS bsb NATURAL JOIN bs_organization AS bso NATURAL JOIN bs_address AS bsa NATURAL JOIN bs_phone AS bsph
    WHERE bso.org_vatin = $1;
  ELSE RAISE EXCEPTION 'E0019';
  END IF;
END;
$function$ LANGUAGE plpgsql;


/*Get customers account*/
CREATE OR REPLACE FUNCTION select_customer_accounts(INT)
  RETURNS TABLE(aid INTEGER, accnum VARCHAR(16), cust_id INTEGER, opendate DATE, closedate DATE, act BOOLEAN, bal NUMERIC) AS $$
BEGIN
  RETURN QUERY SELECT account_id, account_num, customer_id, open_date, close_date, active, balance
               FROM bs_account
               WHERE customer_id = $1;
END;
$$ LANGUAGE plpgsql;

/*Get customer id by person id*/
CREATE OR REPLACE FUNCTION get_customer_id(VARCHAR)
  RETURNS SETOF int AS $BODY$
BEGIN
  RETURN QUERY SELECT customer_id
               FROM bs_person NATURAL JOIN bs_individual NATURAL JOIN bs_customer
               WHERE person_id = $1::INTEGER;
END;
$BODY$ LANGUAGE plpgsql;

/*View transactions*/
CREATE OR REPLACE FUNCTION get_account_transactions(accid Int)
  RETURNS TABLE(t TIMESTAMP,acc_from_num VARCHAR, acc_to_num VARCHAR, val NUMERIC) AS $$
BEGIN
  RETURN QUERY SELECT txn_timestamp, b.account_num, c.account_num, amount FROM
    (SELECT * FROM bs_transaction WHERE account_from_id = accid OR account_to_id = accid) AS a,
    (SELECT * FROM bs_account) AS b,
    (SELECT * FROM bs_account) AS c
  WHERE b.account_id = a.account_from_id AND c.account_id = a.account_to_id
               ORDER BY txn_timestamp DESC;

END;
$$ LANGUAGE plpgsql;

/*Creates busines customer*/
CREATE OR REPLACE FUNCTION customer_business_creator(orgvatin VARCHAR(10), region VARCHAR(20), city VARCHAR(20), street VARCHAR(20), house VARCHAR(20), amount VARCHAR, phone VARCHAR, cat VARCHAR)
  RETURNS void AS $$
BEGIN
  INSERT INTO bs_organization(org_vatin)
  VALUES ($1);
  INSERT INTO bs_customer(type_customer, address_id)
  VALUES ('B', (SELECT address_creator FROM address_creator($2, $3, $4, $5, '0')));
  INSERT INTO bs_business(customer_id, org_id)
  VALUES ((SELECT currval(pg_get_serial_sequence('bs_customer','customer_id'))),(SELECT currval(pg_get_serial_sequence('bs_organization','org_id'))));
  INSERT INTO bs_account(account_num, customer_id, open_date, close_date, active, balance)
  VALUES ((random_account_num_creator()), (SELECT currval(pg_get_serial_sequence('bs_customer','customer_id'))), current_date, NULL, true, $6::NUMERIC);
  INSERT INTO bs_phone(customer_id, phone_num)
  VALUES ((SELECT currval(pg_get_serial_sequence('bs_customer','customer_id'))), $7);
  IF (cat <> '') THEN
    INSERT INTO bs_transaction_category(customer_id, txn_type_id)
    VALUES ((SELECT currval(pg_get_serial_sequence('bs_customer','customer_id'))), (SELECT txn_type_id FROM bs_txn_type WHERE txn_type = cat));
  END IF;
  exception when others then
  RAISE EXCEPTION 'E0017';
END;
$$ LANGUAGE plpgsql;

/*Account block*/
CREATE OR REPLACE FUNCTION change_account_activity(ac_num VARCHAR(16))
  RETURNS BOOLEAN AS $$
BEGIN
  IF ((SELECT active
       FROM bs_account
       WHERE account_num = $1) = FALSE) THEN
    UPDATE bs_account SET active = TRUE WHERE account_num = ac_num;
    UPDATE bs_account SET close_date = NULL WHERE account_num = ac_num;
    RETURN TRUE;
  ELSE
    UPDATE bs_account SET active = FALSE WHERE account_num = ac_num;
    UPDATE bs_account SET close_date = current_date WHERE account_num = ac_num;
    RETURN FALSE;
  END IF;
END;
$$ LANGUAGE plpgsql;

/*Account close date*/
CREATE OR REPLACE FUNCTION get_close_date(ac_num VARCHAR(16))
  RETURNS DATE AS $$

DECLARE closeDate DATE;
BEGIN
  SELECT close_date INTO closeDate
  FROM bs_account
  WHERE account_num = ac_num;
  RETURN closeDate;
END;
$$ LANGUAGE plpgsql;

/*Creates accounts*/
CREATE OR REPLACE FUNCTION account_creator(cid INT, amount DOUBLE PRECISION)
  RETURNS void AS $$
BEGIN
  INSERT INTO bs_account(account_num, customer_id, open_date, active, balance)
  VALUES ((random_account_num_creator()), cid, current_date, TRUE, amount::NUMERIC);
END;
$$ LANGUAGE plpgsql;

/*Returns transaction categories*/
CREATE OR REPLACE FUNCTION get_categories()
  RETURNS SETOF VARCHAR AS $BODY$
BEGIN
  RETURN QUERY SELECT txn_type
               FROM bs_txn_type;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION select_customer_transactions_by_outgoing(VARCHAR)
  RETURNS TABLE(
    txntype VARCHAR,
    summ NUMERIC,
    accout VARCHAR
  ) AS $BODY$
BEGIN
  RETURN QUERY
  SELECT
    CASE bsttype.txn_type IS NULL
    WHEN TRUE
      THEN 'others'
    ELSE bsttype.txn_type END
                     AS txntype,
    sum(bst.amount) AS summ,
    bsa1.account_num AS account
  FROM bs_account AS bsa
    LEFT JOIN (bs_transaction AS bst
    LEFT JOIN bs_account AS bsa1
      ON bst.account_to_id = bsa1.account_id)
    LEFT JOIN (bs_transaction_category AS bstc NATURAL JOIN bs_txn_type AS bsttype) ON bstc.customer_id = bsa1.customer_id
      ON bsa.account_id = bst.account_from_id
  WHERE bsa.account_num = $1
  GROUP BY bst.account_to_id,  txntype, account
  ORDER BY txntype;
END;
$BODY$ LANGUAGE plpgsql;

/*Updates customers (people)*/
CREATE OR REPLACE FUNCTION customer_individual_updator(cid INTEGER, fname VARCHAR(30), lname VARCHAR(30), paspnum VARCHAR(10), s VARCHAR(1), bdate VARCHAR, vatin VARCHAR(12), region VARCHAR(20), city VARCHAR(20), street VARCHAR(20), house VARCHAR(20), apartment VARCHAR(20), log VARCHAR(20), pass VARCHAR(30), phone VARCHAR(20))
  RETURNS void AS $function$
DECLARE
  personid INTEGER;
BEGIN
  SELECT person_id FROM bs_customer NATURAL JOIN bs_individual WHERE customer_id = cid INTO personid;

  UPDATE bs_person
  SET first_name=fname, last_name=lname, passport_number=paspnum, sex=s::person_sex, birth_date=bdate::DATE, person_vatin=vatin
  WHERE person_id = (SELECT person_id FROM bs_customer NATURAL JOIN bs_individual WHERE customer_id = cid);

  UPDATE bs_customer
  SET address_id = (SELECT address_creator FROM address_creator(region, city, street, house, apartment))
  WHERE customer_id = cid;

  UPDATE bs_user
  SET user_login = log, user_pass = pass
  WHERE  person_id = (SELECT person_id FROM bs_customer NATURAL JOIN bs_individual WHERE customer_id = cid);

  UPDATE bs_phone
  SET phone_num = phone
  WHERE customer_id = cid;
  exception when others then
  RAISE EXCEPTION 'E0020';
END;
$function$ LANGUAGE plpgsql;


/*Updates busines customer*/
CREATE OR REPLACE FUNCTION customer_business_updator(custid INT, orgvatin VARCHAR(10), region VARCHAR(20), city VARCHAR(20), street VARCHAR(20), house VARCHAR(20), phone VARCHAR)
  RETURNS void AS $$
BEGIN
  UPDATE bs_organization
  SET org_vatin = orgvatin
  WHERE org_id = (SELECT org_id FROM bs_customer NATURAL JOIN bs_business WHERE customer_id=custid);

  UPDATE bs_customer
  SET address_id = (SELECT address_creator FROM address_creator(region, city, street, house, '0'))
  WHERE customer_id = custid;

  UPDATE bs_phone
  SET phone_num = phone
  WHERE customer_id = custid;
  exception when others then
  RAISE EXCEPTION 'E0020';
END;
$$ LANGUAGE plpgsql;

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

INSERT INTO bs_txn_type VALUES (DEFAULT, 'food'),
  (DEFAULT, 'clothes'),
  (DEFAULT, 'entertainment'),
  (DEFAULT, 'medicine'),
  (DEFAULT, 'utilities'),
  (DEFAULT, 'travels'),
  (DEFAULT, 'booking'),
  (DEFAULT, 'service'),
  (DEFAULT, 'other');

SELECT (customer_individual_creator('Tester', 'Testirovkin', '1121111111', 'F', '1992-01-01', '112111111111', 'Tatarstan', 'Innopolis', 'Sportivnaya St', '108', '25', 'test', 'test', '2000', '1111111121'));
SELECT customer_business_creator('3698521477', 'Tatarstan', 'Innopolis', 'Universitetskaya St', '1', '150000', '789526436', 'food');
