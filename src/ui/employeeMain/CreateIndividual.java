package ui.employeeMain;

import environment.ViewController;
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
public class CreateIndividual extends ViewController {
    public ArrayList arrayList = new ArrayList();

    @FXML
    private Text actiontarget;
    @FXML
    TextField firstNameField;
    @FXML
    TextField lastNameField;
    @FXML
    TextField passportField;
    @FXML
    TextField sexField;
    @FXML
    TextField bdateField;
    @FXML
    TextField vatinField;
    @FXML
    TextField regionField;
    @FXML
    TextField cityField;
    @FXML
    TextField streetField;
    @FXML
    TextField houseField;
    @FXML
    TextField apartmentField;
    @FXML
    TextField loginField;
    @FXML
    TextField passField;
    @FXML
    TextField moneyField;
    @FXML
    TextField phoneField;

    @FXML
    protected void init() {
        firstNameField.setText("");
        lastNameField.setText("");
        passportField.setText("");
        sexField.setText("");
        bdateField.setText("");
        vatinField.setText("");
        regionField.setText("");
        cityField.setText("");
        streetField.setText("");
        houseField.setText("");
        apartmentField.setText("");
        loginField.setText("");
        passField.setText("");
        moneyField.setText("");
        phoneField.setText("");

    }

    public void back(ActionEvent actionEvent) {
        arrayList.add("not null");
        transitionTo("employeeMain", arrayList);
    }

    public void createIndividual(ActionEvent actionEvent) throws SQLException {
        try{
            if(this.data.createIndividual(
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
