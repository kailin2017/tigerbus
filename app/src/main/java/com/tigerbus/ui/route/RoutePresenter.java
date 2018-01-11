package com.tigerbus.ui.route;

import android.os.Bundle;

import com.tigerbus.base.BasePresenter;
import com.tigerbus.connection.RetrofitModel;
import com.tigerbus.data.CityBusService;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusStopOfRoute;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public final class RoutePresenter extends BasePresenter<RouteView> {

    private CityBusService cityBusService = RetrofitModel.getInstance().create(CityBusService.class);
    private Observable<BusRoute> stopOfRouteSubject;
    private Observable<Long> estimateTimeSubject;
    private Disposable disposable;

    @Override
    public void bindIntent() {
        stopOfRouteSubject = getView().bindStopOfRoute();
        addDisposable(stopOfRouteSubject.subscribe(busRoute -> initData(busRoute)));
//        estimateTimeSubject = Observable.interval(15, TimeUnit.SECONDS, Schedulers.io());
//        addDisposable(stopOfRouteSubject.subscribe(busRoute -> getBusStopOfRoute(busRoute)));
    }

    private void initData(BusRoute busRoute) {
        String cityNameEn = busRoute.getCityName().getEn();
        String routeName = busRoute.getRouteName().getZh_tw();
        stopOfRouteSubject = getView().bindStopOfRoute();
        Observable.zip(
                cityBusService.getBusStopOfRoute(cityNameEn, routeName),
                cityBusService.getBusEstimateTime(cityNameEn, routeName),
                (busStopOfRoutes, busEstimateTimes) -> {
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(cityBusService.BUS_STOP_OF_ROUTE, busStopOfRoutes);
                    bundle.putParcelableArrayList(cityBusService.BUS_ESTIMATE_TIME, busEstimateTimes);
                    return bundle;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bundle -> render(RouteViewState.Success.create(bundle)), throwableConsumer);
    }

//    private void getBusStopOfRoute(BusRoute busRoute) {
//        cityBusService.getBusStopOfRoute(
//                busRoute.getCityName().getEn(),
//                busRoute.getRouteName().getZh_tw())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(defaultDisposableConsumer)
//                .subscribe(
//                        busStopOfRoutes -> render(RouteViewState.Success.create(busStopOfRoutes)),
//                        throwableConsumer,
//                        () -> {
//                            if (disposable != null)
//                                removeDisposable(disposable);
//                            disposable = estimateTimeSubject.subscribe(aLong -> getBusEstimateTime(busRoute));
//                            addDisposable(disposable);
//                        });
//    }
//
//    private void getBusEstimateTime(BusRoute busRoute) {
//        cityBusService.getBusEstimateTime(
//                busRoute.getCityName().getEn(),
//                busRoute.getRouteName().getZh_tw())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(defaultDisposableConsumer)
//                .subscribe(
//                        busEstimateTimes -> render(RouteViewState.Success.create(busEstimateTimes)),
//                        throwableConsumer);
//}
}
