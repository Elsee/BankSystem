package environment;
import ui.Administrator.CustomerI;
import ui.userMain.Account;
import ui.userMain.Transaction;

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


    public Boolean checkLogin(String inputLogin, String inputPass) throws SQLException {
        Boolean result = false;
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM bs_user WHERE user_login = ? AND user_pass = ?"))
        {
            stmt.setString(1,inputLogin);
            stmt.setString(2,inputPass);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                result = true;
            }
            rs.close();
            stmt.close();
        }
        return result;
    }

    public Boolean isCustomer(String inputLogin) throws SQLException {
        Boolean result = false;
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT user_id FROM bs_user u, bs_individual i WHERE u.user_login = ? AND u.user_id = i.id_customer"))
        {
            stmt.setString(1,inputLogin);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                result = true;
            }
            rs.close();
            stmt.close();
            }
        return result;
    }

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
