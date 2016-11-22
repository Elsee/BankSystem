/* DROPPERS START */
DROP FUNCTION customer_individual_creator(fname VARCHAR(30), lname VARCHAR(30), paspnum VARCHAR(10), s person_sex, bdate DATE, vatin VARCHAR(12), region VARCHAR(20), city VARCHAR(20), street VARCHAR(20), house VARCHAR(20), apartment VARCHAR(20), log VARCHAR(20), pass VARCHAR(30), amount NUMERIC, phone NUMERIC);
DROP FUNCTION address_creator(region VARCHAR(20), city VARCHAR(20), street VARCHAR(20), house VARCHAR(20), apartment VARCHAR(20));
DROP FUNCTION random_account_num_creator();
DROP FUNCTION select_individuals(VARCHAR, VARCHAR);
DROP FUNCTION make_login(VARCHAR, VARCHAR);
DROP TABLE bs_template;
DROP TABLE bs_customer_transaction_pattern;

DROP FUNCTION select_customer_categories(int);
DROP TABLE bs_transaction_category;

DROP FUNCTION make_transaction(VARCHAR, VARCHAR, NUMERIC);

DROP TRIGGER check_transaction_update ON bs_transaction;
DROP TRIGGER check_transaction_insert ON bs_transaction;
DROP FUNCTION transaction_checker();
DROP TABLE bs_transaction;

DROP TRIGGER check_account_update ON bs_account;
DROP TRIGGER check_account_insert ON bs_account;
DROP FUNCTION account_checker();
DROP TABLE bs_account;

DROP TRIGGER check_phone_update ON bs_phone;
DROP TRIGGER check_phone_insert ON bs_phone;
DROP FUNCTION phone_checker();
DROP TABLE bs_phone;

DROP TRIGGER check_business_update ON bs_business;
DROP TRIGGER check_business_insert ON bs_business;
DROP FUNCTION business_checker();
DROP TABLE bs_business;

DROP TRIGGER check_individual_update ON bs_individual;
DROP TRIGGER check_individual_insert ON bs_individual;
DROP FUNCTION individual_checker();
DROP TABLE bs_individual;

DROP TABLE bs_customer;

DROP TRIGGER check_user_update ON bs_user;
DROP TRIGGER check_user_insert ON bs_user;
DROP FUNCTION user_checker();
DROP TABLE bs_user;

DROP TRIGGER check_organization_update ON bs_organization;
DROP TRIGGER check_organization_insert ON bs_organization;
DROP FUNCTION organisation_checker();
DROP TABLE bs_organization;

DROP TABLE bs_employee;

DROP TABLE bs_office;

DROP TABLE bs_address;

DROP TRIGGER check_person_insert ON bs_person;
DROP TRIGGER check_person_update ON bs_person;
DROP FUNCTION person_checker();
DROP TABLE bs_person;

DROP TYPE user_type;
DROP TYPE customer_type;
DROP TYPE person_sex;

DROP FUNCTION select_db_error_message(VARCHAR);
DROP TRIGGER check_db_errors_update ON bs_db_errors;
DROP TRIGGER check_db_errors_insert ON bs_db_errors;
DROP FUNCTION db_errors_checker();
DROP TABLE bs_db_errors;

DROP FUNCTION alphanumeric_only(VARCHAR);
DROP FUNCTION alphabets_only(VARCHAR);
DROP FUNCTION numeric_only(VARCHAR);
/* DROPPERS END */