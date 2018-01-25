package com.tigerbus.sqlite;

import android.database.Cursor;

import com.tigerbus.TigerApplication;

public interface BriteApi {
    String SELECT_FROM = "SELECT * FROM ";
    String INNER_JOIN = " INNER JOIN ";
    String DOT = ".";
    int BOOLEAN_FALSE = 0;
    int BOOLEAN_TRUE = 1;

    static String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }

    static boolean getBoolean(Cursor cursor, String columnName) {
        return getInt(cursor, columnName) == BOOLEAN_TRUE;
    }

    static long getLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
    }

    static int getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }

    static int putBoolean(boolean b) {
        return b ? BOOLEAN_TRUE : BOOLEAN_FALSE;
    }

     static <T> String object2String(T t) {
        return TigerApplication.object2String(t);
    }

     static <T> T string2Object(Class<T> clazz, String s) {
        return TigerApplication.string2Object(clazz,s);
    }
}
