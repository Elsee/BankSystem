package environment;

import org.postgresql.ds.PGPoolingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by cubazis on 01.11.16.
 */
public class DataAccessController {

    private static DataSource dataSource = null;
    public DataAccessController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static DataSource setSource(){
        PGPoolingDataSource source = new PGPoolingDataSource();
        source.setDataSourceName("LOL");
        source.setServerName("localhost");
        source.setDatabaseName("mydb");
        source.setUser("postgres");
        source.setPassword("pass");
        source.setMaxConnections(10);
        return source;
    }
}
