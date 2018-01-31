package com.tigerbus.util;

import android.content.Context;

import com.tigerbus.R;

public interface Sec2Min {

    default String sec2Min(Context context, int sec) {
        String result;
        if (sec == 0) {
            result = context.getString(R.string.stop_status_nobus);
        } else if (sec < 120) {
            result = context.getString(R.string.stop_status_pit);
        } else {
            result = String.format(context.getString(R.string.stop_status_min), sec / 60, sec % 60);
        }
        return result;
    }
}
