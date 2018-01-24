package com.tigerbus.sqlite.data;

import com.squareup.sqlbrite3.BriteDatabase;

import java.util.List;

public interface CommonStopTypeApi {

    String TAG = CommonStopTypeApi.class.getSimpleName();

    default void initCommodStopTypes(BriteDatabase briteDatabase) {
        briteDatabase
                .createQuery(CommonStopType.TABLE, CommonStopType.QUERY)
                .mapToList(CommonStopType::mapper)
                .subscribe(this::initCommodStopTypes, this::initCommodStopTypes);
    }

    void initCommodStopTypes(List<CommonStopType> commonStopTypes);

    void initCommodStopTypes(Throwable throwable);
}
