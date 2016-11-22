package environment;
import login.Login;
import ui.employeeMain.CustomerI;
import ui.customerMain.Account;
import ui.customerMain.Transaction;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public String getErrorMessage(SQLException error) throws SQLException {
        String errno = error.toString();
        errno = errno.split(" ")[2];
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
        List<Account> result = new ArrayList();
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM bs_account")) {
            while (rs.next()) {
                long account_id = rs.getLong("account_id");
                long id_customer = rs.getLong("id_customer");
                String open_date = rs.getString("open_date");
                String close_date = rs.getString("close_date");
                Boolean active = rs.getBoolean("active");
                String  last_activity = rs.getString("last_activity");
                String balance = rs.getString("balance");
                result.add(new Account(account_id, id_customer, open_date, close_date, active, last_activity, balance));
            }
            rs.close();
            stmt.close();
        }
        return result;
    }

    public List<Account> getCustomerAccounts(int user_id) throws SQLException {
        String id_user = user_id+"";
        List<Account> result = new ArrayList();
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT account_id AS acid, balance AS bal FROM bs_account AS ac\n" +
                     "LEFT JOIN bs_individual AS ind ON  ac.id_customer = ind.id_customer" +
                     "WHERE ind.id_person = "+id_user)) {
            while (rs.next()) {
                long account_id = rs.getLong("account_id");
                Boolean active = rs.getBoolean("active");
                String balance = rs.getString("balance");
                result.add(new Account(account_id, active, balance));
            }
            rs.close();
            stmt.close();
        }
        return result;
    }

    public List<Transaction> getAllTransactions() throws SQLException {
        List<Transaction> result = new ArrayList();
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM bs_transaction")) {
            while (rs.next()) {
                String time = rs.getString(5);
                Long from = rs.getLong("id_account_from");
                Long to = rs.getLong("id_account_to");
                String amount = rs.getString("amount");
                result.add(new Transaction(time, from, to, amount));
            }
            rs.close();
            stmt.close();
        }
        return result;
    }

    public List<CustomerI> searchCustomers(String inputFname, String inputLname) throws SQLException {
        List<CustomerI> result = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT fname, lname, passport_number FROM bs_person WHERE fname = ? AND lname = ?"))
        {
            stmt.setString(1,inputFname);
            stmt.setString(2,inputLname);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String firstname = rs.getString(1);
                String lastname = rs.getString(2);
                Long pass = rs.getLong(3);
                result.add(new CustomerI(firstname, lastname, pass));
            }
            rs.close();
            stmt.close();
        }

        return result;
    }
}
