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

    protected static String createField(String param) {
        /**
         * Служебный метод для создания строк
         */
        return !param.trim().equals("") ? '\'' + param.trim() + '\'' : "NULL";
    }

    protected static String createRequiredField(String param) throws IllegalArgumentException {
        /**
         * Служебный метод для создания NOT NULL строк
         */
        try {
            if (param.trim().equals("")) throw new IllegalArgumentException("Illegal value on field!");
        } catch (NullPointerException npe) {
            throw new IllegalArgumentException("Illegal value on field!");
        }
        return '\'' + param.trim() + '\'';
    }
}
