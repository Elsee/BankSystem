package environment;

import org.postgresql.ds.PGPoolingDataSource;

import javax.sql.DataSource;
import java.sql.*;

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


    public static DataSource setSource(){
        PGPoolingDataSource source = new PGPoolingDataSource();
        source.setDataSourceName("LOL");
        source.setServerName("localhost");
        source.setDatabaseName("bankdb");
        source.setUser("postgres");
        source.setPassword("123456");
        source.setMaxConnections(10);
        return source;
    }
}
