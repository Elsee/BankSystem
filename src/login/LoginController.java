package login;

import environment.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class LoginController extends ViewController{
    @FXML
    private Text actiontarget;
    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        if(true){
            this.transition();
        }
        else {
            actiontarget.setText("Login or password incorrect");
        }
    }
    @FXML
    public void transition() {
        transitionTo("screen2");
    }
}
