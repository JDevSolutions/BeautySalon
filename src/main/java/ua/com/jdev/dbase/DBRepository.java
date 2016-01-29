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
 * Created by Jurimik on 26.01.2016.
 */
public class DBRepository {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String USER = "iris";
    private static final String PASSWORD = "1234qaz";

    private static final Logger log = Logger.getLogger(DBRepository.class);

    void executeUpdate(String query) {
        try {
            try {
                // opening database connection to MySQL server
                connection = DriverManager.getConnection(URL + "/" + DBHelper.DATABASE_NAME, USER, PASSWORD);
            } catch (SQLException ex) {
                createDatabase(DBHelper.DATABASE_NAME);
                connection = DriverManager.getConnection(URL + "/" + DBHelper.DATABASE_NAME, USER, PASSWORD);
            }
            // getting Statement object to execute query
            statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException sqlEx) {
            log.log(Level.WARN, query, sqlEx);
        } finally {
            try { connection.close(); } catch(SQLException se) { log.log(Level.WARN, connection.toString(), se); }
            try { statement.close(); } catch(SQLException se) { log.log(Level.WARN, statement.toString(), se); }
        }
    }

    ObservableList<? extends BaseClass> executeQuery(String query, String tableName) {
        ObservableList<? extends BaseClass> dataList = null;
        try {
            try {
                // opening database connection to MySQL server
                connection = DriverManager.getConnection(URL + "/" + DBHelper.DATABASE_NAME, USER, PASSWORD);
            } catch (SQLException ex) {
                createDatabase(DBHelper.DATABASE_NAME);
                connection = DriverManager.getConnection(URL + "/" + DBHelper.DATABASE_NAME, USER, PASSWORD);
            }// getting Statement object to execute query
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            dataList = getObservableList(resultSet, tableName);
        } catch (SQLException sqlEx) {
            log.log(Level.WARN, query + ", tableName: " + tableName, sqlEx);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { resultSet.close(); } catch (SQLException se) { log.log(Level.WARN, resultSet.toString(), se); }
            try { connection.close(); } catch(SQLException se) { log.log(Level.WARN, connection.toString(), se); }
            try { statement.close(); } catch(SQLException se) { log.log(Level.WARN, statement.toString(), se);  }
        }
        return dataList;
    }

    private static void createDatabase(String databaseName) {
        String Driver = "com.mysql.jdbc.Driver";
        try {
            Class.forName(Driver);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            String query = "CREATE DATABASE " + databaseName + " CHARACTER SET utf8 COLLATE utf8_general_ci;";

            statement = connection.createStatement();
            statement.executeUpdate(query);

            connection = DriverManager.getConnection(URL + "/" + databaseName, USER, PASSWORD);
            statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE `" + DBHelper.TABLE_CLIENTS + "`(`id` INT NOT NULL AUTO_INCREMENT, " +
                    "`firstName` VARCHAR(45) NOT NULL, `secondName` VARCHAR(45), `lastName` VARCHAR(45) NOT NULL, " +
                    "`phone` VARCHAR(10), `cardNumber` VARCHAR(7), `isActive` BOOL, PRIMARY KEY (`id`)) " +
                    "CHARACTER SET 'utf8' COLLATE utf8_general_ci;");

            statement.executeUpdate("CREATE TABLE `" + DBHelper.TABLE_EMPLOYEES + "` (`id` INT NOT NULL AUTO_INCREMENT, " +
                    "`firstName` VARCHAR(45) NOT NULL, `secondName` VARCHAR(45), `lastName` VARCHAR(45) NOT NULL, " +
                    "`phone` VARCHAR(10), `profession` VARCHAR(45) NOT NULL, `isActive` BOOL, PRIMARY KEY (`id`)) CHARACTER SET 'utf8' " +
                    "COLLATE utf8_general_ci;");

            statement.executeUpdate("CREATE TABLE `" + DBHelper.TABLE_ORDERS + "` (`id` INT NOT NULL AUTO_INCREMENT, " +
                    "`client_id` INT NOT NULL, `employee_id` INT, `isPaid` BOOL, `price` DECIMAL(9,2), " +
                    "`date` TIMESTAMP, `isActive` BOOL, PRIMARY KEY (`id`)) CHARACTER SET 'utf8' COLLATE utf8_general_ci;");

            statement.executeUpdate("CREATE TABLE `" + DBHelper.TABLE_GOODS + "` (`id` INT NOT NULL AUTO_INCREMENT, " +
                    "`name` VARCHAR(45) NOT NULL, `price` DECIMAL(9,2), `isActive` BOOL, PRIMARY KEY (`id`)) CHARACTER SET 'utf8'" +
                    "COLLATE utf8_general_ci;");

            statement.executeUpdate("CREATE TABLE `" + DBHelper.TABLE_RENTERS + "` (`id` INT NOT NULL AUTO_INCREMENT," +
                    " `firstName` VARCHAR(45) NOT NULL, `secondName` VARCHAR(45), `lastName` VARCHAR(45) NOT NULL, " +
                    "`phone` VARCHAR(10), `rent` DECIMAL(9,2), `isActive` BOOL, PRIMARY KEY (`id`)) CHARACTER SET 'utf8' " +
                    "COLLATE utf8_general_ci;");

        } catch (SQLException ex) {
            log.log(Level.WARN, databaseName, ex);
        } catch (ClassNotFoundException e) {
            log.log(Level.WARN, e);
        } finally {
            try {connection.close();} catch (SQLException e) { log.log(Level.WARN, connection.toString(), e); }
            try {statement.close();} catch (SQLException e) { log.log(Level.WARN, statement.toString(), e); }
        }
    }

    private static Connection getDBConnection()throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        java.util.Properties properties = new java.util.Properties();
        properties.put("user", USER);
        properties.put("password", PASSWORD);

        /* настройки указывающие о необходимости конвертировать данные из Unicode
	    в UTF-8, который используется в нашей таблице для хранения данных */

        properties.setProperty("useUnicode", "true");
        properties.setProperty("characterEncoding", "UTF-8");
        return (DriverManager.getConnection("jdbc:mysql://localhost:3306/iris_db",
                properties));
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
            log.log(Level.WARN, tableName, e);
        }
        throw new IllegalArgumentException();
    }

    private static ObservableList<Order> fillOrders(ResultSet set, ObservableList<Order> scheduleRecordData) throws SQLException {
        while (set.next()) {
            Order order = new Order(set.getString(1), set.getString(2), set.getString(3));
            order.setId(set.getString(1));
            scheduleRecordData.add(order);
        }
        return scheduleRecordData;
    }

    private static ObservableList<Goods> fillGoods(ResultSet set, ObservableList<Goods> goodsData) throws SQLException {
        while (set.next()) {
            Goods goods = new Goods(set.getString(1), set.getString(2), set.getString(3), set.getString(4));
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
}
