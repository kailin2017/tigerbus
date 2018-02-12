package com.tigerbus.sqlite;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory;

import com.squareup.sqlbrite3.BriteDatabase;
import com.tigerbus.BuildConfig;

import io.reactivex.schedulers.Schedulers;

public final class BriteDB {

    private static BriteDatabase briteDatabase;

    public static BriteDatabase getInstance(Application application) {
        if (briteDatabase == null) {
            synchronized (BriteDatabase.class) {
                if (briteDatabase == null) {
                    createInstance(application);
                }
            }
        }
        return briteDatabase;
    }

    private static void createInstance(Application application) {
        SupportSQLiteOpenHelper.Configuration configuration =
                SupportSQLiteOpenHelper.Configuration.builder(application)
                        .name(BuildConfig.LocalDBName).callback(new BriteDBCallback()).build();
        SupportSQLiteOpenHelper helper = new FrameworkSQLiteOpenHelperFactory().create(configuration);

        briteDatabase = BriteSQL.getInstance().wrapDatabaseHelper(helper, Schedulers.io());
        briteDatabase.setLoggingEnabled(true);
    }

}
