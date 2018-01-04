package com.tigerbus;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import com.tigerbus.base.log.Tlog;
import com.tigerbus.base.log.TlogType;
import com.tigerbus.data.BusRoute;
import com.tigerbus.key.PreferencesKey;
import com.tigerbus.util.AESGCMHelper;
import com.tigerbus.util.TigerPreferences;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;

public final class TigerApplication extends Application {

    private final static String TAG = TigerApplication.class.getName();
    private static Context context;
    private static TigerPreferences tigerPreferences;
    public static WeakHashMap<String, ArrayList<BusRoute>> weakHashMap = new WeakHashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
        this.tigerPreferences = new TigerPreferences(context);
    }

    public static void printLog(TlogType tlogType, String tag, String message) {
        Tlog.printLog(tlogType, tag, message);
    }

    public static <T> T injectResource(String clazz, String resource) {
        try {
            Field field = Class.forName(clazz).getDeclaredField(resource);
            return (T) field.get(null);
        } catch (Exception e) {
            printLog(TlogType.error, TAG, e.toString());
            return null;
        }
    }

    public static void getKey() {
        try {
            Set<String> result = AESGCMHelper.encrypt("hyperTiger");
            AESGCMHelper.decrypt(result);
        } catch (Exception e) {
            Tlog.printLog(TlogType.error, TAG, e.toString());
        }
    }

    public static void writePreferences(String key, String value) {
        tigerPreferences.WritePreferences(key, value);
    }

    public static String readPreferences(String key) {
        return tigerPreferences.ReadPreferences(key);
    }
}
