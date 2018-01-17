package com.tigerbus.data.autovalue;

import com.google.auto.value.AutoValue;
import com.tigerbus.data.bus.BusA2Data;
import com.tigerbus.data.bus.BusEstimateTime;

import java.util.ArrayList;

@AutoValue
public abstract class BusA2DataListAutoValue {
    public static BusA2DataListAutoValue create(
            ArrayList<BusA2Data> busA2Datas,
            ArrayList<BusEstimateTime> busEstimateTimes) {
        return new AutoValue_BusA2DataListAutoValue(busA2Datas, busEstimateTimes);
    }

    public abstract ArrayList<BusA2Data> busA2Datas();

    public abstract ArrayList<BusEstimateTime> busEstimateTimes();
}
