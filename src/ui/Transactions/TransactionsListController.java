package ui.Transactions;

import environment.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import ui.customerMain.Transaction;
import ui.employeeMain.CustomerAccount;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by svetl on 23.11.2016.
 */
public class TransactionsListController extends ViewController {
    @FXML
    TableView transactionTable;

    @FXML
    protected void init() {
        try {
            if (this.getParam() != null) {
                int accId = (int)this.getParam().get(0);
                ArrayList<Transaction> transactions = this.data.getTransactions(accId);
                ObservableList searchedTransitions = FXCollections.observableArrayList(transactions);
                transactionTable.setItems(searchedTransitions);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    @FXML
    void spendings() {
        transitionTo("spendings");
    }

}
