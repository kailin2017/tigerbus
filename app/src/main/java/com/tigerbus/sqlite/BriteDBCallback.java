package com.tigerbus.sqlite;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;

import com.tigerbus.sqlite.data.CommodStop;
import com.tigerbus.sqlite.data.CommodStopType;

import java.io.IOException;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_FAIL;

final class BriteDBCallback extends SupportSQLiteOpenHelper.Callback {
    private static final int VERSION = 1;

    private static final String CREATE_COMMODSTOP_TABLE = ""
            + "CREATE TABLE " + CommodStop.TABLE + "("
            + CommodStop.ID + " TEXT NOT NULL PRIMARY KEY,"
            + CommodStop.STOP + " TEXT NOT NULL,"
            + CommodStop.ROUTE + " TEXT NOT NULL,"
            + CommodStop.ROUTESUB + " TEXT NOT NULL,"
            + CommodStop.TYPE + " TEXT NOT NULL"
            + ")";
    private static final String CREATE_COMMOD_STOP_TYPE_TABLE = ""
            + "CREATE TABLE " + CommodStopType.TABLE + "("
            + CommodStopType.ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + CommodStopType.TYPENAME + " TEXT NOT NULL"
            + ")";

    BriteDBCallback() {
        super(VERSION);
    }

    @Override
    public void onCreate(SupportSQLiteDatabase db) {
        db.execSQL(CREATE_COMMODSTOP_TABLE);
        db.execSQL(CREATE_COMMOD_STOP_TYPE_TABLE);
        db.insert(CommodStopType.TABLE, CONFLICT_FAIL,
                new CommodStopType.SqlBuilder().type("出門").build());
        db.insert(CommodStopType.TABLE, CONFLICT_FAIL,
                new CommodStopType.SqlBuilder().type("回家").build());
        db.insert(CommodStopType.TABLE, CONFLICT_FAIL,
                new CommodStopType.SqlBuilder().type("工作").build());
        db.insert(CommodStopType.TABLE, CONFLICT_FAIL,
                new CommodStopType.SqlBuilder().type("七桃").build());
    }

    @Override
    public void onUpgrade(SupportSQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
