package environment;

import org.postgresql.ds.PGPoolingDataSource;

/**
 * Created by cubazis on 02.11.16.
 */
public abstract class DataSource {
    public static javax.sql.DataSource setSource(){
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
