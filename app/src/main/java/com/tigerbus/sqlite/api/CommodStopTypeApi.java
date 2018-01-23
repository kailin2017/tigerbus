package com.tigerbus.sqlite.api;

import com.squareup.sqlbrite3.BriteDatabase;
import com.tigerbus.sqlite.data.CommodStopType;

import java.util.List;

public interface CommodStopTypeApi {

    String TAG = CommodStopTypeApi.class.getSimpleName();

    default void initCommodStopTypes(BriteDatabase briteDatabase) {
        briteDatabase
                .createQuery(CommodStopType.TABLE, CommodStopType.QUERY)
                .mapToList(CommodStopType::mapper)
                .subscribe(this::initCommodStopTypes, this::initCommodStopTypes);
    }

    void initCommodStopTypes(List<CommodStopType> commodStopTypes);

    void initCommodStopTypes(Throwable throwable);
}
