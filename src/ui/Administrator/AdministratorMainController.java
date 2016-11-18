package ui.Administrator;

import environment.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

public class AdministratorMainController extends ViewController{

    private static AdministratorMainController instance;
    private ObservableList<CustomerI> customerIndividCollection;

    public AdministratorMainController() {};

    public static AdministratorMainController getInstance() {
        if (AdministratorMainController.instance == null) {
            synchronized (AdministratorMainController.class) {
                if (AdministratorMainController.instance == null) {
                    AdministratorMainController.instance = new AdministratorMainController();
                }
            }
        }
        return AdministratorMainController.instance;
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
        transitionTo("screen1");
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
