package ui.employeeMain;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by svetl on 14.11.2016.
 */
public class CustomerI {
    private final SimpleIntegerProperty cid = new SimpleIntegerProperty();
    private final SimpleStringProperty firstname = new SimpleStringProperty();
    private final SimpleStringProperty lastname = new SimpleStringProperty();
    private final SimpleStringProperty passport = new SimpleStringProperty();

    public CustomerI(int id, String fname, String lname, String  pass) {
        setId(id);
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
    public void setPassport(String  pass) {
        this.passport.set(pass);
    }

    public String getFirstname() {
        return firstname.get();
    }

    public String getLastname() {
        return lastname.get();
    }

    public String getPassport() {
        return passport.get();
    }

    public Integer getCid() {
        return cid.get();
    }

    public void setId(int id) { this.cid.set(id); }
}
