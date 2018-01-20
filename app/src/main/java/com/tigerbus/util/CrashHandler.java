package com.tigerbus.util;

import android.content.Context;
import android.os.Process;

import com.google.firebase.crash.FirebaseCrash;
import com.tigerbus.TigerApplication;
import com.tigerbus.base.log.TlogType;

public final class CrashHandler implements Thread.UncaughtExceptionHandler {

    private final static String TAG = CrashHandler.class.getSimpleName();


    private static CrashHandler crashHandler = new CrashHandler();
    private Context context;

    public void init(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.context = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        FirebaseCrash.report(throwable);
        TigerApplication.printLog(TlogType.wtf, TAG, throwable.toString());
        Process.killProcess(android.os.Process.myPid());
    }
}
