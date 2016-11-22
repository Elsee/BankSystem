package ui.employeeMain;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by svetl on 14.11.2016.
 */
public class CustomerI {
    private final SimpleStringProperty firstname = new SimpleStringProperty();
    private final SimpleStringProperty lastname = new SimpleStringProperty();
    private final SimpleLongProperty passport = new SimpleLongProperty();

    public CustomerI(String fname, String lname, Long pass) {
        setFirstname(fname);
        setLastname(lname);
        setPassport(pass);
    }

    public void setFirstname(String fname) {
        this.firstname.set(fname);
    }
    public void setLastname(String lname) {
        this.lastname.set(lname);
    }
    public void setPassport(long pass) {
        this.passport.set(pass);
    }

    public SimpleStringProperty getFirstname() {
        return firstname;
    }

    public SimpleStringProperty getLastname() {
        return lastname;
    }

    public SimpleLongProperty getPassport() {
        return passport;
    }
}
