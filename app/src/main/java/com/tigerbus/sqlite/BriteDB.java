package com.tigerbus.sqlite;

import com.squareup.sqlbrite3.SqlBrite;
import com.tigerbus.TigerApplication;
import com.tigerbus.base.log.TlogType;

public final class BriteDB {

    private static final String TAG = BriteDB.class.getSimpleName();
    private static SqlBrite sqlBrite;

    public synchronized static SqlBrite getInstance() {
        synchronized (SqlBrite.class) {
            if (sqlBrite == null) {
                createInstance();
            }
            return sqlBrite;
        }
    }

    private static void createInstance() {
        sqlBrite = new SqlBrite.Builder().logger(message ->
                TigerApplication.printLog(TlogType.error, TAG, message)).build();
    }

}
