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
    @Deprecated
    public static void insert(Client client) {
        StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(Constants.TABLE_CLIENTS).
                append(" (firstName, secondName, lastName, phone, cardNumber) VALUES (").
                append(createRequiredField(client.getFirstName())).append(", ").
                append(createField(client.getSecondName())).append(", ").
                append(createRequiredField(client.getLastName())).append(", ").
                append(createField(client.getPhone())).append(", ").
                append(createField(client.getCardNumber())).append(");");
        //  log.log(Level.INFO, query.toString());
        dbRep.executeUpdate(query.toString());
        client.setId(dbRep.getLastId(Constants.TABLE_CLIENTS));
    }
    @Deprecated
    public static void insert(Goods goods) {
        StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(Constants.TABLE_GOODS).
                append(" (code, name, price, amount) VALUES (").
                append(createRequiredField(goods.getCode())).append(", ").
                append(createRequiredField(goods.getName())).append(", ").
                append(createField(String.valueOf(goods.getPrice()))).append(", ").
                append(createField(String.valueOf(goods.getAmount()))).append(");");
        //log.log(Level.INFO, query.toString());
        dbRep.executeUpdate(query.toString());
        goods.setId(dbRep.getLastId(Constants.TABLE_GOODS));
    }
    @Deprecated
    public static void insert(Employee employee) {
        StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(Constants.TABLE_EMPLOYEES).append(" (firstName, secondName, lastName, phone, ").append("profession) VALUES (").
                append(createRequiredField(employee.getFirstName())).append(", ").
                append(createField(employee.getSecondName())).append(", ").
                append(createRequiredField(employee.getLastName())).append(", ").
                append(createField(employee.getPhone())).append(", ").
                append(createRequiredField(employee.getPosition())).append(");");
//        log.log(Level.INFO, query.toString());
        dbRep.executeUpdate(query.toString());
        employee.setId(dbRep.getLastId(Constants.TABLE_EMPLOYEES));
    }
    @Deprecated
    public static void insert(Order order) {
        StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(Constants.TABLE_ORDERS).
                append(" (client_id, employee_id, isPaid, price, date) VALUES (").
                append(createRequiredField(order.getClient().getId())).append(", ").
                append(createRequiredField(order.getEmployee().getId())).append(", ").
                append(order.isPaid() ? "'1'" : "'0'").append(", ").
                append(createField(String.valueOf(order.getPrice()))).append(", ").
                append(createField(order.getTime())).append(");");
//        log.log(Level.INFO, query.toString());
        dbRep.executeUpdate(query.toString());
        order.setId(dbRep.getLastId(Constants.TABLE_ORDERS));
    }
    @Deprecated
    public static void update(Client client) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(Constants.TABLE_CLIENTS).
                append(" SET firstName = ").append(createRequiredField(client.getFirstName())).
                append(", secondName = ").append(createField(client.getSecondName())).
                append(", lastName =  ").append(createRequiredField(client.getLastName())).
                append(", phone = ").append(createField(client.getPhone())).
                append(", cardNumber = ").append(createRequiredField(client.getCardNumber())).
                append(" WHERE id = ").append(client.getId()).append(";");
        //      log.log(Level.INFO, query.toString());
        dbRep.executeUpdate(query.toString());
    }
    @Deprecated
    public static void update(Goods goods) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(Constants.TABLE_GOODS).
                append(" SET code = ").append(createRequiredField(goods.getCode())).
                append(", name = ").append(createField(goods.getName())).
                append(", price =  ").append(createField(String.valueOf(goods.getPrice()))).
                append(", amount = ").append(createField(String.valueOf(goods.getAmount()))).
                append(" WHERE id = ").append(goods.getId()).append(";");
        //    log.log(Level.INFO, query.toString());
        dbRep.executeUpdate(query.toString());
    }
    @Deprecated
    public static void update(Employee employee) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(Constants.TABLE_EMPLOYEES).
                append(" SET firstName = ").append(createRequiredField(employee.getFirstName())).
                append(", secondName = ").append(createField(employee.getSecondName())).
                append(", lastName =  ").append(createRequiredField(employee.getLastName())).
                append(", phone = ").append(createField(employee.getPhone())).
                append(", profession = ").append(createRequiredField(employee.getPosition())).
                append(" WHERE id = ").append(employee.getId()).append(";");
        //  log.log(Level.INFO, query.toString());
        dbRep.executeUpdate(query.toString());
    }
    @Deprecated
    public static void update(Order order) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(Constants.TABLE_ORDERS).
                append(" SET (client_id = ").append(createRequiredField(order.getClient().getId())).
                append(", employee_id = ").append(createRequiredField(order.getEmployee().getId())).
                append(", isPaid = ").append(order.isPaid() ? "'1'" : "'0'").
                append(", price = ").append(createField(String.valueOf(order.getPrice()))).
                append(" date = ").append(createField(order.getTime())).
                append(" WHERE id = ").append(order.getId()).append(";");
//        log.log(Level.INFO, query.toString());
        dbRep.executeUpdate(query.toString());
    }
    @Deprecated
    public static void delete(Client client) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(Constants.TABLE_CLIENTS).
                append(" SET isActive = '0' WHERE id = ").
                append(client.getId()).append(";");
        dbRep.executeUpdate(query.toString());
    }
    @Deprecated
    public static void delete(Goods goods) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(Constants.TABLE_CLIENTS).
                append(" SET isActive = '0' WHERE id = ").
                append(goods.getId()).append(";");
        dbRep.executeUpdate(query.toString());
    }
    @Deprecated
    public static void delete(Employee employee) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(Constants.TABLE_EMPLOYEES).
                append(" SET isActive = '0' WHERE id = ").
                append(employee.getId()).append(";");
        dbRep.executeUpdate(query.toString());
    }
    @Deprecated
    public static void delete(Order order) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(Constants.TABLE_ORDERS).
                append(" SET isActive = '0' WHERE id = ").
                append(order.getId()).append(";");
        dbRep.executeUpdate(query.toString());
    }
    @Deprecated
    public static void restore(Client client) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(Constants.TABLE_CLIENTS).
                append(" SET isActive = '1' WHERE id = ").
                append(client.getId());
        dbRep.executeUpdate(query.toString());
    }
    @Deprecated
    public static void restore(Goods goods) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(Constants.TABLE_CLIENTS).
                append(" SET isActive = '1' WHERE id = ").
                append(goods.getId()).append(";");
        dbRep.executeUpdate(query.toString());
    }
    @Deprecated
    public static void restore(Employee employee) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(Constants.TABLE_EMPLOYEES).
                append(" SET isActive = '1' WHERE id = ").
                append(employee.getId()).append(";");
        dbRep.executeUpdate(query.toString());
    }
    @Deprecated
    public static void restore(Order order) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(Constants.TABLE_ORDERS).
                append(" SET isActive = '1' WHERE id = ").
                append(order.getId()).append(";");
        dbRep.executeUpdate(query.toString());
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