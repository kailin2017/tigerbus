package com.tigerbus.ui.route;

import com.tigerbus.base.BasePresenter;
import com.tigerbus.connection.RetrofitModel;
import com.tigerbus.data.CityBusService;
import com.tigerbus.data.bus.BusRoute;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public final class RoutePresenter extends BasePresenter<RouteView> {

    private CityBusService cityBusService = RetrofitModel.getInstance().create(CityBusService.class);
    private Observable<BusRoute> stopOfRouteSubject;
    private Observable<Long> estimateTimeSubject;
    private Disposable disposable;

    @Override
    public void bindIntent() {
        stopOfRouteSubject = getView().bindStopOfRoute();
        estimateTimeSubject = Observable.interval(15, TimeUnit.SECONDS, Schedulers.io());
        addDisposable(stopOfRouteSubject.subscribe(busRoute -> getBusStopOfRoute(busRoute)));
    }

    private void getBusStopOfRoute(BusRoute busRoute) {
        cityBusService.getBusStopOfRoute(
                busRoute.getCityName().getEn(),
                busRoute.getRouteName().getZh_tw())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(defaultDisposableConsumer)
                .subscribe(
                        busStopOfRoutes -> render(RouteViewState.Success.create(busStopOfRoutes)),
                        throwableConsumer,
                        () -> {
                            if (disposable != null)
                                removeDisposable(disposable);
                            disposable = estimateTimeSubject.subscribe(aLong -> getBusEstimateTime(busRoute));
                            addDisposable(disposable);
                        });
    }

    private void getBusEstimateTime(BusRoute busRoute) {
        cityBusService.getBusEstimateTime(
                busRoute.getCityName().getEn(),
                busRoute.getRouteName().getZh_tw())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(defaultDisposableConsumer)
                .subscribe(
                        busEstimateTimes -> render(RouteViewState.Success.create(busEstimateTimes)),
                        throwableConsumer);
    }
}
