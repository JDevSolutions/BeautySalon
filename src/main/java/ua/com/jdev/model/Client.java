package ua.com.jdev.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Client extends Person {

    private StringProperty cardNumber;

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
}
