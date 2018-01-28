package com.tigerbus.sqlite;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;

import com.tigerbus.sqlite.data.CommonStop;
import com.tigerbus.sqlite.data.CommonStopType;
import com.tigerbus.sqlite.data.RemindStop;
import com.tigerbus.sqlite.data.RouteStop;
import com.tigerbus.sqlite.data.WeekStatus;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_FAIL;

final class BriteDBCallback extends SupportSQLiteOpenHelper.Callback {
    private static final int VERSION = 1;
    private static final String COMMA = ",";
    private static final String CREATE_TABLE = "CREATE TABLE ";
    private static final String PRIMARY_KEY = " PRIMARY KEY ";
    private static final String AUTOINCREMENT = " AUTOINCREMENT ";
    private static final String TEXT_NOT_NULL = " TEXT NOT NULL ";
    private static final String INTEGER_NOT_NULL = " INTEGER NOT NULL ";
    private static final String INTEGER_DEFAULT_0 = " INTEGER DEFAULT 0 ";
    private static final String BIGINT_NOT_NULL = " BIGINT NOT NULL ";
    private static final String BIGINT_DEFAULT_0 = " BIGINT DEFAULT 0 ";

    private static final String CREATE_COMMOD_STOPS_TABLE = CREATE_TABLE + CommonStop.TABLE + "("
            + CommonStop.ID + TEXT_NOT_NULL + PRIMARY_KEY + COMMA
            + CommonStop.ROUTESTOP + TEXT_NOT_NULL + COMMA
            + CommonStop.TYPE + TEXT_NOT_NULL + ")";

    private static final String CREATE_COMMOD_STOP_TYPE_TABLE = CREATE_TABLE + CommonStopType.TABLE + "("
            + CommonStopType.ID + INTEGER_NOT_NULL + PRIMARY_KEY + AUTOINCREMENT + COMMA
            + CommonStopType.TYPENAME + TEXT_NOT_NULL + ")";

    private static final String CREATE_ROUTE_STOP_TABLE = CREATE_TABLE + RouteStop.TABLE + "("
            + RouteStop.ID + TEXT_NOT_NULL + PRIMARY_KEY + COMMA
            + RouteStop.STOP + TEXT_NOT_NULL + COMMA
            + RouteStop.ROUTE + TEXT_NOT_NULL + COMMA
            + RouteStop.ROUTESUB + TEXT_NOT_NULL + ")";

    private static final String CREATE_WEEK_STATUS_TABLE = CREATE_TABLE + WeekStatus.TABLE + "("
            + WeekStatus.ID + TEXT_NOT_NULL + PRIMARY_KEY + COMMA
            + WeekStatus.SUN + INTEGER_DEFAULT_0 + COMMA
            + WeekStatus.MON + INTEGER_DEFAULT_0 + COMMA
            + WeekStatus.THU + INTEGER_DEFAULT_0 + COMMA
            + WeekStatus.WED + INTEGER_DEFAULT_0 + COMMA
            + WeekStatus.TUE + INTEGER_DEFAULT_0 + COMMA
            + WeekStatus.FRI + INTEGER_DEFAULT_0 + COMMA
            + WeekStatus.SAT + INTEGER_DEFAULT_0 + ")";

    private static final String CREATE_REMIND_STOP_TABLE = CREATE_TABLE + RemindStop.TABLE + "("
            + RemindStop.ID + TEXT_NOT_NULL + PRIMARY_KEY + COMMA
            + RemindStop.ISONE + INTEGER_DEFAULT_0 + COMMA
            + RemindStop.ISRUN + INTEGER_DEFAULT_0 + COMMA
            + RemindStop.REMIND_MINUTE + INTEGER_NOT_NULL + COMMA
            + RemindStop.DURATION_START + BIGINT_DEFAULT_0 + COMMA
            + RemindStop.DURATION_END + BIGINT_DEFAULT_0 + COMMA
            + RemindStop.ROUTESTOP + TEXT_NOT_NULL + COMMA
            + RemindStop.RUNWEEK + TEXT_NOT_NULL + ")";

    BriteDBCallback() {
        super(VERSION);
    }

    @Override
    public void onCreate(SupportSQLiteDatabase db) {
        db.execSQL(CREATE_COMMOD_STOP_TYPE_TABLE);
        db.execSQL(CREATE_COMMOD_STOPS_TABLE);
        db.execSQL(CREATE_ROUTE_STOP_TABLE);
        db.execSQL(CREATE_WEEK_STATUS_TABLE);
        db.execSQL(CREATE_REMIND_STOP_TABLE);
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
