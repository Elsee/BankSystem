package ui.ViewCustomerAccounts;

import environment.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import ui.employeeMain.CustomerAccount;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by svetl on 22.11.2016.
 */
public class ViewCustomerAccountsController extends ViewController {
    @FXML
    TableView accountsTable;

    @FXML
    protected void init() {
        try {
            if (this.getParam() != null) {
                int custId = (int)this.getParam().get(0);
                ArrayList<CustomerAccount> accounts = this.data.getAccounts(custId);
                ObservableList searchedAccounts = FXCollections.observableArrayList(accounts);
                accountsTable.setItems(searchedAccounts);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void back() {
        transitionTo("employeeMain");
    }
}
