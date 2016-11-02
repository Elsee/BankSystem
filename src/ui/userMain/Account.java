package ui.userMain;

import javafx.beans.property.*;

/**
 * Created by svetl on 02.11.2016.
 */
public class Account {
    private final SimpleLongProperty num = new SimpleLongProperty();
    private final SimpleStringProperty balance = new SimpleStringProperty();
    public Account(long num, String balance) {
        setNum(num);
        setBalance(balance);
    }

    public long getNum() {
        return num.get();
    }
    public void setNum(long num) {
        this.num.set(num);
    }
    public String getNumString(){
        return ""+num.get();
    }
    public String getBalance() {
        return balance.get();
    }
    public void setBalance(String pBalance) {
        balance.set(pBalance);
    }
}
