package com.tigerbus.sqlite;

import com.squareup.sqlbrite3.SqlBrite;
import com.tigerbus.TigerApplication;
import com.tigermvp.log.TlogType;

public final class BriteSQL {

    private static final String TAG = BriteSQL.class.getSimpleName();
    private static SqlBrite sqlBrite;

    public static SqlBrite getInstance() {
        if (sqlBrite == null) {
            synchronized (SqlBrite.class) {
                if (sqlBrite == null) {
                    createInstance();
                }
            }
        }
        return sqlBrite;
    }

    private static void createInstance() {
        sqlBrite = new SqlBrite.Builder().logger(message ->
                TigerApplication.printLog(TlogType.debug, TAG, message)).build();
    }

}
