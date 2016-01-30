package ua.com.jdev.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Employee extends Person {

    private StringProperty position;
    // private int percent;

    public Employee() {
        super(null, null, null, null);
        this.position = new SimpleStringProperty(null);
    }

    public Employee(String firstName, String secondName, String lastName) {
        super(firstName, secondName, lastName);
    }

    public Employee(String firstName, String secondName, String lastName, String phone, String position){
        super(firstName, secondName, lastName, phone);
        this.position = new SimpleStringProperty(position);
    }

    public String getPosition() {
        return position.get();
    }

    public StringProperty positionProperty() {
        return position;
    }

    public void setPosition(String position) {
        this.position.set(position);
    }
}
