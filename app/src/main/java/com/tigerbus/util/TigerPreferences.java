package com.tigerbus.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

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

    public void putInt(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    public <T> void putObject(String key, T value, boolean isEncrypt) {
        String strValue = gson.toJson(value);
        if (isEncrypt)
            putEncrypt(key, strValue);
        else
            preferences.edit().putString(key, strValue).apply();
    }

    public <T> T getObject(String key, Class<T> clazz, boolean isEncrypt) {
        String result = isEncrypt ? getEncrypt(key) : preferences.getString(key, "");
        return gson.fromJson(result, clazz);
    }

    public <T> ArrayList<T> getObjectArrayList(String key, Class<T[]> clazz, boolean isEncrypt) {
        String result = isEncrypt ? getEncrypt(key) : preferences.getString(key, "");
        T[] busRoutes = gson.fromJson(result, clazz);
        return new ArrayList<>(Arrays.asList(busRoutes));
    }

    public void putEncrypt(String key, String value) {
        Set<String> encodeValue = AESGCMHelper.encrypt(value);
        preferences.edit().putStringSet(key, encodeValue).apply();
    }

    public String getEncrypt(String key) {
        Set<String> stringSet = preferences.getStringSet(key, new HashSet<>());
        return stringSet.size() == 2 ? AESGCMHelper.decrypt(stringSet) : "";
    }

}
