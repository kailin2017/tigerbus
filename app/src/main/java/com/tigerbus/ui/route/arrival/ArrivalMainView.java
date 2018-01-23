package com.tigerbus.ui.route.arrival;


import com.tigerbus.sqlite.data.CommodStop;

import io.reactivex.Observable;

public interface ArrivalMainView extends ArrivalView {

    Observable<Object> bindClickRemind();

    Observable<Object> bindClickStationSave();

    Observable<Object> bindClickSataionAllBus();

    Observable<Object> bindClickStationLocation();

    Observable<Object> bindClickStationView();

    Observable<CommodStop> bindSaveStation();

    void bindService(CommodStop commodStop);

    void hiddenSheet();
}
