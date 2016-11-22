package ui.employeeMain;

import environment.ViewController;
import javafx.event.ActionEvent;

/**
 * Created by cubazis on 22.11.16.
 */
public class CreateBusiness  extends ViewController {

    public void back(ActionEvent actionEvent) {
        transitionTo("employeeMain");
    }

    public void createBusiness(ActionEvent actionEvent) {
        transitionTo("employeeMain");
    }
}