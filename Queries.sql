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
    VALUES ((random_account_creator()), (SELECT currval(pg_get_serial_sequence('bs_customer','customer_id'))), current_date, NULL, true, $6);
	INSERT INTO bs_phone(customer_id, phone_num)
	VALUES ((SELECT currval(pg_get_serial_sequence('bs_customer','customer_id'))), $7);
END;
$$ LANGUAGE plpgsql;

/*Creates accounts*/
CREATE OR REPLACE FUNCTION account_creator(cid INT, amount NUMERIC)
    RETURNS void AS $$
    BEGIN
        INSERT INTO bs_account(account_num, customer_id, open_date, active, balance)
            VALUES ((random_account_creator()), $1, current_date, TRUE, $2);
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




