package ua.com.jdev.dbase;

import ua.com.jdev.model.BaseClass;

/**
 * Created by sysadmin on 02.02.2016.
 */
public interface ModelRepository {
    String getInsertQuery();
    String getUpdateQuery();
    String getDeleteQuery();
    String getRestoreQuery();
   // String getSaleQuery();
}
