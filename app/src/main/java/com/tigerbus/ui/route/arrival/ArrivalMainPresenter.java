package com.tigerbus.ui.route.arrival;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.squareup.sqlbrite3.BriteDatabase;
import com.tigerbus.TigerApplication;
import com.tigerbus.sqlite.data.CommodStop;
import com.tigerbus.sqlite.data.CommodStopType;

public final class ArrivalMainPresenter extends ArrivalPresenter<ArrivalMainView> {

    private CommodStop commodStop;
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
        getView().bindSaveStation().doOnSubscribe(this::addDisposable).subscribe(this::bindSaveStation);
        getView().bindTypeList().doOnSubscribe(this::addDisposable).subscribe(this::bindTypeList);
    }

    private void bindClickRemind(Object o) {
        getView().bindService(commodStop);
        getView().showTypeList();
        getView().hiddenSheet();
    }

    private void bindClickStationSave(Object o) {

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

    private void bindSaveStation(CommodStop commodStop) {
        this.commodStop = commodStop;
    }

    private void bindTypeList(int i) {
        CommodStopType commodStopType = TigerApplication.getCommodStopTypes().get(i);
        ContentValues contentValues = new CommodStop.SqlBuilder().init(commodStop).type(commodStopType.type()).build();
        insert(CommodStop.TABLE, contentValues);
    }

    private void insert(String tableName, ContentValues contentValues) {
        briteDatabase.insert(tableName, SQLiteDatabase.CONFLICT_NONE, contentValues);
    }

}
