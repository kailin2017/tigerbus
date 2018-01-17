package com.tigerbus.data.autovalue;

import com.google.auto.value.AutoValue;
import com.tigerbus.data.bus.BusA1Data;
import com.tigerbus.data.bus.BusEstimateTime;

import java.util.ArrayList;

@AutoValue
public abstract class BusA1DataListAutoValue {
    public static BusA1DataListAutoValue create(
            ArrayList<BusA1Data> busA1Datas,
            ArrayList<BusEstimateTime> busEstimateTimes) {
        return new AutoValue_BusA1DataListAutoValue(busA1Datas, busEstimateTimes);
    }

    public abstract ArrayList<BusA1Data> busA1Datas();

    public abstract ArrayList<BusEstimateTime> busEstimateTimes();
}
