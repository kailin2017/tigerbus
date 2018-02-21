package com.tigerbus.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.tigerbus.TigerApplication;
import com.tigermvp.log.TlogType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class TigerPreferences {

    private SharedPreferences preferences;
    private Gson gson = new Gson();

    public TigerPreferences(Context context) {
        preferences = context.getSharedPreferences("", context.MODE_PRIVATE);
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public void putString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public void putStringSet(String key, Set<String> value) {
        preferences.edit().putStringSet(key, value).apply();
    }

    public Set<String> getStringset(String key) {
        return preferences.getStringSet(key, new HashSet<>());
    }

    public void putInt(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    public <T> void putObject(String key, T value, boolean isEncrypt) {
        String strValue = gson.toJson(value);
        putPreferencesLog(key, strValue);
        if (isEncrypt) {
            putEncrypt(key, strValue);
        } else {
            preferences.edit().putString(key, strValue).apply();
        }
    }

    public <T> T getObject(String key, Class<T> clazz, boolean isEncrypt) {
        String result = isEncrypt ? getEncrypt(key) : preferences.getString(key, "");
        getPreferencesLog(key, result);
        return gson.fromJson(result, clazz);
    }

    public <T> ArrayList<T> getObjectArrayList(String key, Class<T[]> clazz, boolean isEncrypt) {
        String result = isEncrypt ? getEncrypt(key) : preferences.getString(key, "");
        T[] ts = gson.fromJson(result, clazz);
        getPreferencesLog(key, ts);
        return new ArrayList<>(Arrays.asList(ts));
    }

    public void putEncrypt(String key, String value) {
        Set<String> encodeValue = AESGCMHelper.encrypt(value);
        preferences.edit().putStringSet(key, encodeValue).apply();
    }

    public String getEncrypt(String key) {
        Set<String> stringSet = preferences.getStringSet(key, new HashSet<>());
        return stringSet.size() == 2 ? AESGCMHelper.decrypt(stringSet) : "";
    }

    public <T> void putPreferencesLog(String key, T t) {
        TigerApplication.printLog(TlogType.debug, "putPreferencesLog", getLogMsg(key, t));
    }

    public <T> void getPreferencesLog(String key, T t) {
        TigerApplication.printLog(TlogType.debug, "getPreferencesLog", getLogMsg(key, t));
    }

    private <T> String getLogMsg(String key, T t) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("key:");
        stringBuffer.append(key);
        stringBuffer.append(",value:");
        stringBuffer.append(TigerApplication.object2String(t));
        return stringBuffer.toString();
    }


}
