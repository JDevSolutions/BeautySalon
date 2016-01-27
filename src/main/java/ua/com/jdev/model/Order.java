package ua.com.jdev.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Order extends Base {

    private StringProperty time;
    private StringProperty employee;
    private StringProperty client;
    private StringProperty price;
    private boolean paid = false;

    public Order() {
        this(null, null, null, null);
    }

    public Order(String time, String employee, String client) {
        this.time = new SimpleStringProperty(time);
        this.employee = new SimpleStringProperty(employee);
        this.client = new SimpleStringProperty(client);
        this.price = new SimpleStringProperty("");
    }

    public Order(String time, String employee, String client, String price) {
        this.time = new SimpleStringProperty(time);
        this.employee = new SimpleStringProperty(employee);
        this.client = new SimpleStringProperty(client);
        this.price = new SimpleStringProperty(price);
    }

    public Order(String time, String employee, String client, String price, boolean paid) {
        this.time = new SimpleStringProperty(time);
        this.employee = new SimpleStringProperty(employee);
        this.client = new SimpleStringProperty(client);
        this.price = new SimpleStringProperty(price);
        this.paid = paid;
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getEmployee() {
        return employee.get();
    }

    public StringProperty employeeProperty() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee.set(employee);
    }

    public String getClient() {
        return client.get();
    }

    public StringProperty clientProperty() {
        return client;
    }

    public void setClient(String client) {
        this.client.set(client);
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

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
