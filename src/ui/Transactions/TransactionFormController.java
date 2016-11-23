package ui.Transactions;

import environment.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.awt.*;

/**
 * Created by svetl on 01.11.2016.
 */

public class TransactionFormController extends ViewController {
    @FXML
    ChoiceBox cb;
    @FXML
    TextField toAccountField;
    @FXML
    TextField amountField;
    @FXML
    Text actiontarget;

    ObservableList<String> accountsList = FXCollections.observableArrayList("account1", "account2");

    @FXML
    protected void init() {
        cb.setValue("account1");
        cb.setItems(accountsList);
    }

    @FXML
    void back() {
        transitionTo("screen1");
    }

    @FXML
    void mainScreen() {
        transitionTo("customerMain");
    }

    @FXML
    void spendings() {
        transitionTo("spendings");
    }
}
