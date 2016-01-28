package ua.com.jdev.model;

import javafx.beans.property.StringProperty;

public abstract class BaseClass {

    protected StringProperty id;

    public void setId(String id) {
        this.id.set(id);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }
}
