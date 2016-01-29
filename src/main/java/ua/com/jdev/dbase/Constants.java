package ua.com.jdev.dbase;

/**
 * Created by Ларочка on 24.01.2016.
 */
public class Constants {
    static final String DATABASE_NAME = "iris_db";

    static final String TABLE_EMPLOYEES = "employees";
    static final String[] TABLE_EMPLOYEES_COLUMNS = {
            "`id` INT NOT NULL AUTO_INCREMENT",
            "`firstName` VARCHAR(45) NOT NULL",
            "`secondName` VARCHAR(45)",
            "`lastName` VARCHAR(45) NOT NULL",
            "`phone` VARCHAR(10)",
            "`profession` VARCHAR(45) NOT NULL",
            "`isActive` BOOL"
    };
    static final String TABLE_EMPLOYEES_PRIMARY_KEY = "id";

    static final String TABLE_ORDERS = "orders";
    static final String[] TABLE_ORDERS_COLUMNS = {
            "`id` INT NOT NULL AUTO_INCREMENT",
            "`client_id` INT NOT NULL",
            "`employee_id` INT NOT NULL",
            "`isPaid` BOOL",
            "`date` TIMESTAMP",
            "`price` DECIMAL(9,2)",
            "`isActive` BOOL"
    };
    static final String TABLE_ORDERS_PRIMARY_KEY = "id";

    static final String TABLE_GOODS = "goods";
    static final String[] TABLE_PRODUCTS_COLUMNS = {
            "`id` INT NOT NULL AUTO_INCREMENT",
            "`code` VARCHAR(10)",
            "`name` VARCHAR(45) NOT NULL",
            "`price` DECIMAL(9,2)",
            "`amount` INT NOT NULL"
    };
    static final String TABLE_GOODS_PRIMARY_KEY = "id";

    static final String TABLE_RENTERS = "renters";
    static final String[] TABLE_RENTER_COLUMNS = {
            "`id` INT NOT NULL AUTO_INCREMENT",
            "`firstName` VARCHAR(45) NOT NULL",
            "`secondName` VARCHAR(45)",
            "`lastName` VARCHAR(45) NOT NULL",
            "`phone` VARCHAR(10)",
            "`rent` DECIMAL(9,2)",
            "`isActive` BOOL",
    };
    static final String TABLE_RENTERS_PRIMARY_KEY = "id";

    static final String TABLE_CLIENTS = "clients";
    static final String[] TABLE_CLIENTS_COLUMNS = {
            "`id` INT NOT NULL AUTO_INCREMENT",
            "`firstName` VARCHAR(45) NOT NULL",
            "`secondName` VARCHAR(45)",
            "`lastName` VARCHAR(45) NOT NULL",
            "`phone` VARCHAR(13)",
            "`cardNumber` VARCHAR(7)",
            "`isActive` BOOL"
    };
    static final String TABLE_CLIENTS_PRIMARY_KEY = "id";

}
