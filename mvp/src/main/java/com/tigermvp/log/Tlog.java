package com.tigermvp.log;

import android.util.Log;

public final class Tlog {

    public static void printLog(TlogType tlogType, String tag, String msg, Throwable throwable) {
        getMethod(tlogType, new Class[]{String.class, String.class, Throwable.class}, new Object[]{tag, msg, throwable});
    }

    public static void printLog(TlogType tlogType, String tag, Throwable throwable) {
        getMethod(tlogType, new Class[]{String.class, Throwable.class}, new Object[]{tag, throwable});
    }

    public static void printLog(TlogType tlogType, String tag, String msg) {
        getMethod(tlogType, new Class[]{String.class, String.class}, new Object[]{tag, msg});
    }

    public static void printLog(TlogType tlogType,  String msg) {
        getMethod(tlogType, new Class[]{String.class, String.class}, new Object[]{tlogType.getType(), msg});
    }

    private static void getMethod(TlogType tlogType, Class[] parameterTypes, Object parameter[]) {
        try {
            Log.class.getMethod(tlogType.getType(), parameterTypes).invoke(null, parameter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
