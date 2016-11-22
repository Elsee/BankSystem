package ui.employeeMain;

import environment.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import login.Login;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeMainController extends ViewController{
    static private ArrayList individualsList;

    @FXML
    protected void init() {
        if(this.getParam() != null){
            for (Object o: (ArrayList)this.getParam()) {
                System.out.println(o.toString());
            }
        }
        else {
            System.out.println("null");
        }

    }

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
    TableView customerSearchTable;
    @FXML
    TableColumn<CustomerI, String> firstNameColumn;
    @FXML
    TableColumn<CustomerI, String> lastNameColumn;
    @FXML
    TableColumn<CustomerI, String> passportColumn;

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
            ArrayList arrayList = this.data.searchIndividuals(firstnameField.getText(), lastnameField.getText());
            individualsList = arrayList;
            ObservableList searchedInd = FXCollections.observableArrayList(arrayList);
            customerSearchTable.setItems(searchedInd);
        }
        catch (SQLException sqle){
            String errmes = this.data.getErrorMessage(sqle);
            actiontarget.setText(errmes);
        }
    }

    public void personCreate(ActionEvent actionEvent) {
        transitionTo("createIndividual");
    }

    public void viewAccounts(ActionEvent actionEvent) {
        transitionTo("viewCustomerAccounts");
    }
}
