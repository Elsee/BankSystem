package environment;

import org.postgresql.ds.PGPoolingDataSource;
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

    public List<Account> getAllAccounts() throws SQLException {
        List<Account> result = new ArrayList();
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM bs_account")) {
            while (rs.next()) {
                long num = rs.getLong("account_id");
                String balance = rs.getString("balance");
                result.add(new Account(num, balance));
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
}
