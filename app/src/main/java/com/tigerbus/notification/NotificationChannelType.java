package com.tigerbus.notification;

import com.tigerbus.R;

/**
 * Created by Kailin on 2018/1/30.
 */

public enum NotificationChannelType {

    channel_default(R.string.notification_channel_default),
    channel_remind(R.string.notification_channel_remind);

    private int channel;

    NotificationChannelType(int channel) {
        this.channel = channel;
    }

    public int getTypeId(){
        return channel;
    }
}
