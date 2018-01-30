package com.tigerbus.notification;

import android.app.NotificationChannel;

import java.util.HashMap;

public final class NotificationChannelUtil {

    public static HashMap<String, NotificationChannel> notificationChannelHashMap = new HashMap<>();

    public synchronized static NotificationChannel getInstance(String key) {
        synchronized (HashMap.class) {
            NotificationChannel notificationChannel = notificationChannelHashMap.get(key);
            if (notificationChannel == null)
                createInstance();
            return notificationChannel;
        }
    }

    public static void createInstance() {
//        notificationChannelHashMap = new NotificationChannel();
    }
}
