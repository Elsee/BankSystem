/*Individuals SEARCH*/
CREATE OR REPLACE FUNCTION findIndividuals(VARCHAR, VARCHAR)
    RETURNS TABLE(cid INT, firstname VARCHAR(30), lastname VARCHAR(30), passnum VARCHAR(10)) AS $$
BEGIN
    RETURN QUERY SELECT customer_id, first_name, last_name, passport_number
            FROM bs_customer NATURAL JOIN bs_individual NATURAL JOIN bs_person
            WHERE first_name = $1 AND last_name = $2;
END;
$$ LANGUAGE plpgsql;

/*Get customers account*/
CREATE OR REPLACE FUNCTION getCustomerAccounts(cid INT)
    RETURNS TABLE(accnum VARCHAR(16), opendate DATE, closedate DATE, act BOOLEAN, bal NUMERIC) AS $$
BEGIN
    RETURN QUERY SELECT account_num, open_date, close_date, active, balance
                 FROM bs_account
                 WHERE customer_id = $1;
END;
$$ LANGUAGE plpgsql;

/*SELECT * FROM getCustomerAccounts(1);*/

/*Get personal info of customer*/
CREATE OR REPLACE FUNCTION get_customer_ind_info(cid INT)
    RETURNS TABLE(cust_id INT, fname VARCHAR(30), lname VARCHAR(30), paspnum VARCHAR(10), s person_sex, bdate DATE, vatin VARCHAR(12)) AS $$
BEGIN
    RETURN QUERY SELECT customer_id, first_name, last_name, passport_number, sex, birth_date, person_vatin
                 FROM bs_person NATURAL JOIN bs_individual NATURAL JOIN bs_customer
                 WHERE customer_id = $1;
END;
$$ LANGUAGE plpgsql;

/*SELECT * FROM get_customer_ind_info(1);*/

/*Get info about organization*/
CREATE OR REPLACE FUNCTION get_customer_b_info(cid INT)
    RETURNS TABLE(oid INT, orgvatin VARCHAR(12)) AS $$
BEGIN
    RETURN QUERY SELECT org_id, org_vatin
                 FROM bs_organization NATURAL JOIN bs_business NATURAL JOIN bs_customer
                 WHERE customer_id = $1;
END;
$$ LANGUAGE plpgsql;

/*SELECT * FROM get_customer_b_info(18);*/

/*Update personal info of customer*/
CREATE OR REPLACE FUNCTION update_customer_ind_person_info(cid INT, fname VARCHAR(30), lname VARCHAR(30), paspnum VARCHAR(10), s person_sex, bdate DATE, vatin VARCHAR(12))
    RETURNS void AS $$
BEGIN
    UPDATE bs_person
        SET first_name=fname, last_name=lname, passport_number=paspnum, sex=s, birth_date=bdate, person_vatin=vatin
        WHERE person_id = (SELECT person_id FROM bs_customer NATURAL JOIN bs_individual WHERE customer_id = cid);
END;
$$ LANGUAGE plpgsql;

/*SELECT (update_customer_ind_person_info(17, 'Sveta', 'Testest', '1111111112', 'F', '1990-03-05', '111111111112'));*/

/*UPDATE organization info*/
CREATE OR REPLACE FUNCTION update_customer_b_org_info(cid INT, vatin VARCHAR(10))
    RETURNS void AS $$
BEGIN
    UPDATE bs_organization
    SET org_vatin = vatin
    WHERE org_id = (SELECT org_id FROM bs_customer NATURAL JOIN bs_business WHERE customer_id=cid);
END;
$$ LANGUAGE plpgsql;

/*SELECT (update_customer_b_org_info(18, '1111111112'));*/

/*Get customer address*/
CREATE OR REPLACE FUNCTION get_customer_address(cid INT)
    RETURNS TABLE(cust_id INT, p_region VARCHAR(20), p_city VARCHAR(20), p_street VARCHAR(20), p_house VARCHAR(20), p_apartment VARCHAR(20)) AS $$
BEGIN
    RETURN QUERY SELECT customer_id, region, city, street, house, apartment
                 FROM bs_customer NATURAL JOIN bs_address
                 WHERE customer_id = $1;
END;
$$ LANGUAGE plpgsql;

/*SELECT * FROM get_customer_address(1);*/

/*Update customer address*/
CREATE OR REPLACE FUNCTION update_customer_address(cid INT, reg VARCHAR(20), c VARCHAR(20), s VARCHAR(20), h VARCHAR(20), a VARCHAR(20))
    RETURNS void AS $$
BEGIN
    UPDATE bs_customer
    SET address_id = (SELECT addresscreator FROM AddressCreator(reg, c, s, h, a))
    WHERE customer_id = cid;
END;
$$ LANGUAGE plpgsql;

/*SELECT (update_customer_address(17, 'Udmurt Republic', 'Izhevsk', 'Pushkina', '20', '10'));*/

/*Generator for accounts*/
CREATE OR REPLACE FUNCTION AccountCreator() 
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
CREATE OR REPLACE FUNCTION AddressCreator(region VARCHAR(20), city VARCHAR(20), street VARCHAR(20), house VARCHAR(20), apartment VARCHAR(20)) 
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
CREATE OR REPLACE FUNCTION CustomerIndCreator(fname VARCHAR(30), lname VARCHAR(30), paspnum VARCHAR(10), s person_sex, bdate DATE, vatin VARCHAR(12), region VARCHAR(20), city VARCHAR(20), street VARCHAR(20), house VARCHAR(20), apartment VARCHAR(20), log VARCHAR(20), pass VARCHAR(30), amount NUMERIC, phone NUMERIC)
RETURNS void AS $$
BEGIN
    INSERT INTO bs_person (first_name, last_name, passport_number, sex, birth_date, person_vatin)
    VALUES ($1, $2, $3, $4, $5, $6);
    INSERT INTO bs_customer(type_customer, address_id)
    VALUES ('I', (SELECT addresscreator FROM AddressCreator($7, $8, $9, $10, $11)));
    INSERT INTO bs_individual(customer_id, person_id)
    VALUES ((SELECT currval(pg_get_serial_sequence('bs_customer','customer_id'))),(SELECT currval(pg_get_serial_sequence('bs_person','person_id'))));
    INSERT INTO bs_user(person_id, user_login, user_pass, type_user)
    VALUES ((SELECT currval(pg_get_serial_sequence('bs_person','person_id'))), $12, $13, 'C');
    INSERT INTO bs_account(account_num, customer_id, open_date, close_date, active, balance)
    VALUES ((AccountCreator()), (SELECT currval(pg_get_serial_sequence('bs_customer','customer_id'))), current_date, NULL, true, $14);
	INSERT INTO bs_phone(customer_id, phone_num)
	VALUES ((SELECT currval(pg_get_serial_sequence('bs_customer','customer_id'))), $15);
END;
$$ LANGUAGE plpgsql;

/*SELECT (CustomerIndCreator('Tester', 'Testirovkin', '4261234565', 'F', '1992-01-01', '123456789111', 'Tatarstan', 'Innopolis', 'Sportivnaya St', '108', '25', 'qwerty', 'qwerty', '2000', '89501234568'));*/

/*Creates busines customer*/
CREATE OR REPLACE FUNCTION CustomerBCreator(orgvatin VARCHAR(12), region VARCHAR(20), city VARCHAR(20), street VARCHAR(20), house VARCHAR(20), amount NUMERIC, phone NUMERIC)
RETURNS void AS $$
BEGIN
    INSERT INTO bs_organization(org_vatin)
    VALUES ($1);
    INSERT INTO bs_customer(type_customer, address_id)
    VALUES ('B', (SELECT addresscreator FROM AddressCreator($2, $3, $4, $5, NULL)));
    INSERT INTO bs_business(customer_id, org_id)
    VALUES ((SELECT currval(pg_get_serial_sequence('bs_customer','customer_id'))),(SELECT currval(pg_get_serial_sequence('bs_organization','org_id'))));
    INSERT INTO bs_account(account_num, customer_id, open_date, close_date, active, balance)
    VALUES ((AccountCreator()), (SELECT currval(pg_get_serial_sequence('bs_customer','customer_id'))), current_date, NULL, true, $6);
	INSERT INTO bs_phone(customer_id, phone_num)
	VALUES ((SELECT currval(pg_get_serial_sequence('bs_customer','customer_id'))), $7);
END;
$$ LANGUAGE plpgsql;

/*Creates accounts*/
CREATE OR REPLACE FUNCTION AccountCreator(cid INT, amount NUMERIC)
    RETURNS void AS $$
    BEGIN
        INSERT INTO bs_account(account_num, customer_id, open_date, active, balance)
            VALUES ((AccountCreator()), $1, current_date, TRUE, $2);
    END;
$$ LANGUAGE plpgsql;

/*Account block*/
CREATE OR REPLACE FUNCTION AccountBlock(ac_num VARCHAR(16))
    RETURNS void AS $$
BEGIN
    UPDATE bs_account SET active = FALSE WHERE account_num = $1;
END;
$$ LANGUAGE plpgsql;

/*Activate account*/
CREATE OR REPLACE FUNCTION AccountActivate(ac_num VARCHAR(16))
    RETURNS void AS $$
BEGIN
    UPDATE bs_account SET active = TRUE WHERE account_num = $1;
END;
$$ LANGUAGE plpgsql;

/*SELECT (AccountActivate('0080649559381108'));*/




