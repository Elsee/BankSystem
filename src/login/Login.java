package login;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by cubazis on 02.11.16.
 */
public class Login {
    private final SimpleIntegerProperty user_id = new SimpleIntegerProperty();
    private final SimpleIntegerProperty user_person_id= new SimpleIntegerProperty();
    private final SimpleStringProperty user_type = new SimpleStringProperty();

    public Login(int user_id, int user_person_id, String user_type){
        setUser_id(user_id);
        setUser_person_id(user_person_id);
        setUser_type(user_type);
    }

    private void setUser_id(int user_id) {
        this.user_id.set(user_id);
    }

    private void setUser_person_id(int user_person_id) {
        this.user_person_id.set(user_person_id);
    }

    private void setUser_type(String  user_type) {
        this.user_type.set(user_type);
    }

    public String getUser_type(){
        return this.user_type.get();
    }
    public int getPerson_id(){
        return this.user_person_id.get();
    }
}
