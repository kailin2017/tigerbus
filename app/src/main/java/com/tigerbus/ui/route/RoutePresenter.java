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

    private PublishSubject<ArrayList<BusEstimateTime>> publishSubject;
    private PublishSubject<Bundle> busA1Data;
    private PublishSubject<Bundle> busA2Data;
    private String cityNameEn, routeUID;

    public RoutePresenter(PublishSubject<ArrayList<BusEstimateTime>> publishSubject) {
        this.publishSubject = publishSubject;
    }

    @Override
    public void bindIntent() {
        Observable<BusRoute> stopOfRouteSubject = getView().bindIntent();
        addUiDisposable(stopOfRouteSubject.subscribe(this::initData));
        addUiDisposable(Observable.interval(BuildConfig.updateTime, TimeUnit.SECONDS, Schedulers.io()).subscribe(aLong -> initEstimateTime()));
    }

    private void initData(BusRoute busRoute) {
        cityNameEn = busRoute.getCityName().getEn();
        routeUID = getRoureUIDQuery(busRoute.getRouteUID());
        Observable.zip(
                cityBusService.getBusStopOfRoute(cityNameEn, routeUID),
                cityBusService.getBusEstimateTime(cityNameEn, routeUID),
                cityBusService.getShape(cityNameEn,routeUID),
                (busStopOfRoutes, busEstimateTimes,busShapes) -> {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(BUS_ROUTE, busRoute);
                    bundle.putParcelableArrayList(BUS_STOP_OF_ROUTE, busStopOfRoutes);
                    bundle.putParcelableArrayList(BUS_ESTIMATE_TIME, busEstimateTimes);
                    bundle.putParcelableArrayList(BUS_SHAPE,busShapes);
                    return bundle;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bundle -> render(RouteViewState.Success.create(bundle)), this::throwable);
    }

    private void initEstimateTime() {
        rxSwitchThread(getEstimateTime()).subscribe(publishSubject::onNext);
    }

    private void initA1Data(){
        Observable.zip(
                getEstimateTime(),
                cityBusService.getBusA1Data(cityNameEn, routeUID),
                (busEstimateTimes, busA1Data) -> {
                    BusA1DataListAutoValue busA1DataAutoValue =
                            BusA1DataListAutoValue.create(busA1Data,busEstimateTimes);
                    return Observable.just(busA1DataAutoValue);
                }
        );
    }

    private void initA2Data(){
        Observable.zip(
                getEstimateTime(),
                cityBusService.getBusA2Data(cityNameEn, routeUID),
                (busEstimateTimes, busA2Data) -> {
                    BusA2DataListAutoValue busA2DataAutoValue =
                            BusA2DataListAutoValue.create(busA2Data,busEstimateTimes);
                    return busA2DataAutoValue;
                }
        )
        .flatMap(busA2DataListAutoValue -> {
            ArrayList<BusEstimateTime> busEstimateTimes = busA2DataListAutoValue.busEstimateTimes();
            ArrayList<BusA2Data> busA2Data = busA2DataListAutoValue.busA2Datas();
            return Observable.just(busA2Data);
        });
    }

    private Observable<ArrayList<BusEstimateTime>> getEstimateTime(){
        return cityBusService.getBusEstimateTime(cityNameEn,routeUID);
    }


}
