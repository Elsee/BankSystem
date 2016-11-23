package environment;
import login.Login;
import ui.employeeMain.CustomerAccount;
import ui.employeeMain.CustomerI;
import ui.customerMain.Account;
import ui.customerMain.Transaction;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cubazis on 01.11.16.
 */
public class DataAccess {

    private static DataSource dataSource = null;
    public DataAccess(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public ArrayList searchIndividuals(String fname, String lname) throws SQLException{
        ArrayList<CustomerI> customers = new ArrayList<>();
        Connection connection = getConnection();
        CallableStatement statement = connection.prepareCall(" { call select_individuals(?, ?) } ");
        statement.setString(1, fname);
        statement.setString(2, lname);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            customers.add(new CustomerI(rs.getInt("cid"), rs.getString("fname"), rs.getString("lname"), rs.getString("passnum")));
        }
        rs.close();
        statement.close();
        return customers;
    }



    public ArrayList login(String inputLogin, String inputPass) throws SQLException {
        ArrayList<Login> logins = new ArrayList<>();
        Connection connection = getConnection();
        CallableStatement statement = connection.prepareCall(" { call make_login(?, ?) } ");
        statement.setString(1, inputLogin);
        statement.setString(2, inputPass);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            logins.add(new Login(rs.getInt("user_id"), rs.getInt("person_id"), rs.getString("type_user")));
        }
        rs.close();
        statement.close();
        return logins;
    }

    public int getCustomerId(String personId) throws SQLException {
        Connection connection = getConnection();
        ArrayList<Integer> cid = new ArrayList<>();
        CallableStatement statement = connection.prepareCall(" { call get_customer_id(?) } ");
        statement.setString(1, personId);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            cid.add(rs.getInt(1));
        }
        rs.close();
        statement.close();
        return cid.get(0);
    }

    public String getErrorMessage(SQLException error) throws SQLException {
        String errno = error.toString();
        Pattern p = Pattern.compile("E[0-9]{4}");
        Matcher m = p.matcher(errno);
        m.find();
        errno = errno.substring(m.start(), m.end());
        String errmes = "";
        Connection connection = getConnection();
        CallableStatement errorSt = connection.prepareCall(" { call select_db_error_message(?) } ");
        errorSt.setString(1, errno);
        ResultSet rs = errorSt.executeQuery();
        while (rs.next()) {
            errmes = rs.getString("err_message");
        }
        rs.close();
        errorSt.close();
        return errmes;
    };

    public List<Account> getAllAccounts() throws SQLException {
        ArrayList<Account> result = new ArrayList<>();

        return result;
    }


    public List<Transaction> getAllTransactions() throws SQLException {
        ArrayList<Transaction> result = new ArrayList<>();

        return result;
    }

    public ArrayList getAccounts(int cid) throws SQLException{
        ArrayList<CustomerAccount> accounts = new ArrayList<>();
        Connection connection = getConnection();
        CallableStatement statement = connection.prepareCall(" { call  select_customer_accounts(?) } ");
        statement.setInt(1, cid);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            accounts.add(new CustomerAccount(rs.getInt("aid"), rs.getString("accnum"), rs.getInt("cust_id"), rs.getString("opendate"), rs.getString("closedate"), rs.getBoolean("act"), rs.getString("bal")));
        }
        rs.close();
        statement.close();
        return accounts;
    }

    public boolean createIndividual(String firstNameField,
                                 String lastNameField,
                                 String passportField,
                                 String sexField,
                                 String bdateField,
                                 String vatinField,
                                 String regionField,
                                 String cityField,
                                 String streetField,
                                 String houseField,
                                 String apartmentField,
                                 String loginField,
                                 String passField,
                                 String moneyField,
                                 String phoneField) throws SQLException {
        Connection connection = getConnection();
        CallableStatement statement = connection.prepareCall(" { call customer_individual_creator(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) } ");
        statement.setString(1, firstNameField);
        statement.setString(2, lastNameField);
        statement.setString(3, passportField);
        statement.setString(4, sexField);
        statement.setString(5, bdateField);
        statement.setString(6, vatinField);
        statement.setString(7, regionField);
        statement.setString(8, cityField);
        statement.setString(9, streetField);
        statement.setString(10, houseField);
        statement.setString(11, apartmentField);
        statement.setString(12, loginField);
        statement.setString(13, passField);
        statement.setString(14, moneyField);
        statement.setString(15, phoneField);
        Boolean rs = statement.execute();
        statement.close();
        return rs;
    }

    public boolean makeTransaction(String accFrom, String accTo, String transSum) throws SQLException {
        Connection connection = getConnection();
        CallableStatement statement = connection.prepareCall(" { call make_transaction(?, ?, ?) } ");
        statement.setString(1, accFrom);
        statement.setString(2, accTo);
        statement.setString(3, transSum);
        Boolean rs = statement.execute();
        statement.close();
        return rs;
    }
}
