package ui.employeeMain;

import environment.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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
    public TextField moneyField;
    @FXML
    public TextField phoneField;

    public void back(ActionEvent actionEvent) {
        transitionTo("employeeMain");
    }

    public void updateBusiness(ActionEvent actionEvent) {
        transitionTo("employeeMain");
    }
}