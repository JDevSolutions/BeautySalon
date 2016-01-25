
package ua.com.jdev.dbase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.com.jdev.model.*;

import java.sql.*;


/**
 * Created by Yurii Mikhailichenko on 17.01.2016.
 *
 * @since 0.1.1
 */

public class DBHelper {
    public static final String URL = "jdbc:mysql://localhost:3306";
    public static final String USER = "iris";
    public static final String PASSWORD = "1234qaz";

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    private static ObservableList<? extends Base> outcomingData;

    private static final String DATABASE_NAME = "iris_db";
    private static final String TABLE_EMPLOYEES = "employees";
    private static final String TABLE_ORDERS = "orders";
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_RENTERS = "renters";
    private static final String TABLE_CLIENTS = "clients";
    private static final String TABLE_CARDS = "cards";


    public static void insert(Client client) {
        StringBuilder query = new StringBuilder("INSERT INTO client (id, firstName, secondName, lastName, phone, cardNumber) VALUES (" +
                appendRequiredField(client.getId()) + ", " + appendRequiredField(client.getFirstName()) + ", " +
                appendField(client.getSecondName()) + ", " + appendRequiredField(client.getLastName()) + ", " +
                appendField(client.getPhone()) + ", " + appendField(client.getCardNumber()) + ");");
        executeUpdate(query.toString());
    }

    public static void insert(Goods goods)  {
        StringBuilder query = new StringBuilder("INSERT INTO goods (id, code, name, price, amount) VALUES (" +
                appendRequiredField(goods.getId()) + ", " + appendRequiredField(goods.getCode()) + ", " +
                appendRequiredField(goods.getName()) + ", " + appendField(goods.getPrice()) + ", " +
                appendField(goods.getAmount()));
        executeUpdate(query.toString());
    }

    public static void insert(Employee employee)  {
        StringBuilder query = new StringBuilder("INSERT INTO employees (id, firstName, secondName, lastName, phone, profession) VALUES (" +
                appendRequiredField(employee.getId()) + ", " + appendRequiredField(employee.getFirstName()) + ", " +
                appendField(employee.getSecondName()) + ", " + appendRequiredField(employee.getLastName()) + ", " +
                appendField(employee.getPhone()) + appendRequiredField(employee.getPosition()) + ");");
        executeUpdate(query.toString());
    }

    public static void insert(ScheduleRecord record) {
        //not implemented
    }

    public static void update(Client client) {
        StringBuilder query = new StringBuilder("UPDATE clients SET firstName = " + appendRequiredField(client.getFirstName()) +
                ", secondName = " + appendField(client.getSecondName()) + ", lastName =  " +
                appendRequiredField(client.getLastName()) + ", phone = " + appendField(client.getPhone()) +
                ", cardNumber = " + appendRequiredField(client.getCardNumber()) + " WHERE id = " + client.getId() + ";");
        executeUpdate(query.toString());
    }

    public static void update(Goods goods)  {
        StringBuilder query = new StringBuilder("UPDATE goods SET code = " + appendRequiredField(goods.getCode()) +
                ", name = " + appendField(goods.getName()) + ", price =  " + appendField(goods.getPrice()) +
                ", amount = " + appendField(goods.getAmount()) + ";");
        executeUpdate(query.toString());
    }

    public static void update(Employee employee) {
        StringBuilder query = new StringBuilder("UPDATE employees SET firstName = " + appendRequiredField(employee.getFirstName()) +
                ", secondName = " + appendField(employee.getSecondName()) + ", lastName =  " +
                appendRequiredField(employee.getLastName()) + ", phone = " + appendField(employee.getPhone()) +
                ", profession = " + appendRequiredField(employee.getPosition()) + " WHERE id = " + employee.getId() + ";");
        executeUpdate(query.toString());
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

    private static void executeUpdate(String query) {
        try {
            // opening database connection to MySQL server
            connection = DriverManager.getConnection(URL + "/" + DATABASE_NAME, USER, PASSWORD);
            // getting Statement object to execute query
            statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            //TODO: insert log
        } finally {
            try { connection.close(); } catch(SQLException se) { /*TODO: insert log*/ }
            try { statement.close(); } catch(SQLException se) { /*TODO: insert log*/ }
        }
    }

    private static ObservableList<? extends Base> executeQuery(String query, String tableName) {
        ObservableList<? extends Base> dataList = null;
        try {
            connection = getDBConnection();
            // getting Statement object to execute query
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            dataList = getObservableList(resultSet, tableName);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            //TODO: insert log
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { resultSet.close(); } catch (SQLException se) { /*TODO: insert log*/ }
            try { connection.close(); } catch(SQLException se) { /*TODO: insert log*/ }
            try { statement.close(); } catch(SQLException se) { /*TODO: insert log*/  }
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

            statement.executeUpdate("CREATE TABLE `" + TABLE_CARDS + "` (`id` INT NOT NULL, " +
                    "`client_id` INT NOT NULL, PRIMARY KEY (`id`)) CHARACTER SET 'utf8' COLLATE utf8_general_ci;");

            statement.executeUpdate("CREATE TABLE `" + TABLE_CLIENTS + "`(`id` INT NOT NULL, " +
                    "`firstName` VARCHAR(45) NOT NULL, `secondName` VARCHAR(45), `lastName` VARCHAR(45) NOT NULL, " +
                    "`phone` VARCHAR(10), PRIMARY KEY (`id`)) CHARACTER SET 'utf8' COLLATE utf8_general_ci;");

            statement.executeUpdate("CREATE TABLE `" + TABLE_EMPLOYEES + "` (`id` INT NOT NULL, " +
                    "`firstName` VARCHAR(45) NOT NULL, `secondName` VARCHAR(45), `lastName` VARCHAR(45) NOT NULL, " +
                    "`phone` VARCHAR(10), `profession` VARCHAR(45) NOT NULL, PRIMARY KEY (`id`)) CHARACTER SET 'utf8' " +
                    "COLLATE utf8_general_ci;");

            statement.executeUpdate("CREATE TABLE `" + TABLE_ORDERS + "` (`id` INT NOT NULL, " +
                    "`client_id` INT NOT NULL, `employee_id` INT, `isPaid` BOOL, `price` DECIMAL(9,2), " +
                    "`date` TIMESTAMP, PRIMARY KEY (`id`)) CHARACTER SET 'utf8' COLLATE utf8_general_ci;");

            statement.executeUpdate("CREATE TABLE `" + TABLE_PRODUCTS + "` (`id` INT NOT NULL, " +
                    "`name` VARCHAR(45) NOT NULL, `price` DECIMAL(9,2), PRIMARY KEY (`id`)) CHARACTER SET 'utf8'" +
                    "COLLATE utf8_general_ci;");

            statement.executeUpdate("CREATE TABLE `" + TABLE_RENTERS + "` (`id` INT NOT NULL," +
                    " `firstName` VARCHAR(45) NOT NULL, `secondName` VARCHAR(45), `lastName` VARCHAR(45) NOT NULL, " +
                    "`phone` VARCHAR(10), `rent` DECIMAL(9,2), PRIMARY KEY (`id`)) CHARACTER SET 'utf8' " +
                    "COLLATE utf8_general_ci;");

        } catch (SQLException ex) {
            ex.printStackTrace();
            //TODO: insert log
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            //TODO: insert log
        } finally {
            try {connection.close();} catch (SQLException e) {
/*TODO: insert log*/
}
            try {statement.close();} catch (SQLException e) {
/*TODO: insert log*/
}
        }
    }

    protected static Connection getDBConnection()throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        java.util.Properties properties = new java.util.Properties();
        properties.put("user", USER);
        properties.put("password", PASSWORD);

/*
          настройки указывающие о необходимости конвертировать данные из Unicode
	  в UTF-8, который используется в нашей таблице для хранения данных
        */

        properties.setProperty("useUnicode", "true");
        properties.setProperty("characterEncoding", "UTF-8");
        return (DriverManager.getConnection("jdbc:mysql://localhost:3306/iris_db",
                properties));
    }


    private static ObservableList<? extends Base> getObservableList(ResultSet set, String tableName) {
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
                    ObservableList<ScheduleRecord> scheduleRecordData = FXCollections.observableArrayList();
                    return fillRecords(set, scheduleRecordData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    private static ObservableList<ScheduleRecord> fillRecords(ResultSet set, ObservableList<ScheduleRecord> scheduleRecordData) throws SQLException {
        while (set.next()) {
            scheduleRecordData.add(new ScheduleRecord(set.getString(1), set.getString(2), set.getString(3), set.getString(4), set.getBoolean(5)));
        }
        return scheduleRecordData;
    }

    private static ObservableList<Goods> fillGoods(ResultSet set, ObservableList<Goods> goodsData) throws SQLException {
        while (set.next()) {
            goodsData.add(new Goods(set.getString(1), set.getString(2), set.getString(3), set.getString(4)));
        }
        return goodsData;
    }

    private static ObservableList<Employee> fillEmployees(ResultSet set, ObservableList<Employee> employeeData) throws SQLException {
        while (set.next()) {
            employeeData.add(new Employee(set.getString(2), set.getString(3), set.getString(4), set.getString(5),
                    set.getString(6)));
        }
        return employeeData;
    }

    private static ObservableList<Client> fillClients(ResultSet set, ObservableList<Client> clientData) throws SQLException {
        while (set.next()) {
            clientData.add(new Client(set.getString(2), set.getString(3), set.getString(4), set.getString(5)));
        }
        return clientData;
    }

}

