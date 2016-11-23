package ui.employeeMain;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CustomerAccount {
    private final SimpleIntegerProperty aid = new SimpleIntegerProperty();
    private final SimpleStringProperty accountNum = new SimpleStringProperty();
    private final SimpleIntegerProperty customerId = new SimpleIntegerProperty();
    private final SimpleStringProperty openDate = new SimpleStringProperty();
    private final SimpleStringProperty closeDate = new SimpleStringProperty();
    private final SimpleBooleanProperty activation = new SimpleBooleanProperty();
    private final SimpleStringProperty balance = new SimpleStringProperty();

    public CustomerAccount(int id, String accnum, int cid, String open_date, String close_date, boolean activ, String bal) {
        setId(id);
        setAccNum(accnum);
        setCId(cid);
        setODate(open_date);
        setCDate(close_date);
        setActivation(activ);
        setBalance(bal);
    }

    public void setId(int id) { this.aid.set(id); }
    public void setAccNum(String accnum) {
        this.accountNum.set(accnum);
    }
    public void setCId(int cid) { this.customerId.set(cid); }
    public void setODate(String oDate) {
        this.openDate.set(oDate);
    }
    public void setCDate(String  cDate) {
        this.closeDate.set(cDate);
    }
    public void setActivation(boolean act) {
        this.activation.set(act);
    }
    public void setBalance(String bal) {
        this.balance.set(bal);
    }

    public int getAid() {
        return this.aid.get();
    }
    public String getAccountNum() {
        return this.accountNum.get();
    }
    public int getCid() {
        return this.customerId.get();
    }
    public String getOpenDate() {
        return this.openDate.get();
    }
    public String getCloseDate() {
        return this.closeDate.get();
    }
    public boolean getActivation() {
        return this.activation.get();
    }
    public String getBalance() {
        return this.balance.get();
    }
}
