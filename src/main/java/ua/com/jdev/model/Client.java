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
        StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(tableName).
                append(" (firstName, secondName, lastName, phone, cardNumber) VALUES (").
                append(getFirstName()).append(", ").
                append(getSecondName()).append(", ").
                append(getLastName()).append(", ").
                append(getPhone()).append(", ").
                append(getCardNumber()).append(");");
        //  log.log(Level.INFO, query.toString());
        return query.toString();
    }

    @Override
    public String getUpdateQuery() {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(tableName).
                append(" SET firstName = ").append(getFirstName()).
                append(", secondName = ").append(getSecondName()).
                append(", lastName =  ").append(getLastName()).
                append(", phone = ").append(getPhone()).
                append(", cardNumber = ").append(getCardNumber()).
                append(" WHERE id = ").append(getId()).
                append(";");
        //      log.log(Level.INFO, query.toString());
        return query.toString();
    }

    @Override
    public String getDeleteQuery() {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(tableName).
                append(" SET isActive = '0' WHERE id = ").
                append(getId()).append(";");
        return query.toString();
    }

    @Override
    public String getRestoreQuery() {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(tableName).
                append(" SET isActive = '1' WHERE id = ").
                append(getId());
        return query.toString();
    }

    @Override
    public String getSaleQuery() {
        return "";
    }
}
