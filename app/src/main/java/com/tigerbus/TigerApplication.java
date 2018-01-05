package com.tigerbus;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.tigerbus.base.log.Tlog;
import com.tigerbus.base.log.TlogType;
import com.tigerbus.data.BusRoute;
import com.tigerbus.util.AESGCMHelper;
import com.tigerbus.util.TigerPreferences;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.WeakHashMap;

public final class TigerApplication extends Application {

    private final static String TAG = TigerApplication.class.getName();
    public static WeakHashMap<String, ArrayList<BusRoute>> weakHashMap = new WeakHashMap<>();
    private static Context context;
    private static TigerPreferences tigerPreferences;

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

    public static void putInt(String key,int value){
        tigerPreferences.putInt(key,value);
    }

    public static int getInt(String key){
        return tigerPreferences.getInt(key);
    }

    public static void putString(String key,String value){
        tigerPreferences.putString(key,value);
    }

    public static String getString(String key){
        return tigerPreferences.getString(key);
    }

    public static <T> void putObject(String key, T value, boolean isEncrypt) {
        tigerPreferences.putObject(key,value,isEncrypt);
    }

    public static <T> T getObject(String key, Class<T> clazz, boolean isEncrypt) {
        return tigerPreferences.getObject(key,clazz,isEncrypt);
    }

    public static <T> ArrayList<T> getObjectArrayList(String key, Class<T[]> clazz, boolean isEncrypt) {
        return tigerPreferences.getObjectArrayList(key,clazz,isEncrypt);
    }

    public static void putEncrypt(String key, String value) {
        tigerPreferences.putEncrypt(key, value);
    }

    public static String getEncrypt(String key) {
        return tigerPreferences.getEncrypt(key);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
        this.tigerPreferences = new TigerPreferences(context);
    }
}
