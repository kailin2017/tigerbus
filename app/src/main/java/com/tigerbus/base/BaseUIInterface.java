package com.tigerbus.base;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.tigerbus.Service.RemindService;

/**
 * Created by Kailin on 2018/1/20.
 */

public interface BaseUIInterface extends DialogInterface.Progress, DialogInterface.Message {

    default void startActivity(@NonNull Context context, @NonNull Class clazz) {
        context.startActivity(new Intent(context, clazz));
    }

    default void startActivity(@NonNull Context context, @NonNull Class clazz, @NonNull Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    default <T extends Service> void bindService(@NonNull Context context, @NonNull Class<T> clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        intent.putExtras(bundle);
        context.startService(intent);
    }

    default <T extends Service> void stopService(@NonNull Context context, @NonNull Class<T> clazz) {
        Intent intent = new Intent(context, clazz);
        context.stopService(intent);
    }
}
