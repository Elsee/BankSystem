package ui.employeeMain;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class CustomerI {
    private final SimpleIntegerProperty cid = new SimpleIntegerProperty();
    private final SimpleStringProperty firstname = new SimpleStringProperty();
    private final SimpleStringProperty lastname = new SimpleStringProperty();
    private final SimpleStringProperty passport = new SimpleStringProperty();
    private final SimpleStringProperty sex = new SimpleStringProperty();
    private final SimpleStringProperty birthdate = new SimpleStringProperty();
    private final SimpleStringProperty vatin = new SimpleStringProperty();
    private final SimpleStringProperty region = new SimpleStringProperty();
    private final SimpleStringProperty city = new SimpleStringProperty();
    private final SimpleStringProperty street = new SimpleStringProperty();
    private final SimpleStringProperty house = new SimpleStringProperty();
    private final SimpleStringProperty apartment = new SimpleStringProperty();
    private final SimpleStringProperty login = new SimpleStringProperty();
    private final SimpleStringProperty password = new SimpleStringProperty();
    private final SimpleStringProperty phone = new SimpleStringProperty();

    public CustomerI(int id,
                     String fname,
                     String lname,
                     String  pass,
                     String sex,
                     String bdate,
                     String vatin,
                     String reg,
                     String city,
                     String street,
                     String house,
                     String apart,
                     String log,
                     String password,
                     String phone) {
        setId(id);
        setFirstname(fname);
        setLastname(lname);
        setPassport(pass);
        setSex(sex);
        setBirthDate(bdate);
        setVatin(vatin);
        setRegion(reg);
        setCity(city);
        setStreet(street);
        setHouse(house);
        setApartment(apart);
        setLogin(log);
        setPassword(password);
        setPhone(phone);
    }

    public void setId(int id) { this.cid.set(id); }
    public void setFirstname(String fname) {
        this.firstname.set(fname);
    }
    public void setLastname(String lname) {
        this.lastname.set(lname);
    }
    public void setPassport(String  pass) {
        this.passport.set(pass);
    }
    public void setSex(String s) {
        this.sex.set(s);
    }
    public void setBirthDate(String bd) {
        this.birthdate.set(bd);
    }
    public void setVatin(String vat) {
        this.vatin.set(vat);
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
    public void setApartment(String ap) {
        this.apartment.set(ap);
    }
    public void setLogin(String log) {
        this.login.set(log);
    }
    public void setPassword(String pass) {
        this.password.set(pass);
    }
    public void setPhone(String ph) {
        this.phone.set(ph);
    }

    public Integer getCid() {
        return cid.get();
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

    public String getSex() {
        return sex.get();
    }

    public String getBirthDate() {
        return this.birthdate.get();
    }
    public String getVatin() {
        return this.vatin.get();
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
    public String getApartment() {
        return this.apartment.get();
    }
    public String getLogin() {
        return this.login.get();
    }
    public String getPassword() {
        return this.password.get();
    }
    public String getPhone() {
        return this.phone.get();
    }
}
