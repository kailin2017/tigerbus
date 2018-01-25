package com.tigerbus.data.autovalue;

import com.google.auto.value.AutoValue;
import com.tigerbus.sqlite.data.CommodStopQueryResult;
import com.tigerbus.sqlite.data.CommonStopType;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.PublishSubject;

@AutoValue
public abstract class HomePresenterAutoValue {

    public abstract CommonStopType commonStopType();

    public abstract PublishSubject<ArrayList<CommodStopQueryResult>> publishSubject();

    public abstract ArrayList<CommodStopQueryResult> commodStopQueryResults();

    public static final HomePresenterAutoValue create(
            @NonNull CommonStopType commonStopType,
            @NonNull PublishSubject<ArrayList<CommodStopQueryResult>> publishSubject,
            @NonNull ArrayList<CommodStopQueryResult> commodStopQueryResults) {
        return new AutoValue_HomePresenterAutoValue(commonStopType, publishSubject, commodStopQueryResults);
    }
}
