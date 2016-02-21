package ua.com.jdev.dbase;

import javafx.collections.ObservableList;

import ua.com.jdev.model.BaseClass;
import ua.com.jdev.model.Client;
import ua.com.jdev.model.Goods;
import ua.com.jdev.model.Employee;
import ua.com.jdev.model.Order;

import java.sql.ResultSet;

/**
 * Created by Yurii Mikhailichenko on 17.01.2016.
 *
 * @since 0.1.1
 */

public class DBHelper {

    public static DBRepository dbRep = new DBRepository();
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

    public static void sale(Goods goods, int amount, double price) {
        String[] sale = goods.getSaleQuery(amount, price);
        for (String s : sale) {
            dbRep.executeUpdate(s);
        }
    }

    public static ObservableList<? extends BaseClass> search(String search) {
        String query = "SELECT * FROM goods WHERE isActive = true AND name LIKE '%" + search + "%';";
        outcomingData = dbRep.executeQuery(query, Constants.TABLE_GOODS);
        return outcomingData;
    }

    public static void main(String[] args) {
        insert(new Goods("53", "Шузы", 354.4, 71));
        insert(new Goods("45", "Кеды", 361.34, 65));
        insert(new Goods("12", "Водолазка", 150.4, 32));
        insert(new Goods("08", "Мастерка", 221.8, 12));
        insert(new Goods("99", "Шапка", 98.0, 112));
        insert(new Goods("44", "Куртяк", 1311.94, 21));
        search("ка");
    }
}