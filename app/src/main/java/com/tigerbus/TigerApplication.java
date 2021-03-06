package com.tigerbus;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.detail.City;
import com.tigerbus.notification.NotificationChannelType;
import com.tigerbus.notification.NotificationChannelUtil;
import com.tigerbus.sqlite.data.CommonStopType;
import com.tigerbus.util.TigerPreferences;
import com.tigermvp.log.Tlog;
import com.tigermvp.log.TlogType;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class TigerApplication extends Application {

    private final static String TAG = TigerApplication.class.getName();
    private static Context context;
    private static TigerPreferences tigerPreferences;
    private static SoftReference<ArrayList<BusRoute>> busRouteData, busRouteDataBacuUp;
    private static SoftReference<HashMap<Integer, CommonStopType>> coomdStopType;
    private static SoftReference<ArrayList<City>> cityData;
    private static Gson gson = new Gson();

    public static void printLog(TlogType tlogType, String tag, Object o) {
        Tlog.printLog(tlogType, tag, object2String(o));
    }

    public static void printLog(TlogType tlogType, String tag, String message) {
        Tlog.printLog(tlogType, tag, message);
    }

    public static ArrayList<BusRoute> getBusRouteData() {
        ArrayList<BusRoute> busRoutes = busRouteData.get();
        if (busRoutes == null) {
            busRoutes = busRouteDataBacuUp.get();
            busRouteData = new SoftReference<>(busRoutes);
        }
        return busRoutes;
    }

    public static void setBusRouteData(ArrayList<BusRoute> busRouteData) {
        TigerApplication.busRouteData = new SoftReference<>(busRouteData);
        TigerApplication.busRouteDataBacuUp = new SoftReference<>(busRouteData);
    }

    public static ArrayList<City> getCityData() {
        return cityData.get();
    }

    public static void setCityData(ArrayList<City> citys) {
        cityData = new SoftReference<>(citys);
    }

    public static void putInt(String key, int value) {
        tigerPreferences.putInt(key, value);
    }

    public static int getInt(String key) {
        return tigerPreferences.getInt(key);
    }

    public static void putString(String key, String value) {
        tigerPreferences.putString(key, value);
    }

    public static String getString(String key) {
        return tigerPreferences.getString(key);
    }

    public static <T> void putObject(String key, T value, boolean isEncrypt) {
        tigerPreferences.putObject(key, value, isEncrypt);
    }

    public static <T> T getObject(String key, Class<T> clazz, boolean isEncrypt) {
        return tigerPreferences.getObject(key, clazz, isEncrypt);
    }

    public static <T> ArrayList<T> getObjectArrayList(String key, Class<T[]> clazz, boolean isEncrypt) {
        return tigerPreferences.getObjectArrayList(key, clazz, isEncrypt);
    }

    public static <T> String object2String(T t) {
        return gson.toJson(t);
    }

    public static <T> T string2Object(Class<T> clazz, String s) {
        return gson.fromJson(s, clazz);
    }

    public static void putEncrypt(String key, String value) {
        tigerPreferences.putEncrypt(key, value);
    }

    public static String getEncrypt(String key) {
        return tigerPreferences.getEncrypt(key);
    }

    public static HashMap<Integer, CommonStopType> getCommodStopTypes() {
        return coomdStopType.get();
    }

    public static void setCommodStopTypes(List<CommonStopType> commonStopTypes) {
        HashMap<Integer, CommonStopType> hashMap = new HashMap<>();
        for (CommonStopType commonStopType : commonStopTypes)
            hashMap.put(commonStopType.id(), commonStopType);
        coomdStopType = new SoftReference<>(hashMap);
    }

    public static <T> void putStringSet(String key, Set<T> tSet) {
        Set<String> temp = new HashSet<>();
        for (T t : tSet)
            temp.add(object2String(t));
        tigerPreferences.putStringSet(key, temp);
    }

    public static <T> HashSet<T> getStringSet(Class<T> clazz, String key) {
        HashSet<T> result = new HashSet<>();
        for (String s : tigerPreferences.getStringset(key)) {
            result.add(string2Object(clazz, s));
        }
        return result;
    }

    public static void sendNotification(Context context, NotificationChannelType notificationChannelType, String title, String text) {
        NotificationChannelUtil.initNotificationChannel(context);
        NotificationChannelUtil.sendNotification(context, notificationChannelType, title, text);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
        this.tigerPreferences = new TigerPreferences(context);
//        CrashHandler crashHandler = new CrashHandler();
//        crashHandler.init(context);
    }

}
