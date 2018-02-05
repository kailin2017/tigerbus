package com.tigerbus.ui.route.arrival;

import android.view.MotionEvent;

import io.reactivex.Observable;

public interface ArrivalMainView extends ArrivalView {

    Observable<Object> bindClickRemind();

    Observable<Object> bindClickStationSave();

    Observable<Object> bindClickStationAllBus();

    Observable<Object> bindClickStationLocation();

    Observable<Object> bindClickStationView();

    Observable<MotionEvent> bindTouchPager();

    Observable<Integer> bindTypeList();

    void hideBottomSheet();

    void showBottomSheet();

    void showTypeList();

}
