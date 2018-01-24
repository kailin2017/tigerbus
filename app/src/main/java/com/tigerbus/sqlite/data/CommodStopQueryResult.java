package com.tigerbus.sqlite.data;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.tigerbus.data.bus.BusEstimateTime;

@AutoValue
public abstract class CommodStopQueryResult implements Parcelable {

    public static final CommodStopQueryResult create(CommonStop commonStop, BusEstimateTime busEstimateTime) {
        return new AutoValue_CommodStopQueryResult(commonStop, busEstimateTime);
    }

    public abstract CommonStop commonStop();

    public abstract BusEstimateTime busEstimateTime();
}
