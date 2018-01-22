package com.tigerbus.ui.main.sub;

import android.os.Bundle;

import com.tigerbus.BuildConfig;
import com.tigerbus.TigerApplication;
import com.tigerbus.base.BasePresenter;
import com.tigerbus.data.CityBusInterface;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kailin on 2018/1/21.
 */

public final class HomePresenter extends BasePresenter<HomeView> implements CityBusInterface {

    private final static String TAG = HomePresenter.class.getSimpleName();

    @Override
    public void bindIntent() {
        Observable.interval(BuildConfig.updateTime, TimeUnit.SECONDS, Schedulers.io())
                .flatMap(aLong -> Observable.fromIterable(TigerApplication.getRouteStopHashSet()))
                .flatMap(routeStop -> cityBusService.getBusEstimateTime(
                        routeStop.getBusRoute().getCityName().getEn(), getRemindQuery(routeStop)))
                .subscribe(routeStop -> {
                }, throwableConsumer);
    }


}
