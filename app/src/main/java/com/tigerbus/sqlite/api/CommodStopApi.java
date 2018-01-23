package com.tigerbus.sqlite.api;

import com.squareup.sqlbrite3.BriteDatabase;
import com.tigerbus.sqlite.data.CommodStop;
import com.tigerbus.sqlite.data.CommodStopType;

import java.util.List;

public interface CommodStopApi {

    String TAG = CommodStopApi.class.getSimpleName();

    default void initCommodStop(BriteDatabase briteDatabase) {
        briteDatabase
                .createQuery(CommodStop.QUERY_TABLES, CommodStop.QUERY)
                .mapToList(CommodStop::mapper)
                .subscribe(this::initCommodStop, this::initCommodStop);
    }

    void initCommodStop(List<CommodStop> commodStopTypes);

    void initCommodStop(Throwable throwable);
}
