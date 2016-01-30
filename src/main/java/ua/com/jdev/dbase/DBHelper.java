package ua.com.jdev.dbase;

import javafx.collections.ObservableList;

import ua.com.jdev.model.BaseClass;
import ua.com.jdev.model.Client;
import ua.com.jdev.model.Goods;
import ua.com.jdev.model.Employee;
import ua.com.jdev.model.Order;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

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

    public static void insert(Client client) {
        StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(Constants.TABLE_CLIENTS).
                append(" (firstName, secondName, lastName, phone, cardNumber) VALUES (").
                append(appendRequiredField(client.getFirstName())).append(", ").
                append(appendField(client.getSecondName())).append(", ").
                append(appendRequiredField(client.getLastName())).append(", ").
                append(appendField(client.getPhone())).append(", ").
                append(appendField(client.getCardNumber())).append(");");
        //  log.log(Level.INFO, query.toString());
        dbRep.executeUpdate(query.toString());
        client.setId(dbRep.getLastId(Constants.TABLE_CLIENTS));
    }

    public static void insert(Goods goods) {
        StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(Constants.TABLE_GOODS).
                append(" (code, name, price, amount) VALUES (").
                append(appendRequiredField(goods.getCode())).append(", ").
                append(appendRequiredField(goods.getName())).append(", ").
                append(appendField(String.valueOf(goods.getPrice()))).append(", ").
                append(appendField(String.valueOf(goods.getAmount()))).append(");");
        //log.log(Level.INFO, query.toString());
        dbRep.executeUpdate(query.toString());
        goods.setId(dbRep.getLastId(Constants.TABLE_GOODS));
    }

    public static void insert(Employee employee) {
        StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(Constants.TABLE_EMPLOYEES).append(" (firstName, secondName, lastName, phone, ").append("profession) VALUES (").
                append(appendRequiredField(employee.getFirstName())).append(", ").
                append(appendField(employee.getSecondName())).append(", ").
                append(appendRequiredField(employee.getLastName())).append(", ").
                append(appendField(employee.getPhone())).append(", ").
                append(appendRequiredField(employee.getPosition())).append(");");
//        log.log(Level.INFO, query.toString());
        dbRep.executeUpdate(query.toString());
        employee.setId(dbRep.getLastId(Constants.TABLE_EMPLOYEES));
    }

    public static void insert(Order order) {
/*
        StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(Constants.TABLE_ORDERS).
                append(" (client_id, employee_id, isPaid, price, date) VALUES (").
                append(appendRequiredField(order.getClient().getId())).append(", ").
                append(appendRequiredField(order.getEmployee().getId())).append(", ").
                append(order.isPaid() ? "'1'" : "'0'").append(", ").
                append(appendField(order.getPrice())).append(", ").
                append(appendField(order.getTime())).append(");");
        log.log(Level.INFO, query.toString());
        dbRep.executeUpdate(query.toString());
        order.setId(dbRep.getLastId(Constants.TABLE_ORDERS));
*/
    }

    public static void update(Client client) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(Constants.TABLE_CLIENTS).
                append(" SET firstName = ").append(appendRequiredField(client.getFirstName())).
                append(", secondName = ").append(appendField(client.getSecondName())).
                append(", lastName =  ").append(appendRequiredField(client.getLastName())).
                append(", phone = ").append(appendField(client.getPhone())).
                append(", cardNumber = ").append(appendRequiredField(client.getCardNumber())).
                append(" WHERE id = ").append(client.getId()).append(";");
        //      log.log(Level.INFO, query.toString());
        dbRep.executeUpdate(query.toString());
    }

    public static void update(Goods goods) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(Constants.TABLE_GOODS).
                append(" SET code = ").append(appendRequiredField(goods.getCode())).
                append(", name = ").append(appendField(goods.getName())).
                append(", price =  ").append(appendField(String.valueOf(goods.getPrice()))).
                append(", amount = ").append(appendField(String.valueOf(goods.getAmount()))).
                append(" WHERE id = ").append(goods.getId()).append(";");
        //    log.log(Level.INFO, query.toString());
        dbRep.executeUpdate(query.toString());
    }

    public static void update(Employee employee) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(Constants.TABLE_EMPLOYEES).
                append(" SET firstName = ").append(appendRequiredField(employee.getFirstName())).
                append(", secondName = ").append(appendField(employee.getSecondName())).
                append(", lastName =  ").append(appendRequiredField(employee.getLastName())).
                append(", phone = ").append(appendField(employee.getPhone())).
                append(", profession = ").append(appendRequiredField(employee.getPosition())).
                append(" WHERE id = ").append(employee.getId()).append(";");
        //  log.log(Level.INFO, query.toString());
        dbRep.executeUpdate(query.toString());
    }

    public static void update(Order order) {
/*
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(Constants.TABLE_ORDERS).
                append(" SET (client_id = ").append(appendRequiredField(order.getClient().getId())).
                append(", employee_id = ").append(appendRequiredField(order.getEmployee().getId())).
                append(", isPaid = ").append(order.isPaid() ? "'1'" : "'0'").
                append(", price = ").append(appendField(order.getPrice())).
                append(" date = ").append(appendField(order.getTime())).
                append(" WHERE id = ").append(order.getId()).append(";");
        log.log(Level.INFO, query.toString());
        dbRep.executeUpdate(query.toString());
*/
    }

    public static void delete(Client client) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(Constants.TABLE_CLIENTS).
                append(" SET isActive = '0' WHERE id = ").
                append(client.getId()).append(";");
        dbRep.executeUpdate(query.toString());
    }

    public static void delete(Goods goods) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(Constants.TABLE_CLIENTS).
                append(" SET isActive = '0' WHERE id = ").
                append(goods.getId()).append(";");
        dbRep.executeUpdate(query.toString());
    }

    public static void delete(Employee employee) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(Constants.TABLE_EMPLOYEES).
                append(" SET isActive = '0' WHERE id = ").
                append(employee.getId()).append(";");
        dbRep.executeUpdate(query.toString());
    }

    public static void delete(Order order) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(Constants.TABLE_ORDERS).
                append(" SET isActive = '0' WHERE id = ").
                append(order.getId()).append(";");
        dbRep.executeUpdate(query.toString());
    }

    public static void restore(Client client) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(Constants.TABLE_CLIENTS).
                append(" SET isActive = '1' WHERE id = ").
                append(client.getId());
        dbRep.executeUpdate(query.toString());
    }

    public static void restore(Goods goods) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(Constants.TABLE_CLIENTS).
                append(" SET isActive = '1' WHERE id = ").
                append(goods.getId()).append(";");
        dbRep.executeUpdate(query.toString());
    }

    public static void restore(Employee employee) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(Constants.TABLE_EMPLOYEES).
                append(" SET isActive = '1' WHERE id = ").
                append(employee.getId()).append(";");
        dbRep.executeUpdate(query.toString());
    }

    public static void restore(Order order) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(Constants.TABLE_ORDERS).
                append(" SET isActive = '1' WHERE id = ").
                append(order.getId()).append(";");
        dbRep.executeUpdate(query.toString());
    }

    private static String appendField(String param) {
        /**
         * Служебный метод для создания строк
         */
        return !param.trim().equals("") ? '\'' + param.trim() + '\'' : "NULL";
    }

    private static String appendRequiredField(String param) throws IllegalArgumentException {
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