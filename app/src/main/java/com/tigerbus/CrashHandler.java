package com.tigerbus;

import android.content.Context;
import android.os.Process;

import com.google.firebase.crash.FirebaseCrash;
import com.tigerbus.base.log.TlogType;

public final class CrashHandler implements Thread.UncaughtExceptionHandler {

    private final static String TAG = CrashHandler.class.getSimpleName();


    private static CrashHandler crashHandler = new CrashHandler();
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    private Context context;

    public static CrashHandler getCrashHandler() {
        return crashHandler;
    }

    public void init(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        this.context = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        FirebaseCrash.report(throwable);
        TigerApplication.printLog(TlogType.wtf, TAG, throwable.toString());
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, throwable);
        } else {
            Process.killProcess(android.os.Process.myPid());
        }
    }
}
