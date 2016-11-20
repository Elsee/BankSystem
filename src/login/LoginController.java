package login;

import environment.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.sql.SQLException;

public class LoginController extends ViewController{

    @FXML
    TextField loginField;
    @FXML
    PasswordField passwordField;

    @FXML
    private Text actiontarget;
    public void handleSubmitButtonAction(ActionEvent actionEvent) throws SQLException {
        //this.data.test();
        if(this.data.checkLogin(loginField.getText(), passwordField.getText())){
            System.out.println("Logged in");
            this.transition();
        }
        else {
            System.out.println("Login or password incorrect");
            actiontarget.setText("Login or password incorrect");
        }
    }
    @FXML
    public void transition()  throws SQLException {
        if (this.data.isCustomer(loginField.getText()))  {
            transitionTo("screen2");
        }
        else {
            transitionTo("employeeMain");
        }
    }


}
