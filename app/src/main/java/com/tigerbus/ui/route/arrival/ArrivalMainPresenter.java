package com.tigerbus.ui.route.arrival;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.MotionEvent;

import com.squareup.sqlbrite3.BriteDatabase;
import com.tigerbus.TigerApplication;
import com.tigerbus.sqlite.data.CommonStopType;
import com.tigerbus.sqlite.data.CommonStops;
import com.tigerbus.sqlite.data.RouteStop;

public final class ArrivalMainPresenter extends ArrivalPresenter<ArrivalMainView> {

    private RouteStop routeStop;
    private BriteDatabase briteDatabase;

    public ArrivalMainPresenter(BriteDatabase briteDatabase) {
        this.briteDatabase = briteDatabase;
    }

    @Override
    public void bindIntent() {
        super.bindIntent();
        getView().bindClickRemind().doOnSubscribe(this::addDisposable).subscribe(this::bindClickRemind);
        getView().bindClickStationSave().doOnSubscribe(this::addDisposable).subscribe(this::bindClickStationSave);
        getView().bindClickSataionAllBus().doOnSubscribe(this::addDisposable).subscribe(this::bindClickSataionAllBus);
        getView().bindClickStationLocation().doOnSubscribe(this::addDisposable).subscribe(this::bindClickStationLocation);
        getView().bindClickStationView().doOnSubscribe(this::addDisposable).subscribe(this::bindClickStationView);
        getView().bindTouchPager().doOnSubscribe(this::addDisposable).subscribe(this::bindTouchPager);
        getView().bindSaveStation().doOnSubscribe(this::addDisposable).subscribe(this::bindSaveStation);
        getView().bindTypeList().doOnSubscribe(this::addDisposable).subscribe(this::bindTypeList);
    }

    private void bindClickRemind(Object o) {
        getView().bindService(routeStop);
        getView().hiddenSheet();
    }

    private void bindClickStationSave(Object o) {
        getView().showTypeList();
        getView().hiddenSheet();
    }

    private void bindClickSataionAllBus(Object o) {
        getView().hiddenSheet();
    }

    private void bindClickStationLocation(Object o) {
        getView().hiddenSheet();
    }

    private void bindClickStationView(Object o) {
        getView().hiddenSheet();
    }

    private void bindTouchPager(MotionEvent motionEvent) {
        getView().hiddenSheet();
    }

    private void bindSaveStation(RouteStop routeStop) {
        this.routeStop = routeStop;
    }

    private void bindTypeList(int i) {
        CommonStopType commonStopType = TigerApplication.getCommodStopTypes().get(i);
        if (commonStopType == null) {

        } else {
            ContentValues routeStopContentValues =
                    new RouteStop.SqlBuilder().routeStop(routeStop).Build();
            insert(RouteStop.TABLE, routeStopContentValues);

            ContentValues commonsStopContentValues =
                    new CommonStops.SqlBuilder().routeStop(routeStop.id()).type(commonStopType.id()).build();
            insert(CommonStops.TABLE, commonsStopContentValues);
        }
    }

    private void insert(String tableName, ContentValues contentValues) {
        briteDatabase.insert(tableName, SQLiteDatabase.CONFLICT_FAIL, contentValues);
    }

}
