package ui.customerMain;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by svetl on 02.11.2016.
 */
public class Transaction {
    private final SimpleStringProperty time = new SimpleStringProperty();
    private final SimpleStringProperty from = new SimpleStringProperty();
    private final SimpleStringProperty to = new SimpleStringProperty();
    private final SimpleStringProperty amount = new SimpleStringProperty();
    public Transaction(String pTime, String pFrom, String pTo, String pAmount) {
        setTime(pTime);
        setFrom(pFrom);
        setTo(pTo);
        setAmount(pAmount);
    }

    public String getTime() {
        return time.get();
    }

    public String getFrom() {
        return from.get();
    }

    public String getTo() {
        return to.get();
    }

    public String getAmount() {
        return amount.get();
    }


    public void setFrom(String pFrom) {
        this.from.set(pFrom);
    }
    public void setTo(String pTo) {
        this.to.set(pTo);
    }
    public void setTime(String pTime) {
        this.time.set(pTime);
    }
    public void setAmount(String pAmount) {
        this.amount.set(pAmount);
    }

}
