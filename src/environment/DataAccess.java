package environment;
import login.Login;
import ui.Spendings.Spending;
import ui.Template.Template;
import ui.employeeMain.CustomerAccount;
import ui.employeeMain.CustomerB;
import ui.employeeMain.CustomerI;
import ui.customerMain.Account;
import ui.customerMain.Transaction;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.util.*;
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
            customers.add(new CustomerI(
                    rs.getInt("cid"),
                    rs.getString("fname"),
                    rs.getString("lname"),
                    rs.getString("passnum"),
                    rs.getString("sekas"),
                    rs.getString("bdate"),
                    rs.getString("pvatin"),
                    rs.getString("reg"),
                    rs.getString("ci"),
                    rs.getString("str"),
                    rs.getString("hou"),
                    rs.getString("apart"),
                    rs.getString("log"),
                    rs.getString("passw"),
                    rs.getString("phonen")));
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

    public void makeTransaction(String accFrom, String accTo, String transSum, Boolean isTemplate ) throws SQLException {
        Connection connection = getConnection();
        CallableStatement statement = connection.prepareCall(" { call make_transaction(?, ?, ?, ?) } ");
        statement.setString(1, accFrom);
        statement.setString(2, accTo);
        statement.setString(3, transSum);
        statement.setBoolean(4, isTemplate);
        ResultSet rs = statement.executeQuery();
        rs.close();
        statement.close();
    }

    public ArrayList getTransactions(int accId) throws SQLException{
        ArrayList<Transaction> transactions = new ArrayList<>();
        Connection connection = getConnection();
        CallableStatement statement = connection.prepareCall(" { call  get_account_transactions(?) } ");
        statement.setInt(1, accId);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            transactions.add(new Transaction(rs.getString("t"), rs.getString("acc_from_num"), rs.getString("acc_to_num"), rs.getString("val")));
        }
        rs.close();
        statement.close();
        return transactions;
    }

    public void changeAccActivity(String accNum) throws SQLException{
        try (Connection connection = getConnection();
        CallableStatement statement = connection.prepareCall(" { call change_account_activity(?) } ")) {
            statement.setString(1, accNum);
            statement.executeUpdate();
            statement.close();
        }
    }

    public String getCloseDate(String accNum) throws SQLException {
        Date closeDate = null;
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall(" { call get_close_date(?) } ")) {
            statement.setString(1, accNum);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                closeDate = rs.getDate(1);
            }
            statement.close();
        }
        if (closeDate == null) {
            return "";
        }
        else {
            return closeDate.toString();
        }
    }

    public void createNewAccount(int custID, Double money) throws SQLException {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall(" { call account_creator(?, ?) } ")) {
            statement.setInt(1, custID);
            statement.setDouble(2, money);
            statement.executeQuery();
            statement.close();
        }
    }

    public ArrayList searchOrganizations(String orgNum) throws SQLException{
        ArrayList<CustomerB> customersB = new ArrayList<>();
        Connection connection = getConnection();
        CallableStatement statement = connection.prepareCall(" { call select_organizations(?) } ");
        statement.setString(1, orgNum);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            customersB.add(new CustomerB(
                    rs.getInt("cid"),
                    rs.getString("ovatin"),
                    rs.getString("reg"),
                    rs.getString("ci"),
                    rs.getString("str"),
                    rs.getString("hou"),
                    rs.getString("phonen")));
        }
        rs.close();
        statement.close();
        return customersB;
    }

    public boolean createBusiness(String vatinField,
                                    String regionField,
                                    String cityField,
                                    String streetField,
                                    String houseField,
                                    String moneyField,
                                    String phoneField,
                                    String category) throws SQLException {
        Connection connection = getConnection();
        CallableStatement statement = connection.prepareCall(" { call customer_business_creator(?, ?, ?, ?, ?, ?, ?, ?) } ");
        statement.setString(1, vatinField);
        statement.setString(2, regionField);
        statement.setString(3, cityField);
        statement.setString(4, streetField);
        statement.setString(5, houseField);
        statement.setString(6, moneyField);
        statement.setString(7, phoneField);
        statement.setString(8, category);
        Boolean rs = statement.execute();
        statement.close();
        return rs;
    }

    public ArrayList getCategories() throws SQLException{
        ArrayList<String> categories = new ArrayList<>();
        Connection connection = getConnection();
        CallableStatement statement = connection.prepareCall(" { call  get_categories() } ");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            categories.add(rs.getString(1));
        }
        rs.close();
        statement.close();
        return categories;
    }

    public ArrayList<Spending> getCustomerSpendings(String accountNum) throws SQLException{
        ArrayList<Spending> spendings = new ArrayList<>();
        Connection connection = getConnection();
        CallableStatement statement = connection.prepareCall(" { call  select_customer_transactions_by_outgoing(?) } ");
        statement.setString(1, accountNum);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            spendings.add(new Spending(rs.getString("txntype"), rs.getDouble("summ"), rs.getString("accout")));
            for (Spending s: spendings) {
                Spending spending = s;
                spending.setPercent();
                s = spending;
            }
        }
        rs.close();
        statement.close();
        return spendings;
    }

    public ArrayList<Template> getCustomerTemplates(Integer customerNum) throws SQLException {
        ArrayList<Template> templates = new ArrayList<>();
        Connection connection = getConnection();
        CallableStatement statement = connection.prepareCall(" { call  select_customer_templates(?) } ");
        statement.setInt(1, customerNum);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            //System.out.println(rs.getString("account_from")+" "+rs.getString("account_to")+" "+rs.getString("amount"));
            templates.add(new Template(rs.getString("account_from"), rs.getString("account_to"), rs.getString("amount")));
        }
        rs.close();
        statement.close();
        return templates;
    }

        public boolean updateIndividual(int custID,
                                    String firstNameField,
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
                                    String phoneField) throws SQLException {
        Connection connection = getConnection();
        CallableStatement statement = connection.prepareCall(" { call customer_individual_updator(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) } ");
        statement.setInt(1, custID);
        statement.setString(2, firstNameField);
        statement.setString(3, lastNameField);
        statement.setString(4, passportField);
        statement.setString(5, sexField);
        statement.setString(6, bdateField);
        statement.setString(7, vatinField);
        statement.setString(8, regionField);
        statement.setString(9, cityField);
        statement.setString(10, streetField);
        statement.setString(11, houseField);
        statement.setString(12, apartmentField);
        statement.setString(13, loginField);
        statement.setString(14, passField);
        statement.setString(15, phoneField);
        Boolean rs = statement.execute();
        statement.close();
        return rs;
    }

    public boolean updateBusiness(int custID,
                                  String vatinField,
                                  String regionField,
                                  String cityField,
                                  String streetField,
                                  String houseField,
                                  String phoneField) throws SQLException {
        Connection connection = getConnection();
        CallableStatement statement = connection.prepareCall(" { call customer_business_updator(?, ?, ?, ?, ?, ?, ?) } ");
        statement.setInt(1, custID);
        statement.setString(2, vatinField);
        statement.setString(3, regionField);
        statement.setString(4, cityField);
        statement.setString(5, streetField);
        statement.setString(6, houseField);
        statement.setString(7, phoneField);
        Boolean rs = statement.execute();
        statement.close();
        return rs;
    }
}
