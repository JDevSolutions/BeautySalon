package ua.com.jdev.model;

import javafx.beans.property.*;
import ua.com.jdev.dbase.Constants;

public class Goods extends BaseClass {

    private StringProperty code;
    private StringProperty name;
    private DoubleProperty price;
    private IntegerProperty amount;

    {
        this.tableName = Constants.TABLE_GOODS;
    }

    public Goods() {
        this(null, null, 0.0, 0);
    }

    public Goods(String code, String name, double price, int amount) {
        this.code = new SimpleStringProperty(code);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
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

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
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

    @Override
    public String getInsertQuery() {
        //log.log(Level.INFO, query.toString());
        return "INSERT INTO " + tableName + " (code, name, price, amount) VALUES (" +
                createRequiredField(getCode()) + ", " +
                createRequiredField(getName()) + ", " +
                createField(String.valueOf(getPrice())) + ", " +
                createField(String.valueOf(getAmount())) + ");";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE " + tableName + " SET code = " + createRequiredField(getCode()) +
                ", name = " + createField(getName()) +
                ", price =  " + createField(String.valueOf(getPrice())) +
                ", amount = " + createField(String.valueOf(getAmount())) +
                " WHERE id = " + getId() + ";";
    }

    @Override
    public String getDeleteQuery() {
        return "UPDATE " + tableName + " SET isActive = '0' WHERE id = " + getId() + ";";
    }

    @Override
    public String getRestoreQuery() {
        return "UPDATE " + tableName + " SET isActive = '1' WHERE id = " + getId() + ";";
    }

    public String[] getSaleQuery(int count, double price) {
        String firstQuery = "UPDATE " + tableName + " SET amount = (amount - " + count + ") WHERE id = " + getId() + ";";
        String secondQuery = "INSERT INTO sales (goods_id, datetime, price, count) VALUES (" +
                getId() + ", " +
                "current_timestamp, " +
                price + ", " +
                count + ");";
        return new String[]{firstQuery, secondQuery};
    }
}
