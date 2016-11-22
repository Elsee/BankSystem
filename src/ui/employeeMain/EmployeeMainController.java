package ui.employeeMain;

import environment.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import login.Login;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeMainController extends ViewController{

    private ObservableList<CustomerI> customerIndividCollection;

    @FXML
    private Text actiontarget;

    @FXML
    TextField firstnameField;

    public TextField getFirstnameField() {
        return firstnameField;
    }

    @FXML
    TextField lastnameField;

    public TextField getLastnameField() {
        return lastnameField;
    }

    @FXML
    public void transition()  throws SQLException {
        transitionTo("");
    }

    @FXML
    void back() {
        transitionTo("login");
    }


    @FXML
    void personSearch() throws SQLException {
        try{
            ArrayList arrayList = this.data.searchIndividuals(getFirstnameField().getText(), getLastnameField().getText());
        }
        catch (SQLException sqle){
            String errmes = this.data.getErrorMessage(sqle);
            System.out.println(errmes);
            actiontarget.setText(errmes);
        }
    }
}
