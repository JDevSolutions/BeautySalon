package ua.com.jdev.dbase;

import javafx.collections.ObservableList;

import ua.com.jdev.model.BaseClass;
import ua.com.jdev.model.Client;
import ua.com.jdev.model.Goods;
import ua.com.jdev.model.Employee;
import ua.com.jdev.model.Order;

/**
 * Created by Yurii Mikhailichenko on 17.01.2016.
 *
 * @since 0.1.1
 */

public class DBHelper {

    private static DBRepository dbRep = new DBRepository();
    private static ObservableList<? extends BaseClass> outcomingData;

    //private final static Logger log = Logger.getLogger(DBHelper.class);

    public static ObservableList<? extends BaseClass> getData(String tableName) {
        String query = "SELECT * FROM " + tableName + " WHERE isActive = true;";
        outcomingData = dbRep.executeQuery(query, tableName);
        return outcomingData;
    }

    public static void insert(BaseClass model) {
        String query = model.getInsertQuery();
        dbRep.executeUpdate(query);
        model.setId(dbRep.getLastId(model.getTableName()));
    }

    public static void update(BaseClass model) {
        String query = model.getUpdateQuery();
        dbRep.executeUpdate(query);
    }

    public static void delete(BaseClass model) {
        String query = model.getDeleteQuery();
        dbRep.executeUpdate(query);
    }

    public static void restore(BaseClass model) {
        String query = model.getRestoreQuery();
        dbRep.executeUpdate(query);
    }

    public static void sale(BaseClass model) {
        String query = model.getSaleQuery();
        dbRep.executeUpdate(query);
    }

    public static String createField(String param) {
        /**
         * Служебный метод для создания строк
         */
        return !param.trim().equals("") ? '\'' + param.trim() + '\'' : "NULL";
    }

    public static String createRequiredField(String param) throws IllegalArgumentException {
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