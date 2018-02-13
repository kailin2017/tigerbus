package com.tigerbus.sqlite.table;

abstract class CreateTableString {

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
        return String.format("CREATE TABLE %s ( %s )", getTableString(), getColumnString());
    }

    protected abstract String getTableString();

    protected abstract String getColumnString();
}
