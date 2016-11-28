package ui.employeeMain;

import environment.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by cubazis on 22.11.16.
 */
public class EditBusiness extends ViewController {

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
    public TextField phoneField;
    @FXML
    public Text actiontarget;
    ArrayList<CustomerB> customer = new ArrayList<>();

    protected void init() {
        if (this.getParam() != null) {
            customer = this.getParam();

            vatinField.setText(customer.get(0).getOrgNum());
            regionField.setText(customer.get(0).getRegion());
            cityField.setText(customer.get(0).getCity());
            streetField.setText(customer.get(0).getStreet());
            houseField.setText(customer.get(0).getHouse());
            phoneField.setText(customer.get(0).getPhone());
        }
    }

    public void back(ActionEvent actionEvent) {
        transitionTo("employeeMain");
    }

    public void updateBusiness(ActionEvent actionEvent) throws SQLException {
        try{
            if(this.data.updateBusiness(
                    customer.get(0).getCid(),

                    vatinField.getText(),
                    regionField.getText(),
                    cityField.getText(),
                    streetField.getText(),
                    houseField.getText(),
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