package com.tigerbus;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.tigerbus.base.log.Tlog;
import com.tigerbus.base.log.TlogType;
import com.tigerbus.data.CityBusService;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.util.TigerPreferences;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class TigerApplication extends Application {

    private final static String TAG = TigerApplication.class.getName();
    private static Context context;
    private static TigerPreferences tigerPreferences;
    private static WeakReference<ArrayList<BusRoute>> busRouteData;
    private static Gson gson = new Gson();

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

    public static ArrayList<BusRoute> getBusRouteData() {
        return busRouteData.get();
    }

    public static void setBusRouteData(ArrayList<BusRoute> busRouteData) {
        TigerApplication.busRouteData = new WeakReference<>(busRouteData);
    }

    public static void putInt(String key, int value){
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

    public static <T> String object2String(T t){
        return gson.toJson(t);
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
