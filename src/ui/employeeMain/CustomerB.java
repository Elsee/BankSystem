package ui.employeeMain;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CustomerB {
    private final SimpleIntegerProperty cid = new SimpleIntegerProperty();
    private final SimpleIntegerProperty oid = new SimpleIntegerProperty();
    private final SimpleStringProperty orgNum = new SimpleStringProperty();
    private final SimpleStringProperty region = new SimpleStringProperty();
    private final SimpleStringProperty city = new SimpleStringProperty();
    private final SimpleStringProperty street = new SimpleStringProperty();
    private final SimpleStringProperty house = new SimpleStringProperty();
    private final SimpleStringProperty phone = new SimpleStringProperty();

    public CustomerB(int id,
                     String orgnum,
                     String reg,
                     String city,
                     String street,
                     String house,
                     String phone) {
        setId(id);
        setOrgNum(orgnum);
        setRegion(reg);
        setCity(city);
        setStreet(street);
        setHouse(house);
        setPhone(phone);
    }

    public void setId(int id) { this.cid.set(id); }
    public void setOrgNum(String onum) {
        this.orgNum.set(onum);
    }
    public void setRegion(String r) {
        this.region.set(r);
    }
    public void setCity(String city) {
        this.city.set(city);
    }
    public void setStreet(String st) {
        this.street.set(st);
    }
    public void setHouse(String h) {
        this.house.set(h);
    }
    public void setPhone(String ph) {
        this.phone.set(ph);
    }

    public String getOrgNum() {
        return orgNum.get();
    }
    public Integer getCid() {
        return cid.get();
    }
    public String getRegion() {
        return this.region.get();
    }
    public String getCity() {
        return this.city.get();
    }
    public String getStreet() {
        return this.street.get();
    }
    public String getHouse() {
        return this.house.get();
    }
    public String getPhone() {
        return this.phone.get();
    }
}
