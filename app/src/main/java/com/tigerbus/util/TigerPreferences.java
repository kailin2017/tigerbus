package com.tigerbus.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;


public final class TigerPreferences {

    private SharedPreferences preferences;

    public TigerPreferences(Context context) {
        preferences = context.getSharedPreferences("", context.MODE_PRIVATE);
    }

    public void WritePreferences(String key, String value) {
        Set<String> encodeValue = AESGCMHelper.encrypt(value);
        preferences.edit().putStringSet(key, encodeValue).apply();
    }

    public String ReadPreferences(String key) {
        Set<String> stringSet = preferences.getStringSet(key, new HashSet<>());
        return stringSet.size() == 2 ? AESGCMHelper.decrypt(stringSet) : "";
    }
}
