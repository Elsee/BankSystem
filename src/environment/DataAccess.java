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

    public ArrayList searchIndividuals(String fname, String lname) throws SQLException{
        ArrayList<CustomerI> customers = new ArrayList<>();
        Connection connection = getConnection();
        CallableStatement statement = connection.prepareCall(" { call select_individuals(?, ?) } ");
        statement.setString(1, fname);
        statement.setString(2, lname);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            customers.add(new CustomerI(rs.getInt("cid"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("passnum")));
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
        ArrayList<Account> result = new ArrayList<>();

        return result;
    }


    public List<Transaction> getAllTransactions() throws SQLException {
        ArrayList<Transaction> result = new ArrayList<>();

        return result;
    }
}
