package ui.employeeMain;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CustomerB {
    private final SimpleIntegerProperty cid = new SimpleIntegerProperty();
    private final SimpleIntegerProperty oid = new SimpleIntegerProperty();
    private final SimpleStringProperty orgNum = new SimpleStringProperty();

    public CustomerB(int id, String orgnum) {
        setId(id);
        setOrgNum(orgnum);
    }

    public void setOrgNum(String onum) {
        this.orgNum.set(onum);
    }

    public String getOrgNum() {
        return orgNum.get();
    }

    public Integer getCid() {
        return cid.get();
    }

    public void setId(int id) { this.cid.set(id); }
}
