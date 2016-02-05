package ua.com.jdev.dbase;

/**
 * Created by Ларочка on 24.01.2016.
 */
public class Constants {
    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String URL = "jdbc:mysql://localhost:3306/";
    static final String USER = "iris";
    static final String PASSWORD = "1234qaz";

    static final String DATABASE_NAME = "iris_db";

    public static final String TABLE_EMPLOYEES = "employees";
    static final String[] TABLE_EMPLOYEES_COLUMNS = {
            "`id` INT NOT NULL AUTO_INCREMENT",
            "`firstName` VARCHAR(45) NOT NULL",
            "`secondName` VARCHAR(45)",
            "`lastName` VARCHAR(45) NOT NULL",
            "`phone` VARCHAR(10)",
            "`profession` VARCHAR(45) NOT NULL",
            "`isActive` BOOL NOT NULL DEFAULT '1'"
    };
    static final String TABLE_EMPLOYEES_PRIMARY_KEY = "id";

    public static final String TABLE_ORDERS = "orders";
    static final String[] TABLE_ORDERS_COLUMNS = {
            "`id` INT NOT NULL AUTO_INCREMENT",
            "`client_id` INT NOT NULL",
            "`employee_id` INT NOT NULL",
            "`isPaid` BOOL",
            "`date` VARCHAR(10)", //TODO: Change to DATE
            "`time` VARCHAR(10)", //TODO: Change to TIME
            "`price` DECIMAL(9,2)",
            "`isActive` BOOL NOT NULL DEFAULT '1'"
    };
    static final String TABLE_ORDERS_PRIMARY_KEY = "id";

    public static final String TABLE_GOODS = "goods";
    static final String[] TABLE_GOODS_COLUMNS = {
            "`id` INT NOT NULL AUTO_INCREMENT",
            "`code` VARCHAR(10)",
            "`name` VARCHAR(45) NOT NULL",
            "`price` DECIMAL(9,2)",
            "`amount` INT NOT NULL",
            "`isActive` BOOL NOT NULL DEFAULT '1'"
    };
    static final String TABLE_GOODS_PRIMARY_KEY = "id";

    public static final String TABLE_RENTERS = "renters";
    static final String[] TABLE_RENTER_COLUMNS = {
            "`id` INT NOT NULL AUTO_INCREMENT",
            "`firstName` VARCHAR(45) NOT NULL",
            "`secondName` VARCHAR(45)",
            "`lastName` VARCHAR(45) NOT NULL",
            "`phone` VARCHAR(10)",
            "`rent` DECIMAL(9,2)",
            "`isActive` BOOL NOT NULL DEFAULT '1'"
    };
    static final String TABLE_RENTERS_PRIMARY_KEY = "id";

    public static final String TABLE_CLIENTS = "clients";
    static final String[] TABLE_CLIENTS_COLUMNS = {
            "`id` INT NOT NULL AUTO_INCREMENT",
            "`firstName` VARCHAR(45) NOT NULL",
            "`secondName` VARCHAR(45)",
            "`lastName` VARCHAR(45) NOT NULL",
            "`phone` VARCHAR(13)",
            "`cardNumber` VARCHAR(7)",
            "`isActive` BOOL NOT NULL DEFAULT '1'"
    };
    static final String TABLE_CLIENTS_PRIMARY_KEY = "id";

    public static final String TABLE_SALES = "sales";
    static final String[] TABLE_SALES_COLUMNS = {
            "`id` INT NOT NULL AUTO_INCREMENT",
            "`goods_id` INT NOT NULL",
            "`datetime` DATETIME",
            "`price` DECIMAL(9,2)",
            "`count` DECIMAL(9,2)",
    };
    static final String TABLE_SALES_PRIMARY_KEY = "id";

    static final String CHARACTER_SET = "CHARACTER SET 'utf8' COLLATE utf8_general_ci";
}
