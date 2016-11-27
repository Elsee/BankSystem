package ui.ViewCustomerAccounts;

import environment.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import ui.employeeMain.CustomerAccount;

import java.sql.SQLException;
import java.util.ArrayList;

public class ViewCustomerAccountsController extends ViewController {
    ObservableList<CustomerAccount> searchedAccounts;
    @FXML
    TableView accountsTable;

    @FXML
    protected void init() {
        try {
            if (this.getParam() != null) {
                int custId = (int)this.getParam().get(0);
                ArrayList<CustomerAccount> accounts = this.data.getAccounts(custId);
                searchedAccounts = FXCollections.observableArrayList(accounts);
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

    public void changeActivity(ActionEvent actionEvent) throws SQLException {
        try {
            int ix = accountsTable.getSelectionModel().getSelectedIndex();
            CustomerAccount curAcc = searchedAccounts.get(ix);
            String accountNum = curAcc.getAccountNum();
            this.data.changeAccActivity(accountNum);
            curAcc.setActivation(!curAcc.getActivation());
            curAcc.setCDate(this.data.getCloseDate(accountNum));
            accountsTable.refresh();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
