package com.tigerbus.sqlite;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;

import com.tigerbus.sqlite.data.CommonStop;
import com.tigerbus.sqlite.data.CommonStopType;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_FAIL;

final class BriteDBCallback extends SupportSQLiteOpenHelper.Callback {
    private static final int VERSION = 1;

    private static final String CREATE_COMMODSTOP_TABLE = ""
            + "CREATE TABLE " + CommonStop.TABLE + "("
            + CommonStop.ID + " TEXT NOT NULL PRIMARY KEY,"
            + CommonStop.STOP + " TEXT NOT NULL,"
            + CommonStop.ROUTE + " TEXT NOT NULL,"
            + CommonStop.ROUTESUB + " TEXT NOT NULL,"
            + CommonStop.TYPE + " INTEGER NOT NULL"
            + ")";
    private static final String CREATE_COMMOD_STOP_TYPE_TABLE = ""
            + "CREATE TABLE " + CommonStopType.TABLE + "("
            + CommonStopType.ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + CommonStopType.TYPENAME + " TEXT NOT NULL"
            + ")";

    BriteDBCallback() {
        super(VERSION);
    }

    @Override
    public void onCreate(SupportSQLiteDatabase db) {
        db.execSQL(CREATE_COMMODSTOP_TABLE);
        db.execSQL(CREATE_COMMOD_STOP_TYPE_TABLE);
        db.insert(CommonStopType.TABLE, CONFLICT_FAIL,
                new CommonStopType.SqlBuilder().type("出門").build());
        db.insert(CommonStopType.TABLE, CONFLICT_FAIL,
                new CommonStopType.SqlBuilder().type("回家").build());
        db.insert(CommonStopType.TABLE, CONFLICT_FAIL,
                new CommonStopType.SqlBuilder().type("工作").build());
        db.insert(CommonStopType.TABLE, CONFLICT_FAIL,
                new CommonStopType.SqlBuilder().type("七桃").build());
    }

    @Override
    public void onUpgrade(SupportSQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
