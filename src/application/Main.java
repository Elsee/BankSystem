package application;

import environment.DataAccessController;
import environment.Screens;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.postgresql.ds.PGPoolingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;

public class Main extends Application {
    private static final HashMap<String, String> SCREENS = new HashMap<>();
    private static final String START_SCREEN = "screen1";

    static {
        SCREENS.put("screen1", "screens/Login.fxml");
        SCREENS.put("screen2", "screens/UserMain.fxml");
        SCREENS.put("accountDetail", "screens/AccountDetail.fxml");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        DataSource source = DataAccessController.setSource();
        //Parent root = FXMLLoader.load(getClass().getResource("Screen1.fxml"));
        primaryStage.setTitle("Bank System (Login)");

        HashMap<String, Object> data = new HashMap<>();
        data.put("test", "application works");

        Screens screens = new Screens(SCREENS, primaryStage, data);
        Scene scene = new Scene(screens, 600, 400);

//        primaryStage.setScene(new Scene(root, 360, 275));
        primaryStage.setScene(scene);
        primaryStage.show();
        screens.transitionTo(START_SCREEN);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
