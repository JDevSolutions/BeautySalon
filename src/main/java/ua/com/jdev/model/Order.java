package ua.com.jdev.model;

import javafx.beans.property.*;

public class Order extends BaseClass {

    private StringProperty time;
    private ObjectProperty employee;
    private ObjectProperty client;
    private DoubleProperty price;
    private boolean paid = false;

    public Order() {
        this(null, null, null);
    }

    public Order(String time, Employee employee, Client client) {
        this.time = new SimpleStringProperty(time);
        this.employee = new SimpleObjectProperty(employee);
        this.client = new SimpleObjectProperty(client);
        this.price = new SimpleDoubleProperty(0.0);
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

    public Employee getEmployee() {
        return (Employee) employee.get();
    }

    public ObjectProperty employeeProperty() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee.set(employee);
    }

    public Client getClient() {
        return (Client) client.get();
    }

    public ObjectProperty clientProperty() {
        return client;
    }

    public void setClient(Client client) {
        this.client.set(client);
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

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
