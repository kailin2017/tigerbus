package com.tigerbus.ui.route.arrival;


import com.tigerbus.data.bus.RouteStop;
import com.tigerbus.data.detail.Stop;

import io.reactivex.Observable;

/**
 * Created by Kailin on 2018/1/20.
 */

public interface ArrivalMainView extends ArrivalView {

    Observable<Object> bindClickRemind();

    Observable<Object> bindClickStationSave();

    Observable<Object> bindClickSataionAllBus();

    Observable<Object> bindClickStationLocation();

    Observable<Object> bindClickStationView();

    Observable<Stop> bindSaveStation();

    void bindService(RouteStop routeStop);

    void hiddenSheet();
}
