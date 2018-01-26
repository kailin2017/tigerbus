package com.tigerbus.sqlite.data;

import com.squareup.sqlbrite3.BriteDatabase;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public interface CommonStopTypeApi {

    String TAG = CommonStopTypeApi.class.getSimpleName();

    default void initCommodStopTypes(BriteDatabase briteDatabase) {
        briteDatabase
                .createQuery(CommonStopType.TABLE, CommonStopType.QUERY)
                .mapToList(CommonStopType::mapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::initCommodStopTypes, this::initCommodStopTypes);
    }

    void initCommodStopTypes(List<CommonStopType> commonStopTypes);

    void initCommodStopTypes(Throwable throwable);
}
