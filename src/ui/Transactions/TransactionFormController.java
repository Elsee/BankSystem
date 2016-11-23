package ui.Transactions;

import environment.ViewController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ui.employeeMain.CustomerAccount;

import java.sql.SQLException;
import java.util.ArrayList;

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

    ArrayList accountsList;
    ArrayList<String> accountsNumbers;

    @FXML
    protected void init() {
        accountsNumbers = new ArrayList<>();
        if (this.getParam() != null) {
            accountsList = this.getParam();
            for (int i = 0; i < accountsList.size(); ++i) {
                CustomerAccount curAcc = (CustomerAccount) accountsList.get(i);
                accountsNumbers.add(curAcc.getAccountNum());
            }
            cb.setValue(accountsNumbers.get(0));
            cb.setItems(FXCollections.observableArrayList(accountsNumbers));
        }
    }

    @FXML
    protected void createTransaction() throws SQLException {
        try{
            actiontarget.setText(" ");
            this.data.makeTransaction(cb.getSelectionModel().getSelectedItem().toString(), toAccountField.getText(), amountField.getText());
            actiontarget.setText("Successfull");
        }
        catch (SQLException sqle){
            String errmes = this.data.getErrorMessage(sqle);
            actiontarget.setText(errmes);
        }
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
