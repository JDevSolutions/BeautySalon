package ua.com.jdev.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Goods extends Base {

    private StringProperty code;
    private StringProperty name;
    private StringProperty price;
    private IntegerProperty amount;

    public Goods() {
        this(null, null, null, 0);
    }

    public Goods(String code, String name, String price, int amount) {
        this.code = new SimpleStringProperty(code);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleStringProperty(price);
        this.amount = new SimpleIntegerProperty(amount);
    }

    public String getCode() {
        return code.get();
    }

    public StringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPrice() {
        return price.get();
    }

    public StringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public int getAmount() {
        return amount.get();
    }

    public IntegerProperty amountProperty() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }
}
