package ui.userMain;

import javafx.beans.property.*;

/**
 * Created by svetl on 02.11.2016.
 */
public class Account {
    private final SimpleLongProperty num = new SimpleLongProperty();
    private final SimpleFloatProperty balance = new SimpleFloatProperty();
    public Account(long num, Float balance) {
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
    public Float getBalance() {
        return balance.get();
    }
    public void setBalance(float pBalance) {
        balance.set(pBalance);
    }
}
