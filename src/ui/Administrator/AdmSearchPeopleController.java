package ui.Administrator;

import environment.ViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import ui.userMain.Account;
import ui.userMain.Transaction;

import java.sql.SQLException;
import java.util.List;

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

    private AdministratorMainController admContr = AdministratorMainController.getInstance();

    @FXML
    protected void init() {
//        customerIndividCollection = AdministratorMainController.getInstance().getCustomerIndividCollection();
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
