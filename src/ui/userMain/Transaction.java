package ui.userMain;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Timestamp;

/**
 * Created by svetl on 02.11.2016.
 */
public class Transaction {
    private final SimpleStringProperty time = new SimpleStringProperty();
    private final SimpleLongProperty from = new SimpleLongProperty();
    private final SimpleLongProperty to = new SimpleLongProperty();
    private final SimpleStringProperty amount = new SimpleStringProperty();
    public Transaction(String pTime, Long pFrom, Long pTo, String pAmount) {
        setTime(pTime);
        setFrom(pFrom);
        setTo(pTo);
        setAmount(pAmount);
    }

    public String getTime() {
        return time.get();
    }

    public void setFrom(long pFrom) {
        this.from.set(pFrom);
    }
    public void setTo(long pTo) {
        this.to.set(pTo);
    }
    public void setTime(String pTime) {
        this.time.set(pTime);
    }
    public void setAmount(String pAmount) {
        this.amount.set(pAmount);
    }

}
