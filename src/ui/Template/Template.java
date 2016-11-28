package ui.Template;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by cubazis on 28.11.16.
 */
public class Template {
    private final SimpleStringProperty from = new SimpleStringProperty();
    private final SimpleStringProperty to = new SimpleStringProperty();
    private final SimpleStringProperty amount = new SimpleStringProperty();

    public Template(String pFrom, String pTo, String pAmount){
        setFrom(pFrom);
        setTo(pTo);
        setAmount(pAmount);
    }
    private void setFrom(String pFrom) {
        this.from.set(pFrom);
    }
    private void setTo(String pTo) {
        this.to.set(pTo);
    }
    private void setAmount(String pAmount) {
        this.amount.set(pAmount);
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
}
