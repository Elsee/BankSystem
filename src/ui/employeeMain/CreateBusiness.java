package ui.employeeMain;

import environment.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.SQLException;

/**
 * Created by cubazis on 22.11.16.
 */
public class CreateBusiness  extends ViewController {

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
    public TextField moneyField;
    @FXML
    public TextField phoneField;
    @FXML
    private Text actiontarget;

    public void back(ActionEvent actionEvent) {
        transitionTo("employeeMain");
    }

    public void createBusiness(ActionEvent actionEvent) throws SQLException {
        try{
            actiontarget.setText("");
            if(this.data.createBusiness(
                    vatinField.getText(),
                    regionField.getText(),
                    cityField.getText(),
                    streetField.getText(),
                    houseField.getText(),
                    moneyField.getText(),
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