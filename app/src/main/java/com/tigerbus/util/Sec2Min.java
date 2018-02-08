package com.tigerbus.util;

import android.content.Context;
import android.graphics.Color;

import com.google.auto.value.AutoValue;
import com.tigerbus.R;

public interface Sec2Min {

    default MinResult sec2Min(Context context, int sec) {
        MinResult result;
        if (sec < 1) {
            result = MinResult.create(context.getString(R.string.stop_status_nobus), Color.GRAY);
        } else if (sec < 120) {
            result = MinResult.create(context.getString(R.string.stop_status_pit), 0xFF3F51B5);
        } else {
            result = MinResult.create(String.format(context.getString(R.string.stop_status_min), sec / 60, sec % 60), 0xFF3F51B5);
        }
        return result;
    }

    @AutoValue
    abstract class MinResult {

        public abstract String string();

        public abstract int color();

        public final static MinResult create(String string, int color) {
            return new AutoValue_Sec2Min_MinResult(string, color);
        }
    }
}
