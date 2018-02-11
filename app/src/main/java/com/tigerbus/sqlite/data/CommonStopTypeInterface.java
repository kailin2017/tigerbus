package com.tigerbus.sqlite.data;

import com.squareup.sqlbrite3.BriteDatabase;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public interface CommonStopTypeInterface {

    String TAG = CommonStopTypeInterface.class.getSimpleName();

    default Disposable initCommodStopTypes(BriteDatabase briteDatabase) {
        return briteDatabase
                .createQuery(CommonStopType.TABLE, CommonStopType.getQueryString())
                .mapToList(CommonStopType::mapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::initCommodStopTypes, this::initCommodStopTypes);
    }

    void initCommodStopTypes(List<CommonStopType> commonStopTypes);

    void initCommodStopTypes(Throwable throwable);
}
