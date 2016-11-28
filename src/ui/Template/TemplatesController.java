package ui.Template;

import environment.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import ui.customerMain.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by svetl on 23.11.2016.
 */
public class TemplatesController extends ViewController {
    @FXML
    TableView templatesTable;

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
