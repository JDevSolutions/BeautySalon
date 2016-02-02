package ua.com.jdev.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ua.com.jdev.dbase.Constants;

public class Client extends Person {

    private StringProperty cardNumber;

    {
        tableName = Constants.TABLE_CLIENTS;
    }

    public Client(String firstName, String secondName, String lastName) {
        super(firstName, secondName, lastName);
    }

    public Client(String firstName, String secondName, String lastName, String phone) {
        super(firstName, secondName, lastName, phone);
        this.cardNumber = new SimpleStringProperty("");
    }
    public Client(String firstName, String secondName, String lastName, String phone, String cardNumber) {
        super(firstName, secondName, lastName, phone);
        this.cardNumber = new SimpleStringProperty(cardNumber);
    }

    public Client() {
        super(null, null, null, null);
        this.cardNumber = new SimpleStringProperty(null);
    }

    public String getCardNumber() {
        return cardNumber.get();
    }

    public StringProperty cardNumberProperty() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber.set(cardNumber);
    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO " + tableName + " (firstName, secondName, lastName, phone, cardNumber) VALUES (" +
                createRequiredField(getFirstName()) + ", " +
                createField(getSecondName()) + ", " +
                createRequiredField(getLastName()) + ", " +
                createField(getPhone()) + ", " +
                createField(getCardNumber()) + ");";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE " + tableName +
                " SET firstName = " + createRequiredField(getFirstName()) +
                ", secondName = " + createField(getSecondName()) +
                ", lastName =  " + createRequiredField(getLastName()) +
                ", phone = " + createField(getPhone()) +
                ", cardNumber = " + createField(getCardNumber()) +
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

    @Override
    public String getSaleQuery() {
        return "";
    }
}
