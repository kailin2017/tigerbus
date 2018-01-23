package com.tigerbus.ui.main.sub;

import com.squareup.sqlbrite3.BriteDatabase;
import com.tigerbus.BuildConfig;
import com.tigerbus.TigerApplication;
import com.tigerbus.base.BasePresenter;
import com.tigerbus.base.log.TlogType;
import com.tigerbus.data.CityBusInterface;
import com.tigerbus.sqlite.api.CommodStopTypeApi;
import com.tigerbus.sqlite.data.CommodStop;
import com.tigerbus.sqlite.data.CommodStopType;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public final class HomePresenter extends BasePresenter<HomeView>
        implements CityBusInterface, CommodStopTypeApi {

    private final static String TAG = HomePresenter.class.getSimpleName();
    private BriteDatabase briteDatabase;

    public HomePresenter(BriteDatabase briteDatabase) {
        this.briteDatabase = briteDatabase;
        initCommodStopTypes(briteDatabase);
    }

    @Override
    public void bindIntent() {
        initCommodStopTypes(briteDatabase);
        initCommodStop();
    }

    private void initCommodStop() {
        Observable.interval(0, BuildConfig.updateTime, TimeUnit.SECONDS, Schedulers.io())
                .doOnSubscribe(this::addDisposable)
                .flatMap(aLong -> briteDatabase.createQuery(CommodStop.QUERY_TABLES, CommodStop.QUERY).mapToList(CommodStop::mapper))
                .flatMap(commodStops -> Observable.fromIterable(commodStops))
                .flatMap(commodStop -> cityBusService.getBusEstimateTime(
                        commodStop.busRoute().getCityName().getEn(), getRemindQuery(commodStop)))
                .subscribe(busEstimateTimes -> {
                }, throwableConsumer);
    }

    @Override
    public void initCommodStopTypes(List<CommodStopType> commodStopTypes) {
        TigerApplication.setCommodStopTypes(commodStopTypes);
    }

    @Override
    public void initCommodStopTypes(Throwable throwable) {
        TigerApplication.printLog(TlogType.wtf, TAG, throwable.toString());
    }
}
