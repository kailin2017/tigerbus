package com.tigerbus.sqlite.table;

import android.arch.persistence.db.SupportSQLiteDatabase;

import com.tigerbus.TigerApplication;
import com.tigermvp.log.TlogType;

public abstract class CreateTableObj {

    protected final static String COMMA = ",";
    protected final static String CREATE_TABLE = "CREATE TABLE ";
    protected final static String PRIMARY_KEY = " PRIMARY KEY ";
    protected final static String AUTOINCREMENT = " AUTOINCREMENT ";
    protected final static String TEXT_NOT_NULL = " TEXT NOT NULL ";
    protected final static String INTEGER_NOT_NULL = " INTEGER NOT NULL ";
    protected final static String INTEGER_DEFAULT_0 = " INTEGER DEFAULT 0 ";
    protected final static String BIGINT_NOT_NULL = " BIGINT NOT NULL ";
    protected final static String BIGINT_DEFAULT_0 = " BIGINT DEFAULT 0 ";

    public String getCreateTableString() {
        String createString = String.format("CREATE TABLE %s ( %s )", getTableString(), getColumnString());
        TigerApplication.printLog(TlogType.debug, CreateTableObj.class.getSimpleName(), createString);
        return createString;
    }

    public abstract void initDefaultData(SupportSQLiteDatabase database);

    protected abstract String getTableString();

    protected abstract String getColumnString();

}
