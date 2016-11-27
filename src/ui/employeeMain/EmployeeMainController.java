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
    static private ArrayList organizationsList;

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
    private Text actiontarget1; //For organizations

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
    TextField orgNumField;

    @FXML
    TableView customerSearchTable;
    @FXML
    TableColumn<CustomerI, String> firstNameColumn;
    @FXML
    TableColumn<CustomerI, String> lastNameColumn;
    @FXML
    TableColumn<CustomerI, String> passportColumn;

    @FXML
    TableView organizationSearchTable;

    @FXML
    TableColumn orgNumColumn;

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
        int ix = customerSearchTable.getSelectionModel().getSelectedIndex();
        CustomerI selesctedCustomer = (CustomerI) individualsList.get(ix);
        ArrayList<Integer> selectedCustomerId = new ArrayList<>();
        selectedCustomerId.add(selesctedCustomer.getCid());
        transitionTo("viewCustomerAccounts", selectedCustomerId);
    }

    @FXML
    void organizationSearch() throws SQLException {
        try{
            actiontarget1.setText("");
            ArrayList arrayListOrg = this.data.searchOrganizations(orgNumField.getText());
            organizationsList = arrayListOrg;
            ObservableList searchedOrg = FXCollections.observableArrayList(arrayListOrg);
            organizationSearchTable.setItems(searchedOrg);
        }
        catch (SQLException sqle){
            String errmes = this.data.getErrorMessage(sqle);
            actiontarget1.setText(errmes);
        }
    }

    @FXML
    public void viewBAccounts(ActionEvent actionEvent) {
        int ix = organizationSearchTable.getSelectionModel().getSelectedIndex();
        CustomerB selesctedCustomer = (CustomerB) organizationsList.get(ix);
        ArrayList<Integer> selectedCustomerId = new ArrayList<>();
        selectedCustomerId.add(selesctedCustomer.getCid());
        transitionTo("viewCustomerAccounts", selectedCustomerId);
    }

    public void organizationCreate(ActionEvent actionEvent) {
        transitionTo("createBusiness");
    }
}
