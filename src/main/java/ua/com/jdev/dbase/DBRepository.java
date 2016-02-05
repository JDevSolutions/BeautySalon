package ua.com.jdev.dbase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ua.com.jdev.model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;


/**
 * Created by Yurik Mikhailichenko on 26.01.2016.
 */
public class DBRepository {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

//    private static final Logger log = Logger.getLogger(DBRepository.class);

    void executeUpdate(String query) {
        try {
            connection = getDBConnection();
            createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        closer(new AutoCloseable[]{statement, connection});
    }

    ObservableList<? extends BaseClass> executeQuery(String query, String tableName) {
        ObservableList<? extends BaseClass> dataList = null;
        try {
            connection = getDBConnection();
            createStatement();
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dataList = getObservableList(resultSet, tableName);
        closer(new AutoCloseable[]{resultSet, statement, connection});
        return dataList;
    }

    public String getLastId(String tableName) {
        String lastId = "";
        try {
            connection = getDBConnection();
            createStatement();
            resultSet = statement.executeQuery("SELECT MAX(id) FROM " + tableName);
            while (resultSet.next()) {
                lastId = resultSet.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastId;
    }

    private static void createDatabaseSchema(String databaseName) {
        try {
            connection = getDBConnection();
            statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE " + databaseName + " CHARACTER SET utf8 COLLATE utf8_general_ci;");
            statement.executeUpdate("USE " + databaseName);
            createTables();
        } catch (Exception e) {
            e.printStackTrace();
    //        log.log(Level.WARN, databaseName, e);
        } finally {
            closer(new AutoCloseable[]{statement, connection});
        }
    }

    private static void createTables() throws SQLException {
        createTable(Constants.TABLE_CLIENTS, Constants.TABLE_CLIENTS_COLUMNS, Constants.TABLE_CLIENTS_PRIMARY_KEY);
        createTable(Constants.TABLE_EMPLOYEES, Constants.TABLE_EMPLOYEES_COLUMNS, Constants.TABLE_EMPLOYEES_PRIMARY_KEY);
        createTable(Constants.TABLE_GOODS, Constants.TABLE_GOODS_COLUMNS, Constants.TABLE_GOODS_PRIMARY_KEY);
        createTable(Constants.TABLE_ORDERS, Constants.TABLE_ORDERS_COLUMNS, Constants.TABLE_ORDERS_PRIMARY_KEY);
        createTable(Constants.TABLE_RENTERS, Constants.TABLE_RENTER_COLUMNS, Constants.TABLE_RENTERS_PRIMARY_KEY);
        createTable(Constants.TABLE_SALES, Constants.TABLE_SALES_COLUMNS, Constants.TABLE_SALES_PRIMARY_KEY);
    }

    private static Connection getDBConnection() throws Exception {
        Class.forName(Constants.DRIVER);
        java.util.Properties properties = new java.util.Properties();
        properties.put("user", Constants.USER);
        properties.put("password", Constants.PASSWORD);
        properties.setProperty("useUnicode", "true");
        properties.setProperty("characterEncoding", "UTF-8");
        return (DriverManager.getConnection(Constants.URL,
                properties));
    }

    private static void createStatement() throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate("USE " + Constants.DATABASE_NAME);
    }

    private static void createTable (String tableName, String[] columns, String primaryKey) throws SQLException {
        StringBuilder query = new StringBuilder("CREATE TABLE `" + tableName + "` (");
        for (String s : columns) {
            query.append(s).append(", ");
        }
        query.append("PRIMARY KEY (`").append(primaryKey).append("`)) ");
        query.append(Constants.CHARACTER_SET + ";");
        statement.executeUpdate(query.toString());
    }

    private static ObservableList<? extends BaseClass> getObservableList(ResultSet set, String tableName) {
        try {
            switch (tableName.toLowerCase()) {
                case "clients":
                    ObservableList<Client> clientData = FXCollections.observableArrayList();
                    return fillClients(set, clientData);
                case "employees":
                    ObservableList<Employee> employeeData = FXCollections.observableArrayList();
                    return fillEmployees(set, employeeData);
                case "goods":
                    ObservableList<Goods> goodsData = FXCollections.observableArrayList();
                    return fillGoods(set, goodsData);
                case "orders":
                    ObservableList<Order> orderData = FXCollections.observableArrayList();
                    return fillOrders(set, orderData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
      //      log.log(Level.WARN, tableName, e);
        }
        throw new IllegalArgumentException();
    }

    private static void closer(AutoCloseable[] closeables) {
        for (AutoCloseable auc : closeables) {
            try {
                auc.close();
            } catch (Exception e) {
                e.printStackTrace();
        //        log.log(Level.WARN, auc.toString(), e);
            }
        }
    }

    private static ResultSet searchPerson(String search) throws SQLException {
        StringBuilder query = new StringBuilder("SELECT CONCAT (lastName, ' ', firstName, ' ', secondName) AS fullName " +
                "FROM clients WHERE CONCAT (lastName, ' ', firstName, ' ', secondName) LIKE '%");
        query.append(search).append("%';");
        return statement.executeQuery(query.toString());
    }

    private static ObservableList<Order> fillOrders(ResultSet set, ObservableList<Order> scheduleRecordData) throws SQLException {
        while (set.next()) {
            Order order = new Order(set.getString(1), getEmployeeById(set.getString(2)), getClientById(set.getString(3)));
            order.setId(set.getString(1));
            scheduleRecordData.add(order);
        }
        return scheduleRecordData;
    }

    private static ObservableList<Goods> fillGoods(ResultSet set, ObservableList<Goods> goodsData) throws SQLException {
        while (set.next()) {
            Goods goods = new Goods(set.getString(2), set.getString(3), set.getDouble(4), set.getInt(5));
            goods.setId(set.getString(1));
            goodsData.add(goods);
        }
        return goodsData;
    }

    private static ObservableList<Employee> fillEmployees(ResultSet set, ObservableList<Employee> employeeData) throws SQLException {
        while (set.next()) {
            Employee employee = new Employee(set.getString(2), set.getString(3), set.getString(4), set.getString(5),
                    set.getString(6));
            employee.setId(set.getString(1));
            employeeData.add(employee);
        }
        return employeeData;
    }

    private static ObservableList<Client> fillClients(ResultSet set, ObservableList<Client> clientData) throws SQLException {
        while (set.next()) {
            Client client = new Client(set.getString(2), set.getString(3), set.getString(4), set.getString(5), set.getString(6));
            client.setId(set.getString(1));
            clientData.add(client);
        }
        return clientData;
    }

    private static Employee getEmployeeById(String string) throws SQLException {
        String query = "SELECT firstName, secondName, lastNamed, phone, position FROM " + Constants.TABLE_EMPLOYEES +
                " WHERE id = " + string + ";";
        try {
            connection = getDBConnection();
            createStatement();
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Employee employee = new Employee(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                resultSet.getString(4), resultSet.getString(5));
        closer(new AutoCloseable[]{connection, statement, resultSet});
        return employee;
    }

    private static Client getClientById(String string) throws SQLException {
        String query = "SELECT firstName, secondName, lastName, phone, cardNumber FROM " + Constants.TABLE_CLIENTS +
                " WHERE id = " + string + ";";
        try {
            connection = getDBConnection();
            createStatement();
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Client client = new Client(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                resultSet.getString(4), resultSet.getString(5));
        closer(new AutoCloseable[]{connection, statement, resultSet});
        return client;
    }

    private static Goods getGoodsById(String string) throws SQLException {
        String query = "SELECT code, name, price, amount FROM " + Constants.TABLE_GOODS +
                " WHERE id = " + string + ";";
        try {
            connection = getDBConnection();
            createStatement();
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*Goods goods = new Goods(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                resultSet.getString(4), resultSet.getString(5))
        */closer(new AutoCloseable[]{connection, statement, resultSet});
        return new Goods();
    }

    private static Order getOrderById(String string) throws SQLException {
        String query = "SELECT firstName, secondName, lastName, phone, cardNumber FROM " + Constants.TABLE_CLIENTS +
                " WHERE id = " + string + ";";
        try {
            connection = getDBConnection();
            createStatement();
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        closer(new AutoCloseable[]{connection, statement, resultSet});
        return new Order();
    }


    public static void main(String[] args) throws Exception {
        /*connection = getDBConnection();
        statement = connection.createStatement();
        statement.executeUpdate("USE iris_db");
        String search = "kone";
        for (int i = 1; i <= 4; i++) {
            ResultSet set = searchPerson(search.substring(0, i));
            System.out.println("Search: " + search.substring(0, i));
            while (set.next()) {
                System.out.println(set.getString(1));
            }
            System.out.println();
        }*/
        createDatabaseSchema(Constants.DATABASE_NAME);
    }
}
