package ua.com.jdev.dbase;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;
import ua.com.jdev.entity.WindowEntity;
import ua.com.jdev.model.*;

import java.sql.*;
import java.util.ArrayList;

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


    @Deprecated
    public static ObservableList<? extends Base> getData(WindowEntity entity) {
        execute(entity);
        return outcomingData;
    }

    public static void addBase(Base baseObject) {
        if (baseObject instanceof Client) {
            Client client = (Client) baseObject;
            addClient(client);
        } else if (baseObject instanceof Goods) {
            Goods goods = (Goods) baseObject;
            addGoods(goods);
        } else if (baseObject instanceof Employee) {
            Employee employee = (Employee) baseObject;
            addEmployee(employee);
        } else if (baseObject instanceof ScheduleRecord) {
            ScheduleRecord record = (ScheduleRecord) baseObject;
            addScheduleRecord(record);
        }
    }

    public static void editBase(Base baseObject) {
        if (baseObject instanceof Client) {
            Client client = (Client) baseObject;
            updateClient(client);
        } else if (baseObject instanceof Goods) {
            Goods goods = (Goods) baseObject;
            updateGoods(goods);
        } else if (baseObject instanceof Employee) {
            Employee employee = (Employee) baseObject;
            updateEmployee(employee);
        } else if (baseObject instanceof ScheduleRecord) {
            ScheduleRecord record = (ScheduleRecord) baseObject;
            updateScheduleRecord(record);
        }
    }

    private static void addClient(Client client) {
        StringBuilder query = "INSERT INTO client (id, firstName, secondName, lastName, phone, cardNumber) VALUES (" +
                appendRequiredField(client.getId()) + ", " + appendRequiredField(client.getFirstName()) + ", " +
                appendField(client.getSecondName()) + ", " + appendRequiredField(client.getLastName()) + ", " +
                appendField(client.getPhone()) + ", " + appendField(client.getCardNumber()) + ");";
        executeUpdate(query.toString());
    }

    private static void addGoods(Goods goods) {
        StringBuilder query = "INSERT INTO goods (id, code, name, price, amount) VALUES (" +
                appendRequiredField(goods.getId()) + ", " + appendRequiredField(goods.getCode()) + ", " +
                appendRequiredField(goods.getName()) + ", " + appendField(goods.getPrice()) + ", " +
                appendField(goods.getAmount());
        executeUpdate(query.toString());
    }

    private static void addEmployee(Employee employee) {
        StringBuilder query = new StringBuilder("INSERT INTO employees (id, firstName, secondName, lastName, phone, profession) VALUES (" +
                appendRequiredField(employee.getId()) + ", " + appendRequiredField(employee.getFirstName()) + ", " +
                appendField(employee.getSecondName()) + ", " + appendRequiredField(employee.getLastName()) + ", " +
                appendField(employee.getPhone()) + appendRequiredField(employee.getPosition()) + ");");
        executeUpdate(query.toString());
    }

    private static void addScheduleRecord(ScheduleRecord record) {
        //not implemented
    }

    private static void updateClient(Client client) {
        StringBuilder query = new StringBuilder("UPDATE clients SET firstName = " + appendRequiredField(client.getFirstName()) +
                ", secondName = " + appendField(client.getSecondName()) + ", lastName =  " +
                appendRequiredField(client.getLastName()) + ", phone = " + appendField(client.getPhone()) +
                ", cardNumber = " + appendRequiredField(client.getCardNumber()) + " WHERE id = " + client.getId() + ";");
    }

    private static void updateGoods(Goods goods) {
        StringBuilder query = new StringBuilder("UPDATE goods SET code = " + appendRequiredField(goods.getCode()) +
                ", name = " + appendField(goods.getName()) + ", price =  " + appendField(goods.getPrice()) +
                ", amount = " + appendField(goods.getAmount()) + ";");
    }

    private static void updateEmployee(Employee employee) {
        StringBuilder query = new StringBuilder("UPDATE employees SET firstName = " + appendRequiredField(employee.getFirstName()) +
                ", secondName = " + appendField(employee.getSecondName()) + ", lastName =  " +
                appendRequiredField(employee.getLastName()) + ", phone = " + appendField(employee.getPhone()) +
                ", profession = " + appendRequiredField(employee.getPosition()) + " WHERE id = " + client.getId() + ";");
    }

    private static void updateScheduleRecord(ScheduleRecord record) {
        //not implemented
    }


    private static String appendField(String param) {
        return !param.equals("") ? "'" + param + "'" : "NULL";
    }

    private static String appendRequiredField(String param) throws IllegalArgumentException {
        try {
            if (param.equals("")) throw new IllegalArgumentException("Illegal value on field!");
        } catch (NullPointerException npe) {
            throw new IllegalArgumentException("Illegal value on field!");
        }
        return "'" + param + "'";
    }

    public static ObservableList<? extends Base> selectBase() {
        return null;
    }

    public static void deleteBase(Base baseObject) {

    }

    private static void execute(Base baseObject) {

    }

    @Deprecated
    public static void execute(WindowEntity entity) throws IllegalArgumentException {
        /**
         * @throws IllegalArgumentException
         * @param = entity.getKeyWord()
         */
        switch (entity.getKeyWord().toUpperCase()) {
            case "INSERT":
                executeUpdate(createInsertQuery(entity));
                break;
            case "UPDATE":
                executeUpdate(createUpdateQuery(entity));
                break;
            case "SELECT":
                outcomingData = executeQuery(createSelectQuery(entity), entity.getTableName());
                break;
            case "DELETE":
                //executeQuery(createDeleteQuery(entity));
                break;
            default:
                throw new IllegalArgumentException("No find keyWord! (INSERT, UPDATE, SELECT, DELETE)");
        }
    }

    private static void executeUpdate(String query) {
        try {
            try {
                // opening database connection to MySQL server
                connection = DriverManager.getConnection(URL + "/" + DATABASE_NAME, USER, PASSWORD);

            } catch (MySQLSyntaxErrorException ex) {
                System.out.println("!!");
                //if databases not found - create new database
                //TODO: add log
                createDatabase(DATABASE_NAME);
                connection = DriverManager.getConnection(URL + "/" + DATABASE_NAME, USER, PASSWORD);
            }
            // getting Statement object to execute query
            statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            //TODO: add log
        } finally {
            try { connection.close(); } catch(SQLException se) { /*TODO: add log*/ }
            try { statement.close(); } catch(SQLException se) { /*TODO: add log*/ }
        }
    }

    private static ObservableList<? extends Base> executeQuery(String query, String tableName) {
        ObservableList<? extends Base> dataList = null;
        try {
            try {
                // opening database connection to MySQL server
                connection = getDBConnection();
            } catch (MySQLSyntaxErrorException ex) {
                //if databases not found - create new database
                //TODO: add log
                createDatabase(DATABASE_NAME);
                connection = DriverManager.getConnection(URL + "/" + DATABASE_NAME, USER, PASSWORD);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // getting Statement object to execute query
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            dataList = getObservableList(resultSet, tableName);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            //TODO: add log
        } finally {
            try { resultSet.close(); } catch (SQLException se) { /*TODO: add log*/ }
            try { connection.close(); } catch(SQLException se) { /*TODO: add log*/ }
            try { statement.close(); } catch(SQLException se) { /*TODO: add log*/ }
        }
        return dataList;
    }

    @NotNull
    @Deprecated
    private static String createUpdateQuery(WindowEntity entity) {
        /**
         * Генератор запроса UPDATE
         *
         * @param entity
         * @return UPDATE tableName SET labels.get(0) = values.get(0), /.../ labels.get(n) = values.get(n);
         */
        ArrayList<String> labels = entity.getLabels();
        ArrayList<String> values = entity.getValues();
        StringBuilder queryBuilder = new StringBuilder(entity.getKeyWord() + " " + entity.getTableName() + " SET ");
        for (int i = 0; i < labels.size(); i++) {
            queryBuilder.append(labels.get(i) + " = '" + values.get(i) + "'" +
                    (i != labels.size() - 1 ? ", " : ";"));
        }
        return queryBuilder.toString();
    }

    @NotNull
    @Deprecated
    private static String createInsertQuery(WindowEntity entity) {
        /**
         * Генератор запроса INSERT.
         *
         * @param entity
         * @return INSERT INTO tableName (labels.get(0), /.../ labels.get(n)) VALUES ('values.get(0)', /.../ 'values.get(n));
         */
        ArrayList<String> labels = entity.getLabels();
        ArrayList<String> values = entity.getValues();
        StringBuilder queryBuilder = new StringBuilder(entity.getKeyWord() + " INTO " + entity.getTableName() + " (");
        for (int i = 0; i < labels.size(); i++) {
            queryBuilder.append(labels.get(i) + (i != labels.size() - 1 ? ", " : ") VALUES ("));
        }
        for (int i = 0; i < labels.size(); i++) {
            queryBuilder.append("'" + values.get(i) + "'" + (i != labels.size() - 1  ? ", " : ");" ));
        }
        return queryBuilder.toString();
    }

    @NotNull
    @Deprecated
    private static String createSelectQuery(WindowEntity entity) {
        /**
         * Генератор запроса SELECT
         *
         * @param entity
         * @return SELECT labels.get(0), /.../ labels.get(n) FROM tableName WHERE values.get(0) /.../ values.get(n);
         */
        ArrayList<String> labels = entity.getLabels();
        ArrayList<String> values = entity.getValues();
        StringBuilder queryBuilder = new StringBuilder(entity.getKeyWord() + " * FROM " + entity.getTableName());
/*
        for (int i = 0; i < labels.size(); i++) {
            queryBuilder.append(" " + labels.get(i) + (i != (labels.size() - 1) ? "," : " "));
        }

        queryBuilder.append("FROM " + entity.getTableName());
        if (values.size() > 0) {
            queryBuilder.append(" WHERE " + values.get(0));
        }
*/
        return queryBuilder.toString();
    }

    @NotNull
    private static String createDeleteQuery(WindowEntity entity) {
        /**
         * Генератор запроса DELETE
         *
         * @param entity
         * @return UPDATE...
         */
        //return createUpdateQuery(entity);
        return "";
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
            //TODO: add log
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            //TODO: add log
        } finally {
            try {connection.close();} catch (SQLException e) {/*TODO: add log*/}
            try {statement.close();} catch (SQLException e) {/*TODO: add log*/}
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
        ObservableList<? extends Base> currentData = null;
        ObservableList<ScheduleRecord> scheduleRecordData = FXCollections.observableArrayList();
        ObservableList<Goods> goodsData = FXCollections.observableArrayList();
        ObservableList<Employee> employeeData = FXCollections.observableArrayList();
        ObservableList<Client> clientData = FXCollections.observableArrayList();
        try {
            switch (tableName.toLowerCase()) {
                case "clients":
                    currentData = fillClients(set, clientData);
                    break;
                case "employees":
                    currentData = fillEmployees(set, employeeData);
                    break;
                case "goods":
                    currentData = fillGoods(set, goodsData);
                    break;
                case "orders":
                    currentData = fillRecords(set, scheduleRecordData);
                    break;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currentData;

    }

    private static ObservableList<ScheduleRecord> fillRecords(ResultSet set, ObservableList<ScheduleRecord> scheduleRecordData) throws SQLException {
        while (set.next()) {
            scheduleRecordData.add(new ScheduleRecord(set.getString(1), set.getString(2), set.getString(3)));
        }
        return scheduleRecordData;
    }

    private static ObservableList<Goods> fillGoods(ResultSet set, ObservableList<Goods> goodsData) throws SQLException {
        while (set.next()) {
            goodsData.add(new Goods(set.getString(1), set.getString(2), set.getString(3)));
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
