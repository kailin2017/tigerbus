package com.tigerbus.sqlite;

import android.app.Application;
import android.arch.persistence.db.BuildConfig;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory;

import com.squareup.sqlbrite3.BriteDatabase;

import io.reactivex.schedulers.Schedulers;

public final class BriteSQL {

    private static BriteDatabase briteDatabase;

    public synchronized static BriteDatabase getInstance(Application application) {
        synchronized (BriteDatabase.class) {
            if (briteDatabase == null) {
                createInstance(application);
            }
            return briteDatabase;
        }
    }

    private static void createInstance(Application application){
        SupportSQLiteOpenHelper.Configuration configuration =
                SupportSQLiteOpenHelper.Configuration.builder(application)
                .name(BuildConfig.LocalDBName).callback(new BriteDBCallback()).build();
        SupportSQLiteOpenHelper helper = new FrameworkSQLiteOpenHelperFactory().create(configuration);
        briteDatabase = BriteDB.getInstance().wrapDatabaseHelper(helper, Schedulers.io());
        briteDatabase.setLoggingEnabled(true);
    }

}
