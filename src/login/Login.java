package login;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by cubazis on 02.11.16.
 */
public class Login {
    private final SimpleIntegerProperty user_id = new SimpleIntegerProperty();
    private final SimpleStringProperty user_login= new SimpleStringProperty();
    private final SimpleStringProperty user_pass = new SimpleStringProperty();

    public Login(int id, String login, String pass){
        setUser_id(id);
        setUser_login(login);
        setUser_pass(pass);
    }

    public int getUser_id() {
        return user_id.get();
    }
    public void  setUser_id(int id){
        this.user_id.set(id);
    }

    public void  setUser_login(String login){
        this.user_login.set(login);
    }

    public void  setUser_pass(String pass){
        this.user_login.set(pass);
    }
    public SimpleIntegerProperty user_idProperty() {
        return user_id;
    }

    public String getUser_login() {
        return user_login.get();
    }

    public SimpleStringProperty user_loginProperty() {
        return user_login;
    }

    public String getUser_pass() {
        return user_pass.get();
    }

    public SimpleStringProperty user_passProperty() {
        return user_pass;
    }
}
