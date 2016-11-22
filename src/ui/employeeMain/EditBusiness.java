package ui.employeeMain;

import environment.ViewController;
import javafx.event.ActionEvent;

/**
 * Created by cubazis on 22.11.16.
 */
public class EditBusiness extends ViewController {

    public void back(ActionEvent actionEvent) {
        transitionTo("employeeMain");
    }

    public void updateBusiness(ActionEvent actionEvent) {
        transitionTo("employeeMain");
    }
}