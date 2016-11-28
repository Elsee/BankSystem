package ui.Template;

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
public class TemplatesController extends ViewController {
    @FXML
    TableView templatesTable;

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
                Integer customerNum = (Integer) this.getParam().get(0);
                System.out.println(customerNum);
                ArrayList<Template> templates = this.data.getCustomerTemplates(customerNum);
                ObservableList selectedSpendings = FXCollections.observableArrayList(templates);
                templatesTable.setItems(selectedSpendings);
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
