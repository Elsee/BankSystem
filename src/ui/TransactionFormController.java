package ui;

import environment.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.awt.*;

/**
 * Created by svetl on 01.11.2016.
 */

public class TransactionFormController extends ViewController {
    ObservableList<String> accountsList = FXCollections.observableArrayList("account1", "account2");

    @FXML
    protected void init() {
        cb.setValue("account1");
        cb.setItems(accountsList);
    }

    @FXML
    ChoiceBox cb;

    @FXML
    void back() {
        transitionTo("screen1");
    }

    @FXML
    void mainScreen() {
        transitionTo("screen2");
    }
}
