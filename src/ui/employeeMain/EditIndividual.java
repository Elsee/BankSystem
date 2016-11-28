package ui.employeeMain;

import environment.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by cubazis on 22.11.16.
 */
public class EditIndividual  extends ViewController {
    @FXML
    public TextField firstNameField;
    @FXML
    public TextField lastNameField;
    @FXML
    public TextField passportField;
    @FXML
    public TextField sexField;
    @FXML
    public TextField bdateField;
    @FXML
    public TextField vatinField;
    @FXML
    public TextField regionField;
    @FXML
    public TextField cityField;
    @FXML
    public TextField streetField;
    @FXML
    public TextField houseField;
    @FXML
    public TextField apartmentField;
    @FXML
    public TextField loginField;
    @FXML
    public TextField passField;
    @FXML
    public TextField phoneField;
    @FXML
    public Text actiontarget;

    ArrayList<CustomerI> customer = new ArrayList<>();

    public void back(ActionEvent actionEvent) {
        transitionTo("employeeMain");
    }

    @FXML
    protected void init() {
        if (this.getParam() != null) {
            customer = this.getParam();

            firstNameField.setText(customer.get(0).getFirstname());
            lastNameField.setText(customer.get(0).getLastname());
            passportField.setText(customer.get(0).getPassport());
            sexField.setText(customer.get(0).getSex());
            bdateField.setText(customer.get(0).getBirthDate());
            vatinField.setText(customer.get(0).getVatin());
            regionField.setText(customer.get(0).getRegion());
            cityField.setText(customer.get(0).getCity());
            streetField.setText(customer.get(0).getStreet());
            houseField.setText(customer.get(0).getHouse());
            apartmentField.setText(customer.get(0).getApartment());
            loginField.setText(customer.get(0).getLogin());
            passField.setText(customer.get(0).getPassword());
            phoneField.setText(customer.get(0).getPhone());
        }
    }

    public void updateIndividual(ActionEvent actionEvent) throws SQLException {
        try{
            if(this.data.updateIndividual(
                    customer.get(0).getCid(),
                    firstNameField.getText(),
                    lastNameField.getText(),
                    passportField.getText(),
                    sexField.getText(),
                    bdateField.getText(),
                    vatinField.getText(),
                    regionField.getText(),
                    cityField.getText(),
                    streetField.getText(),
                    houseField.getText(),
                    apartmentField.getText(),
                    loginField.getText(),
                    passField.getText(),
                    phoneField.getText())){
                transitionTo("employeeMain");
            }
            else {
                System.out.println("Karachev vinovat");
            }
        }
        catch (SQLException sqle){
            String errmes = this.data.getErrorMessage(sqle);
            actiontarget.setText(errmes);
        }
    }
}
