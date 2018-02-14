package com.tigerbus.sqlite;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;

import com.tigerbus.sqlite.table.CreateTableEnum;
import com.tigerbus.sqlite.table.CreateTableObj;

final class BriteDBCallback extends SupportSQLiteOpenHelper.Callback {
    private static final int VERSION = 1;

    BriteDBCallback() {
        super(VERSION);
    }

    @Override
    public void onCreate(SupportSQLiteDatabase database) {
        for (CreateTableEnum createTableEnum : CreateTableEnum.values()) {
            CreateTableObj createTableObj = createTableEnum.getCreateTableObj();
            database.execSQL(createTableObj.getCreateTableString());
            createTableObj.initDefaultData(database);
        }
    }


    @Override
    public void onUpgrade(SupportSQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
