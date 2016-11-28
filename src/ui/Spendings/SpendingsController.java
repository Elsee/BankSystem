package ui.Spendings;

import environment.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by svetl on 23.11.2016.
 */
public class SpendingsController extends ViewController {
    @FXML
    TableView spendingsTable;
    @FXML
    Text total;

    @FXML
    private Text actiontarget;

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

    protected void init() {
        try {
            if(this.getParam() != null){
                String accountNum = (String) this.getParam().get(0);
                ArrayList<Spending> spendings = this.data.getCustomerSpendings(accountNum);
                ObservableList selectedSpendings = FXCollections.observableArrayList(spendings);
                spendingsTable.setItems(selectedSpendings);
                total.setText("    "+Spending.getTotal());
            }

        }
        catch (SQLException sqle){
            String errmes = null;
            try {
                errmes = this.data.getErrorMessage(sqle);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            actiontarget.setText(errmes);
        }
    }

}
