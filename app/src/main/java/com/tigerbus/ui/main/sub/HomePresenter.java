package com.tigerbus.ui.main.sub;

import com.squareup.sqlbrite3.BriteDatabase;
import com.tigerbus.BuildConfig;
import com.tigerbus.base.BasePresenter;
import com.tigerbus.data.CityBusInterface;
import com.tigerbus.sqlite.data.CommodStop;
import com.tigerbus.sqlite.data.CommodStopType;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public final class HomePresenter extends BasePresenter<HomeView> implements CityBusInterface {

    private final static String TAG = HomePresenter.class.getSimpleName();
    private BriteDatabase briteDatabase;

    public HomePresenter(BriteDatabase briteDatabase) {
        this.briteDatabase = briteDatabase;
    }

    @Override
    public void bindIntent() {
        initData();
        initQuery();
    }

    private void initData() {
        briteDatabase
                .createQuery(CommodStopType.TABLE, CommodStopType.QUERY)
                .mapToList(CommodStopType::mapper)
                .flatMap(commodStopTypes -> Observable.fromIterable(commodStopTypes))
                .subscribe(commodStopType -> {},throwableConsumer);
    }

    private void initQuery() {
        Observable.interval(BuildConfig.updateTime, TimeUnit.SECONDS, Schedulers.io())
                .flatMap(aLong -> briteDatabase.createQuery(CommodStop.QUERY_TABLES, CommodStop.QUERY).mapToList(CommodStop::mapper))
                .flatMap(commodStops -> Observable.fromIterable(commodStops))
                .flatMap(commodStop -> cityBusService.getBusEstimateTime(
                        commodStop.busRoute().getCityName().getEn(), getRemindQuery(commodStop)))
                .subscribe(routeStop -> {
                }, throwableConsumer);
    }

}
