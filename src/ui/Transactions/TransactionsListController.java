package ui.Transactions;

import environment.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

/**
 * Created by svetl on 23.11.2016.
 */
public class TransactionsListController extends ViewController {
    @FXML
    TableView transactionTable;

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
