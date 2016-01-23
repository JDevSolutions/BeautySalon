package ua.com.jdev.dbase;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import org.jetbrains.annotations.NotNull;
import ua.com.jdev.entity.DBEntity;
import ua.com.jdev.entity.WindowEntity;

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
    private static ArrayList<DBEntity> dbEntities;

    private static final String DATABASE_NAME = "iris_db";
    private static final String TABLE_EMPLOYEES = "employees";
    private static final String TABLE_ORDERS = "orders";
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_RENTERS = "renters";
    private static final String TABLE_CLIENTS = "clients";
    private static final String TABLE_CARDS = "cards";

    public static ResultSet getResultSet() {
        return resultSet;
    }

    /**
     *@throws IllegalArgumentException
     *
     */
    public static void execute(WindowEntity entity) throws IllegalArgumentException {
        switch (entity.getKeyWord().toUpperCase()) {
            case "INSERT":
                executeUpdate(createInsertQuery(entity));
                break;
            case "UPDATE":
                executeUpdate(createUpdateQuery(entity));
                break;
            case "SELECT":
                dbEntities = new ArrayList<>();
                executeQuery(createSelectQuery(entity));
                break;
            case "DELETE":
                //executeQuery(createDeleteQuery(entity));
                break;
            default:
                throw new IllegalArgumentException("No find keyWord! (INSERT, UPDATE, SELECT, DELETE)");
        }
    }

    private static ResultSet executeQuery(String query) {
        try {
            try {
                // opening database connection to MySQL server
                connection = DriverManager.getConnection(URL + "/" + DATABASE_NAME, USER, PASSWORD);
            } catch (MySQLSyntaxErrorException ex) {
                //if databases not found - create new database
                //TODO: add log
                createDatabase(DATABASE_NAME);
                connection = DriverManager.getConnection(URL + "/" + DATABASE_NAME, USER, PASSWORD);
            }
            // getting Statement object to execute query
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String firstName = resultSet.getString(1);
                String lastName = resultSet.getString(2);
                String phone = resultSet.getString(3);
                System.out.printf("firstName: %s, lastName: %s, phone: %s %n", firstName, lastName, phone);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            //TODO: add log
        } finally {
            try { resultSet.close(); } catch (SQLException se) { /*TODO: add log*/ }
            try { connection.close(); } catch(SQLException se) { /*TODO: add log*/ }
            try { statement.close(); } catch(SQLException se) { /*TODO: add log*/ }
        }
        return resultSet;
    }

    private static void executeUpdate(String query) {
        try {
            try {
                // opening database connection to MySQL server
                connection = DriverManager.getConnection(URL + "/" + DATABASE_NAME, USER, PASSWORD);
            } catch (MySQLSyntaxErrorException ex) {
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

    /**
     * Генератор запроса UPDATE
     *
     * @param entity
     * @return UPDATE tableName SET labels.get(0) = values.get(0), /.../ labels.get(n) = values.get(n);
     */
    @NotNull
    private static String createUpdateQuery(WindowEntity entity) {
        ArrayList<String> labels = entity.getLabels();
        ArrayList<String> values = entity.getValues();
        StringBuilder queryBuilder = new StringBuilder(entity.getKeyWord() + " " + entity.getTableName() + " SET ");
        for (int i = 0; i < labels.size(); i++) {
            queryBuilder.append(labels.get(i) + " = '" + values.get(i) + "'" +
                    (i != labels.size() - 1 ? ", " : ";"));
        }
        return queryBuilder.toString();
    }

    /**
     * Генератор запроса INSERT.
     *
     * @param entity
     * @return INSERT INTO tableName (labels.get(0), /.../ labels.get(n)) VALUES ('values.get(0)', /.../ 'values.get(n));
     */
    @NotNull
    private static String createInsertQuery(WindowEntity entity) {
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

    /**
     * Генератор запроса SELECT
     *
     * @param entity
     * @return SELECT labels.get(0), /.../ labels.get(n) FROM tableName WHERE values.get(0) /.../ values.get(n);
     */
    @NotNull
    private static String createSelectQuery(WindowEntity entity) {
        ArrayList<String> labels = entity.getLabels();
        ArrayList<String> values = entity.getValues();
        StringBuilder queryBuilder = new StringBuilder(entity.getKeyWord());
        for (int i = 0; i < labels.size(); i++) {
            queryBuilder.append(" " + labels.get(i) + (i != (labels.size() - 1) ? "," : " "));
        }
        queryBuilder.append("FROM " + entity.getTableName());
        if (values.size() > 0) {
            queryBuilder.append(" WHERE " + values.get(0));
        }
        return queryBuilder.toString();
    }

    /**
     * Генератор запроса DELETE
     *
     * @param entity
     * @return SELECT labels.get(0), /.../ labels.get(n) FROM tableName WHERE values.get(0) /.../ values.get(n);
     */
    @NotNull
    private static String createDeleteQuery(WindowEntity entity) {
        //return createUpdateQuery(entity);
        return "";
    }


    private static void createDatabase(String databaseName) {
        String Driver = "com.mysql.jdbc.Driver";
        try {
            Class.forName(Driver);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            String query = "CREATE DATABASE " + databaseName;

            statement = connection.createStatement();
            statement.executeUpdate(query);

            connection = DriverManager.getConnection(URL + "/" + databaseName, USER, PASSWORD);
            statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE `" + TABLE_CARDS + "` (`id` INT NOT NULL, " +
                    "`client_id` INT NOT NULL, PRIMARY KEY (`id`))");

            statement.executeUpdate("CREATE TABLE `" + TABLE_CLIENTS + "`(`id` INT NOT NULL, " +
                    "`firstName` VARCHAR(45) NOT NULL, `secondName` VARCHAR(45), `lastName` VARCHAR(45) NOT NULL, " +
                    "`phone` VARCHAR(10), PRIMARY KEY (`id`))");

            statement.executeUpdate("CREATE TABLE `" + TABLE_EMPLOYEES + "` (`id` INT NOT NULL, " +
                    "`firstName` VARCHAR(45) NOT NULL, `secondName` VARCHAR(45), `lastName` VARCHAR(45) NOT NULL, " +
                    "`phone` VARCHAR(10), `profession` VARCHAR(45) NOT NULL, PRIMARY KEY (`id`))");

            statement.executeUpdate("CREATE TABLE `" + TABLE_ORDERS + "` (`id` INT NOT NULL, " +
                    "`client_id` INT NOT NULL, `employee_id` INT, `isPaid` BOOL, `price` DECIMAL(9,2), " +
                    "`date` TIMESTAMP, PRIMARY KEY (`id`))");

            statement.executeUpdate("CREATE TABLE `" + TABLE_PRODUCTS + "` (`id` INT NOT NULL, " +
                    "`name` VARCHAR(45) NOT NULL, `price` DECIMAL(9,2), PRIMARY KEY (`id`))");

            statement.executeUpdate("CREATE TABLE `" + TABLE_RENTERS + "` (`id` INT NOT NULL," +
                    " `firstName` VARCHAR(45) NOT NULL, `secondName` VARCHAR(45), `lastName` VARCHAR(45) NOT NULL, " +
                    "`phone` VARCHAR(10), `rent` DECIMAL(9,2), PRIMARY KEY (`id`))");

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
}
