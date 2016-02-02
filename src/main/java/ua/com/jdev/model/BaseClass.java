package ua.com.jdev.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ua.com.jdev.dbase.ModelRepository;

public abstract class BaseClass implements ModelRepository{

    protected StringProperty id;

    protected String tableName;

    {
        this.id = new SimpleStringProperty("");
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getTableName() {
        return tableName;
    }
}
