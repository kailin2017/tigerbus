package com.tigerbus.ui.route.arrival;

import android.view.MotionEvent;

import com.tigerbus.sqlite.data.RouteStop;

import io.reactivex.Observable;

public interface ArrivalMainView extends ArrivalView {

    Observable<Object> bindClickRemind();

    Observable<Object> bindClickStationSave();

    Observable<Object> bindClickStationLocation();

    Observable<Object> bindClickStationView();

    Observable<MotionEvent> bindTouchPager();

    Observable<Integer> bindTypeList();

    void hideBottomSheet();

    void showBottomSheet();

    void showTypeList();

    void showAddType();

    void goArrivalMap(RouteStop routeStop);

}
