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
  err_message TEXT
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

/* Office entity table creation */
CREATE TABLE bs_office(
  office_id serial PRIMARY KEY,
  office_num int UNIQUE NOT NULL,
  address_id int REFERENCES bs_address(address_id)
);

/* Employee entity table creation */
CREATE TABLE bs_employee(
  office_id int REFERENCES bs_office(office_id),
  employee_id int PRIMARY KEY REFERENCES bs_person(person_id)
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
  person_id int REFERENCES bs_person(person_id),
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
  address_id int REFERENCES bs_address(address_id)
);

/* Individual Customer entity table creation */
CREATE TABLE bs_individual(
  customer_id int REFERENCES bs_customer(customer_id),
  person_id int REFERENCES bs_person(person_id),
  PRIMARY KEY (customer_id, person_id)
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
  org_id int REFERENCES bs_organization(org_id),
  PRIMARY KEY (customer_id, org_id)
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
  customer_id int REFERENCES bs_customer(customer_id),
  phone_num NUMERIC UNIQUE NOT NULL
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
  account_from_id int REFERENCES bs_account(account_id),
  account_to_id int REFERENCES bs_account(account_id),
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

/*  SQL Transaction method for Transaction table */
CREATE FUNCTION make_transaction(VARCHAR, VARCHAR, NUMERIC)
  RETURNS BOOLEAN AS $$
DECLARE
  from_id int;
  to_id int;
BEGIN
  from_id := (SELECT bsa.account_id FROM bs_account AS bsa WHERE bsa.account_num = $1);
  to_id := (SELECT bsa.account_id FROM bs_account AS bsa WHERE bsa.account_num = $2);
  INSERT INTO bs_transaction VALUES (DEFAULT, from_id, to_id, $3, clock_timestamp());
  UPDATE bs_account SET balance = balance - $3 WHERE account_num = $1;
  UPDATE bs_account SET balance = balance + $3 WHERE account_num = $2;
END;
$$ LANGUAGE plpgsql;

/* Transaction category entity table creation */
CREATE TABLE bs_transaction_category(
  txn_cat_id serial PRIMARY KEY,
  customer_id int REFERENCES bs_customer(customer_id),
  txn_type VARCHAR(20) NOT NULL
);

/* Selection of customer categories */
CREATE FUNCTION select_customer_categories(int)
  RETURNS TABLE (
    txn_cat_id int, txn_type VARCHAR
  ) AS
$func$
BEGIN
  RETURN QUERY
  SELECT * FROM bs_transaction_category AS bstc WHERE bstc.customer_id = $1 OR bstc.customer_id = 1;
END;
$func$  LANGUAGE plpgsql;

/* Customer transactions by category entity table creation */
CREATE TABLE bs_customer_transaction_pattern(
  customer_id int REFERENCES bs_customer(customer_id),
  account_from_id int REFERENCES bs_account(account_id),
  account_to_id int REFERENCES bs_account(account_id),
  txn_cat_id int REFERENCES bs_transaction_category(txn_cat_id),
  PRIMARY KEY (account_from_id, account_to_id)
);

CREATE TABLE bs_template(
  customer_id int REFERENCES bs_customer(customer_id),
  txn_id int REFERENCES bs_transaction(txn_id),
  amount NUMERIC NOT NULL,
  PRIMARY KEY (customer_id, txn_id)
);
