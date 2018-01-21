package com.tigerbus.ui.route.arrival;

import com.tigerbus.TigerApplication;
import com.tigerbus.data.bus.RouteStop;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.detail.Stop;

public final class ArrivalMainPresenter extends ArrivalPresenter<ArrivalMainView> {

    private RouteStop routeStop;

    @Override
    public void bindIntent() {
        super.bindIntent();
        getView().bindClickRemind().doOnSubscribe(this::addDisposable).subscribe(this::bindClickRemind);
        getView().bindClickStationSave().doOnSubscribe(this::addDisposable).subscribe(this::bindClickStationSave);
        getView().bindClickSataionAllBus().doOnSubscribe(this::addDisposable).subscribe(this::bindClickSataionAllBus);
        getView().bindClickStationLocation().doOnSubscribe(this::addDisposable).subscribe(this::bindClickStationLocation);
        getView().bindClickStationView().doOnSubscribe(this::addDisposable).subscribe(this::bindClickStationView);
        getView().bindSaveStation().doOnSubscribe(this::addDisposable).subscribe(this::bindSaveStation);
    }

    private void bindClickRemind(Object o) {
        getView().bindService(routeStop);
    }

    private void bindClickStationSave(Object o) {
        TigerApplication.commodStopAdd(routeStop);
    }

    private void bindClickSataionAllBus(Object o) {
    }

    private void bindClickStationLocation(Object o) {
    }

    private void bindClickStationView(Object o) {
    }

    private void bindSaveStation(RouteStop routeStop) {
        this.routeStop = routeStop;
    }

}
