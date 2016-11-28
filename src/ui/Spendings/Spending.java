package ui.Spendings;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by cubazis on 28.11.16.
 */
public class Spending {
    private static Double total = 0.0;
    private final SimpleStringProperty category = new SimpleStringProperty();
    private final SimpleStringProperty account = new SimpleStringProperty();
    private final SimpleDoubleProperty summ = new SimpleDoubleProperty();
    private final SimpleIntegerProperty percent = new SimpleIntegerProperty();

    public Spending(String category, Double summ, String account){
        setCategory(category);
        setSumm(summ);
        setAccount(account);
        setTotal(summ);
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category){
        this.category.set(category);
    }

    public String getAccount() {
        return account.get();
    }

    public void setAccount(String account){
        this.account.set(account);
    }

    public Double getSumm(){
        return summ.get();
    }
    public void setSumm(Double summ){
        this.summ.set(summ);
    }

    public Integer getPercent(){
        return percent.get();
    }

    public void setPercent(){
        this.percent.set((int)Math.round((this.getSumm()/getTotal())*100.0));
    }

    public static Double getTotal(){
        return total;
    }
    public static void setTotal(Double summ){
        total += summ;
    }
}
