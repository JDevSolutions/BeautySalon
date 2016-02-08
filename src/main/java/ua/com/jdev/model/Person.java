package ua.com.jdev.model;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Person extends BaseClass {

    protected StringProperty firstName;
    protected StringProperty secondName;
    protected StringProperty lastName;
    protected StringProperty phone;
    private ReadOnlyStringWrapper fullName = new ReadOnlyStringWrapper();

    public Person(String firstName, String secondName, String lastName) {
        this.firstName = new SimpleStringProperty(firstName);
        this.secondName = new SimpleStringProperty(secondName);
        this.lastName = new SimpleStringProperty(lastName);
    }

    public Person(String firstName, String secondName, String lastName, String phone) {
        this.firstName = new SimpleStringProperty(firstName);
        this.secondName = new SimpleStringProperty(secondName);
        this.lastName = new SimpleStringProperty(lastName);
        this.phone = new SimpleStringProperty(phone);

        this.fullName.bind(Bindings.concat(this.lastName, " ", this.firstName, " ", this.secondName));
    }

    protected Person() {}

    public ReadOnlyStringProperty fullNameProperty() {
        return fullName.getReadOnlyProperty();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getSecondName() {
        return secondName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty secondNameProperty() {
        return secondName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public void setSecondName(String secondName) {
        this.secondName.set(secondName);
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getSecondName() + " " + getLastName();
    }
}
