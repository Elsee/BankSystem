package environment;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by cubazis on 22.11.16.
 */
public class Errors {
    private final SimpleStringProperty errNo = new SimpleStringProperty();
    private final SimpleStringProperty errMes = new SimpleStringProperty();

    public Errors(String errno, String errmes) {
        setErrNo(errno);
        setErrMes(errmes);
    }
    private void setErrNo(String errno) {
        this.errNo.set(errno);
    }
    private void setErrMes(String errmes) {
        this.errMes.set(errmes);
    }
}
