package login;

import environment.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.sql.SQLException;
import java.util.ArrayList;

public class LoginController extends ViewController{

    @FXML
    TextField loginField;
    @FXML
    PasswordField passwordField;

    @FXML
    private Text actiontarget;
    public void handleSubmitButtonAction(ActionEvent actionEvent) throws SQLException {
        try {
            ArrayList arrayList = this.data.login(loginField.getText(), passwordField.getText());
            Login login = (Login) arrayList.get(0);

            if(login.getUser_type().equals("E")){
                transitionTo("employeeMain");
            }
            else {
                transitionTo("customerMain");
            }
        }
        catch (SQLException sqle){
            String errmes = this.data.getErrorMessage(sqle);
            actiontarget.setText(errmes);
        }

    }
}
