package ua.com.jdev.entity;

import java.util.ArrayList;

/**
 * Created by Ларочка on 22.01.2016.
 */
public class WindowEntity {
    private ArrayList<String> labels;
    private ArrayList<String> values;
    private String keyWord;
    private String tableName;

    public WindowEntity(ArrayList<String> labels, ArrayList<String> values, String keyWord, String tableName) {
        this.labels = labels;
        this.values = values;
        this.keyWord = keyWord;
        this.tableName = tableName;
    }

    public ArrayList<String> getLabels() {
        return labels;
    }
    public ArrayList<String> getValues() {
        return values;
    }
    public String getKeyWord() {
        return keyWord;
    }
    public String getTableName() {
        return tableName;
    }
}
