package ua.com.jdev.dbase;

/**
 * Created by Ларочка on 24.01.2016.
 */
public class Constants {
    static final String DATABASE_NAME = "iris_db";
    static final String TABLE_EMPLOYEES = "employees";
    static final String[] TABLE_EMPLOYEES_COLUMNS = {"id", "firstName", "secondName", "lastName", "phone", "profession"};
    static final String TABLE_ORDERS = "orders";
    static final String[] TABLE_ORDERS_COLUMNS = {"id", "client_id", "employee_id", "time", "price"};
    static final String TABLE_PRODUCTS = "products";
    static final String[] TABLE_PRODUCTS_COLUMNS = {"id", "code", "name", "price", "count"};
    static final String TABLE_RENTERS = "renters";
    static final String[] TABLE_RENTER_COLUMNS = {"id", "firstName", "secondName", "lastName", "phone", "rent"};
    static final String TABLE_CLIENTS = "clients";
    static final String[] TABLE_CLIENTS_COLUMNS = {"id", "firstName", "secondName", "lastName", "phone", "card_number"};
}
