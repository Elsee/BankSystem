package ui.employeeMain;

import environment.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

public class EmployeeMainController extends ViewController{

    private static EmployeeMainController instance;
    private ObservableList<CustomerI> customerIndividCollection;

    public EmployeeMainController() {};

    public static EmployeeMainController getInstance() {
        if (EmployeeMainController.instance == null) {
            synchronized (EmployeeMainController.class) {
                if (EmployeeMainController.instance == null) {
                    EmployeeMainController.instance = new EmployeeMainController();
                }
            }
        }
        return EmployeeMainController.instance;
    }

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
    public ObservableList<CustomerI> exec() {
        try {
            List<CustomerI> customerIs = data.searchCustomers(firstnameField.getText(), lastnameField.getText());
            customerIndividCollection = FXCollections.observableArrayList(customerIs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerIndividCollection;
    }

    @FXML
    void personSearch() throws SQLException {
        AdmSearchPeopleController.getSearchInstance().exec(this.exec());
        this.transitionTo("peopleSearch");
    }
}
