package com.tigerbus.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import com.tigerbus.R;

import java.util.Random;

import io.reactivex.Observable;

public final class NotificationChannelUtil {

    /**
     * getId() — 獲取 ChannleId
     * enableLights() — 開啟指示燈，如果設備有的話。
     * setLightColor() — 設置指示燈顏色
     * enableVibration() — 開啟震動
     * setVibrationPattern() — 設置震動頻率
     * setImportance() — 設置頻道重要性
     * getImportance() — 獲取頻道重要性
     * setSound() — 設置聲音
     * getSound() — 獲取聲音
     * setGroup() — 設置 ChannleGroup
     * getGroup() — 得到 ChannleGroup
     * setBypassDnd() — 設置繞過免打擾模式
     * canBypassDnd() — 檢測是否繞過免打擾模式
     * getName() — 獲取名稱
     * setLockScreenVisibility() — 設置是否應在鎖定屏幕上顯示此頻道的通知
     * getLockscreenVisibility() — 檢測是否應在鎖定屏幕上顯示此頻道的通知
     * setShowBadge() 設置是否顯示角標
     * canShowBadge() — 檢測是否顯示角標
     */

    public static void initNotificationChannel(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Observable.just(true)
                .filter(notificationChannelType -> Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                .flatMap(aBoolean -> Observable.fromArray(NotificationChannelType.values()))
                .subscribe(notificationChannelType -> {
                    NotificationChannel notificationChannel = createNotifcationChannel(context, notificationChannelType);
                    notificationManager.createNotificationChannel(notificationChannel);
                });
    }

    private static NotificationChannel createNotifcationChannel(Context context, NotificationChannelType notificationChannelType) {
        NotificationChannel notificationChannel = new NotificationChannel(
                Integer.toString(notificationChannelType.getTypeId()),
                context.getString(notificationChannelType.getTypeId()),
                NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 100, 300});
        notificationChannel.setImportance(NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationChannel.setShowBadge(true);
        notificationChannel.setBypassDnd(true);
        return notificationChannel;
    }

    public static void sendNotification(Context context, NotificationChannelType notificationChannelType, String title, String text) {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setSmallIcon(R.drawable.cast_ic_notification_small_icon);
        builder.setNumber(new Random().nextInt(Integer.MAX_VALUE));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            builder.setChannelId(Integer.toString(notificationChannelType.getTypeId()));
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationChannelType.getTypeId(), builder.build());
    }
}
