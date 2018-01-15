package com.tigerbus.ui.route;

import android.os.Bundle;

import com.tigerbus.base.BasePresenter;
import com.tigerbus.connection.RetrofitModel;
import com.tigerbus.data.CityBusService;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public final class RoutePresenter extends BasePresenter<RouteView> {

    private PublishSubject<ArrayList<BusEstimateTime>> publishSubject;
    private CityBusService cityBusService = RetrofitModel.getInstance().create(CityBusService.class);
    private String cityNameEn, routeUID;

    public RoutePresenter(PublishSubject<ArrayList<BusEstimateTime>> publishSubject) {
        this.publishSubject = publishSubject;
    }

    @Override
    public void bindIntent() {
        Observable<BusRoute> stopOfRouteSubject = getView().bindIntent();
        addUiDisposable(stopOfRouteSubject.subscribe(this::initData));
        addUiDisposable(Observable.interval(15, TimeUnit.SECONDS, Schedulers.io()).subscribe(aLong -> initEstimateTime()));
    }

    private void initData(BusRoute busRoute) {
        cityNameEn = busRoute.getCityName().getEn();
        routeUID = CityBusService.getRoureUIDQuery(busRoute.getRouteUID());
        Observable.zip(
                cityBusService.getBusStopOfRoute(cityNameEn, routeUID),
                cityBusService.getBusEstimateTime(cityNameEn, routeUID),
                (busStopOfRoutes, busEstimateTimes) -> {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(cityBusService.BUS_ROUTE, busRoute);
                    bundle.putParcelableArrayList(cityBusService.BUS_STOP_OF_ROUTE, busStopOfRoutes);
                    bundle.putParcelableArrayList(cityBusService.BUS_ESTIMATE_TIME, busEstimateTimes);
                    return bundle;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bundle -> render(RouteViewState.Success.create(bundle)), throwableConsumer);
    }

    private void initEstimateTime() {
        rxSwitchThread(cityBusService.getBusEstimateTime(cityNameEn, routeUID)).subscribe(publishSubject::onNext);
    }


}
