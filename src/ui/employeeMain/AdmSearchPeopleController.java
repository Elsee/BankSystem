package ui.employeeMain;

import environment.ViewController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by svetl on 16.11.2016.
 */
public class AdmSearchPeopleController extends ViewController {
    private static AdmSearchPeopleController searchInstance;

    public static AdmSearchPeopleController getSearchInstance() {
        if (AdmSearchPeopleController.searchInstance == null) {
            synchronized (AdmSearchPeopleController.class) {
                if (AdmSearchPeopleController.searchInstance == null) {
                    AdmSearchPeopleController.searchInstance = new AdmSearchPeopleController();
                }
            }
        }
        return AdmSearchPeopleController.searchInstance;
    }


    private ObservableList<CustomerI> customerIndividCollection;
    @FXML
    TableView<CustomerI> tableView;
    @FXML
    TableColumn<CustomerI, String> firstNameColumn;
    @FXML
    TableColumn<CustomerI, String> lastNameColumn;
    @FXML
    TableColumn<CustomerI, Long> passportColumn;

    private EmployeeMainController admContr = EmployeeMainController.getInstance();

    @FXML
    protected void init() {
//        customerIndividCollection = EmployeeMainController.getInstance().getCustomerIndividCollection();
//        System.out.println(customerIndividCollection);
//        if (customerIndividCollection != null) {
//            tableView.setItems(customerIndividCollection);
//        }
    }


    @FXML
    public void exec(ObservableList<CustomerI> customerIndividCol) {

        if (customerIndividCol != null) {
            firstNameColumn.setCellValueFactory(new PropertyValueFactory<CustomerI, String>("firstname"));
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<CustomerI, String>("lastname"));
            passportColumn.setCellValueFactory(new PropertyValueFactory<CustomerI, Long>("passport"));
        }

    }
}
