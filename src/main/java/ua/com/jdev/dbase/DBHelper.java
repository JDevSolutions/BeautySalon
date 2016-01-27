
package ua.com.jdev.dbase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Level;
import ua.com.jdev.model.*;

import org.apache.log4j.Logger;

/**
 * Created by Yurii Mikhailichenko on 17.01.2016.
 *
 * @since 0.1.1
 */

public class DBHelper {

    private static DBRepository dbRep = new DBRepository();

    private static ObservableList<? extends Base> outcomingData;

    public static final String DATABASE_NAME = "iris_db";
    public static final String TABLE_EMPLOYEES = "employees";
    public static final String TABLE_ORDERS = "orders";
    public static final String TABLE_GOODS = "goods";
    public static final String TABLE_RENTERS = "renters";
    public static final String TABLE_CLIENTS = "clients";
    public static final String TABLE_CARDS = "cards";

    private final static Logger log = Logger.getLogger(DBHelper.class);

    public static ObservableList<? extends Base> getData(String tableName) {
        String query = "SELECT * FROM " + tableName + ";";
        outcomingData = dbRep.executeQuery(query, tableName);
        return outcomingData;
    }

    public static void insert(Client client) {
        StringBuilder query = new StringBuilder("INSERT INTO " + TABLE_CLIENTS + " (firstName, secondName, lastName, " +
                "phone, cardNumber) VALUES (" +  appendRequiredField(client.getFirstName()) + ", " +
                appendField(client.getSecondName()) + ", " +  appendRequiredField(client.getLastName()) + ", " +
                appendField(client.getPhone()) + ", " + appendField(client.getCardNumber()) + ");");
        log.log(Level.INFO, query.toString());
        dbRep.executeUpdate(query.toString());
    }

    public static void insert(Goods goods)  {
        StringBuilder query = new StringBuilder("INSERT INTO " + TABLE_GOODS + " (code, name, price, amount) VALUES (" +
                appendRequiredField(goods.getCode()) + ", " + appendRequiredField(goods.getName()) + ", " +
                appendField(goods.getPrice()) + ", " + appendField(goods.getAmount()));
        log.log(Level.INFO, query.toString());
        dbRep.executeUpdate(query.toString());
    }

    public static void insert(Employee employee)  {
        StringBuilder query = new StringBuilder("INSERT INTO " + TABLE_GOODS + " (firstName, secondName, lastName, phone, " +
                "profession) VALUES (" + appendRequiredField(employee.getFirstName()) + ", " +
                appendField(employee.getSecondName()) + ", " + appendRequiredField(employee.getLastName()) + ", " +
                appendField(employee.getPhone()) + appendRequiredField(employee.getPosition()) + ");");
        log.log(Level.INFO, query.toString());
        dbRep.executeUpdate(query.toString());
    }

    public static void insert(ScheduleRecord record) {
        //not implemented
    }

    public static void update(Client client) {
        StringBuilder query = new StringBuilder("UPDATE " + TABLE_CLIENTS + " SET firstName = " + appendRequiredField(client.getFirstName()) +
                ", secondName = " + appendField(client.getSecondName()) + ", lastName =  " +
                appendRequiredField(client.getLastName()) + ", phone = " + appendField(client.getPhone()) +
                ", cardNumber = " + appendRequiredField(client.getCardNumber()) + " WHERE id = " + client.getId() + ";");
        log.log(Level.INFO, query.toString());
        dbRep.executeUpdate(query.toString());
    }

    public static void update(Goods goods)  {
        StringBuilder query = new StringBuilder("UPDATE " + TABLE_GOODS + " SET code = " + appendRequiredField(goods.getCode()) +
                ", name = " + appendField(goods.getName()) + ", price =  " + appendField(goods.getPrice()) +
                ", amount = " + appendField(goods.getAmount()) + ";");
        log.log(Level.INFO, query.toString());
        dbRep.executeUpdate(query.toString());
    }

    public static void update(Employee employee) {
        StringBuilder query = new StringBuilder("UPDATE " + TABLE_EMPLOYEES + " SET firstName = " + appendRequiredField(employee.getFirstName()) +
                ", secondName = " + appendField(employee.getSecondName()) + ", lastName =  " +
                appendRequiredField(employee.getLastName()) + ", phone = " + appendField(employee.getPhone()) +
                ", profession = " + appendRequiredField(employee.getPosition()) + " WHERE id = " + employee.getId() + ";");
        log.log(Level.INFO, query.toString());
        dbRep.executeUpdate(query.toString());
    }

    public static void update(ScheduleRecord record) {
        //not implemented
    }


    private static String appendField(String param) {
        /**
         * Служебный метод для создания строк
         */
        return !param.equals("") ? "'" + param + "'" : "NULL";
    }

    private static String appendRequiredField(String param) throws IllegalArgumentException {
        /**
         * Служебный метод для создания NOT NULL строк
         */
        try {
            if (param.equals("")) throw new IllegalArgumentException("Illegal value on field!");
        } catch (NullPointerException npe) {
            throw new IllegalArgumentException("Illegal value on field!");
        }
        return "'" + param + "'";
    }



}

