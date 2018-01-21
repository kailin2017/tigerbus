package com.tigerbus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Kailin on 2018/1/20.
 */

public final class TigerBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()){
            case Intent.ACTION_PACKAGE_ADDED:
                break;
            case Intent.ACTION_PACKAGE_DATA_CLEARED:
                break;
            case Intent.ACTION_UNINSTALL_PACKAGE:
                break;
            case Intent.ACTION_USER_BACKGROUND:
                break;
        }
    }
}
