package com.tigerbus.sqlite.data;

import com.squareup.sqlbrite3.BriteDatabase;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public interface CommonStopInterface {

    String TAG = CommonStopInterface.class.getSimpleName();

    default void initCommodStops(BriteDatabase briteDatabase) {
        briteDatabase
                .createQuery(CommonStop.QUERY_TABLES, CommonStop.QUERY)
                .mapToList(CommonStop::mapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::initCommodStops, this::initCommodStops);
    }

    void initCommodStops(List<CommonStop> commonStops);

    void initCommodStops(Throwable throwable);
}
