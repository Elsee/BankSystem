package ui.ViewCustomerAccounts;

import environment.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Text;
import ui.employeeMain.CustomerAccount;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ViewCustomerAccountsController extends ViewController {
    ArrayList<CustomerAccount> accounts;
    ObservableList<CustomerAccount> searchedAccounts;
    int custId;
    @FXML
    TableView accountsTable;

    @FXML
    Text actionTarget;

    @FXML
    protected void init() {
        try {
            if (this.getParam() != null) {
                custId = (int)this.getParam().get(0);
                accounts = this.data.getAccounts(custId);
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

    public void addAccount(ActionEvent actionEvent) throws SQLException {
        try {
            TextInputDialog dialog = new TextInputDialog("1000");
            dialog.setTitle("Amount Input Dialog");
            dialog.setHeaderText("New account creation");
            dialog.setContentText("Please enter amount of money for new account:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                Double money = -1.0;
                try {
                    actionTarget.setText("");
                    money = Double.parseDouble(result.get());
                }
                catch (Exception e) {
                    actionTarget.setText("Wrong amount of money! Please, try again.");
                }
                if (money > 0) {
                    this.data.createNewAccount(custId, money);
                    accounts = this.data.getAccounts(custId);
                    searchedAccounts = FXCollections.observableArrayList(accounts);
                    accountsTable.setItems(searchedAccounts);
                }
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
