package com.tigerbus.sqlite.data;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.tigerbus.data.bus.BusEstimateTime;

@AutoValue
public abstract class CommodStopQueryResult implements Parcelable {

    public static final CommodStopQueryResult create(CommonStops commonStops, BusEstimateTime busEstimateTime) {
        return new AutoValue_CommodStopQueryResult(commonStops, busEstimateTime);
    }

    public abstract CommonStops commonStop();

    public abstract BusEstimateTime busEstimateTime();
}
