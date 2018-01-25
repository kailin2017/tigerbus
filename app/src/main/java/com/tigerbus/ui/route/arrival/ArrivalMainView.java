package com.tigerbus.ui.route.arrival;


import android.view.MotionEvent;

import com.tigerbus.sqlite.data.RouteStop;

import io.reactivex.Observable;

public interface ArrivalMainView extends ArrivalView {

    Observable<Object> bindClickRemind();

    Observable<Object> bindClickStationSave();

    Observable<Object> bindClickSataionAllBus();

    Observable<Object> bindClickStationLocation();

    Observable<Object> bindClickStationView();

    Observable<MotionEvent> bindTouchPager();

    Observable<RouteStop> bindSaveStation();

    Observable<Integer> bindTypeList();

    void bindService(RouteStop commonStop);

    void hiddenSheet();

    void showTypeList();
}
