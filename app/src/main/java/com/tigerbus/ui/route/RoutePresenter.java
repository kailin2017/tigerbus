package com.tigerbus.ui.route;

import android.os.Bundle;

import com.tigerbus.BuildConfig;
import com.tigerbus.base.BasePresenter;
import com.tigerbus.connection.RetrofitModel;
import com.tigerbus.data.CityBusInterface;
import com.tigerbus.data.CityBusService;
import com.tigerbus.data.autovalue.BusA1DataListAutoValue;
import com.tigerbus.data.autovalue.BusA2DataListAutoValue;
import com.tigerbus.data.bus.BusA2Data;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public final class RoutePresenter extends BasePresenter<RouteView> implements CityBusInterface {

    private String cityNameEn, routeUID;

    @Override
    public void bindIntent() {
        Observable<BusRoute> stopOfRouteSubject = getView().bindIntent();
        stopOfRouteSubject.doOnSubscribe(this::addUiDisposable).subscribe(this::initData);
    }

    private void initData(BusRoute busRoute) {
        cityNameEn = busRoute.getCityName().getEn();
        routeUID = getRoureUIDQuery(busRoute.getRouteUID());
        Observable.zip(
                cityBusService.getBusStopOfRoute(cityNameEn, routeUID),
                cityBusService.getShape(cityNameEn, routeUID),
                (busStopOfRoutes, busShapes) -> {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(BUS_ROUTE, busRoute);
                    bundle.putParcelableArrayList(BUS_STOP_OF_ROUTE, busStopOfRoutes);
                    bundle.putParcelableArrayList(BUS_SHAPE, busShapes);
                    return bundle;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bundle -> render(RouteViewState.Success.create(bundle)), this::throwable);
    }
}
