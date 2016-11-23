package application;

import environment.DataAccess;
import environment.Screens;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.sql.DataSource;
import java.util.HashMap;

public class Main extends Application {
    private static final HashMap<String, String> SCREENS = new HashMap<>();
    //private static final String START_SCREEN = "login";
    private static final String START_SCREEN = "employeeMain";

    static {
        SCREENS.put("login", "screens/Login.fxml");
        SCREENS.put("customerMain", "screens/CustomerMain.fxml");
        SCREENS.put("employeeMain", "screens/EmployeeMain.fxml");
        SCREENS.put("transactionForm", "screens/TransactionForm.fxml");
        SCREENS.put("createIndividual", "screens/CreateIndividual.fxml");
        SCREENS.put("editIndividual", "screens/EditIndividual.fxml");
        SCREENS.put("createBusiness", "screens/CreateBusiness.fxml");
        SCREENS.put("editBusiness", "screens/EditBusiness.fxml");
        SCREENS.put("viewCustomerAccounts", "screens/ViewAccounts.fxml");
        SCREENS.put("transactionsList", "screens/TransactionsList.fxml");
        SCREENS.put("spendings", "screens/Spendings.fxml");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        DataSource source = environment.DataSource.setSource();
        DataAccess dataAccess = new DataAccess(source);
        primaryStage.setTitle("Bank System (Login)");

        Screens screens = new Screens(SCREENS, primaryStage, dataAccess);
        Scene scene = new Scene(screens, 1024, 768);

        primaryStage.setScene(scene);
        primaryStage.show();
        screens.transitionTo(START_SCREEN);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
