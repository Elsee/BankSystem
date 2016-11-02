package ui.userMain;

import javafx.beans.property.*;

/**
 * Created by svetl on 02.11.2016.
 */
public class Account {
    private final SimpleLongProperty account_id = new SimpleLongProperty();
    private final SimpleLongProperty id_customer = new SimpleLongProperty();
    private final SimpleStringProperty open_date = new SimpleStringProperty();
    private final SimpleStringProperty close_date = new SimpleStringProperty();
    private final SimpleBooleanProperty active = new SimpleBooleanProperty();
    private final SimpleStringProperty last_activity = new SimpleStringProperty();
    private final SimpleStringProperty balance = new SimpleStringProperty();

    public Account(long account_id, long id_customer, String open_date, String close_date, Boolean active, String last_activity, String balance) {
        setAccount_id(account_id);
        setId_customer(id_customer);
        setOpen_date(open_date);
        setClose_date(close_date);
        setActive(active);
        setLast_activity(last_activity);
        setBalance(balance);
    }

    public long getAccount_id() {
        return account_id.get();
    }

    public SimpleLongProperty account_idProperty() {
        return account_id;
    }

    public void setAccount_id(long id) {
        this.account_id.set(id);
    }

    public long getId_customer() {
        return id_customer.get();
    }

    public SimpleLongProperty id_customerProperty() {
        return id_customer;
    }

    public void setId_customer(long id) {
        this.id_customer.set(id);
    }

    public String getOpen_date() {
        return open_date.get();
    }

    public SimpleStringProperty open_dateProperty() {
        return open_date;
    }

    public void setOpen_date(String date) {
        this.open_date.set(date);
    }

    public String getClose_date() {
        return close_date.get();
    }

    public SimpleStringProperty close_dateProperty() {
        return close_date;
    }

    public void setClose_date(String date) {
        this.close_date.set(date);
    }

    public Boolean getActive() {
        return active.get();
    }

    public SimpleBooleanProperty activeProperty() {
        return active;
    }

    public void setActive(Boolean bool) {
        this.active.set(bool);
    }

    public String getLast_activity() {
        return last_activity.get();
    }

    public SimpleStringProperty last_activityProperty() {
        return last_activity;
    }

    public void setLast_activity(String  activity) {
        this.last_activity.set(activity);
    }

    public String getBalance() {
        return balance.get();
    }

    public SimpleStringProperty balanceProperty() {
        return balance;
    }

    public void setBalance(String  balance) {
        this.balance.set(balance);
    }
}
