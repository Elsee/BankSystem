package ui.Spendings;

import environment.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

/**
 * Created by svetl on 23.11.2016.
 */
public class SpendingsController extends ViewController {
    @FXML
    TableView spendingsTable;
    @FXML
    Text total;

    @FXML
    void mainScreen() {
        transitionTo("customerMain");
    }

    @FXML
    void transactionForm() {
        transitionTo("transactionForm");
    }

    @FXML
    void logout() {
        transitionTo("login");
    }
}
