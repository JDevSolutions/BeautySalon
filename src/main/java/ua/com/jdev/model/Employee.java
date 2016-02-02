package ua.com.jdev.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ua.com.jdev.dbase.Constants;

public class Employee extends Person {

    private StringProperty position;
    // private int percent;

    {
        this.tableName = Constants.TABLE_EMPLOYEES;
    }

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

    @Override
    public String getInsertQuery() {
        return "INSERT INTO " + tableName + " (firstName, secondName, lastName, phone, " + "profession) VALUES (" +
                createRequiredField(getFirstName()) + ", " +
                createField(getSecondName()) + ", " +
                createRequiredField(getLastName()) + ", " +
                createField(getPhone()) + ", " +
                createRequiredField(getPosition()) + ");";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE " + tableName +
                " SET firstName = " + createRequiredField(getFirstName()) +
                ", secondName = " + createField(getSecondName()) +
                ", lastName =  " + createRequiredField(getLastName()) +
                ", phone = " + createField(getPhone()) +
                ", profession = " + createRequiredField(getPosition()) +
                " WHERE id = " + getId() + ";";
    }

    @Override
    public String getDeleteQuery() {
        return "UPDATE " + tableName +
                " SET isActive = '0' WHERE id = " + getId() + ";";
    }

    @Override
    public String getRestoreQuery() {
        return "UPDATE " + tableName + " SET isActive = '1' WHERE id = " + getId() + ";";
    }

    @Override
    public String getSaleQuery() {
        return null;
    }
}
