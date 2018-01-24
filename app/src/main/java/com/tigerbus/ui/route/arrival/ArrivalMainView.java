package com.tigerbus.ui.route.arrival;


import android.view.MotionEvent;

import com.tigerbus.sqlite.data.CommonStop;

import io.reactivex.Observable;

public interface ArrivalMainView extends ArrivalView {

    Observable<Object> bindClickRemind();

    Observable<Object> bindClickStationSave();

    Observable<Object> bindClickSataionAllBus();

    Observable<Object> bindClickStationLocation();

    Observable<Object> bindClickStationView();

    Observable<MotionEvent> bindTouchPager();

    Observable<CommonStop> bindSaveStation();

    Observable<Integer> bindTypeList();

    void bindService(CommonStop commonStop);

    void hiddenSheet();

    void showTypeList();
}
