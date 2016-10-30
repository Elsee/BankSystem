package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.postgresql.ds.PGPoolingDataSource;
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        PGPoolingDataSource source = new PGPoolingDataSource();
        source.setDataSourceName("MyDataSource");
        source.setServerName("localhost");
        source.setDatabaseName("bs");
        source.setUser("postgres");
        source.setPassword("123456");
        source.setMaxConnections(10);
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        primaryStage.setTitle("Bank System");
        primaryStage.setScene(new Scene(root, 450, 550));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
