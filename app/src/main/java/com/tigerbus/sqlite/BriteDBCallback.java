package com.tigerbus.sqlite;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;

import com.tigerbus.sqlite.data.CommonStopType;
import com.tigerbus.sqlite.table.CreateTableEnum;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_FAIL;

final class BriteDBCallback extends SupportSQLiteOpenHelper.Callback implements DBString {
    private static final int VERSION = 1;

    BriteDBCallback() {
        super(VERSION);
    }

    @Override
    public void onCreate(SupportSQLiteDatabase db) {
        for(CreateTableEnum createTableEnum : CreateTableEnum.values()){
            db.execSQL(createTableEnum.getCreateTableString());
        }
        db.insert(CommonStopType.TABLE, CONFLICT_FAIL,
                new CommonStopType.SqlBuilder().type("全部").build());
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
