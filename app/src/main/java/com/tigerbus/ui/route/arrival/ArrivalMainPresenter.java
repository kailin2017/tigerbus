package com.tigerbus.ui.route.arrival;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.squareup.sqlbrite3.BriteDatabase;
import com.tigerbus.sqlite.data.CommodStop;

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
    }

    private void bindClickRemind(Object o) {
        getView().bindService(commodStop);
    }

    private void bindClickStationSave(Object o) {
        ContentValues contentValues = new CommodStop.SqlBuilder().init(commodStop).type("").build();
        insert(CommodStop.TABLE, contentValues);
    }

    private void bindClickSataionAllBus(Object o) {
    }

    private void bindClickStationLocation(Object o) {
    }

    private void bindClickStationView(Object o) {
    }

    private void bindSaveStation(CommodStop commodStop) {
        this.commodStop = commodStop;
    }

    private void insert(String tableName, ContentValues contentValues) {
        briteDatabase.insert(tableName, SQLiteDatabase.CONFLICT_NONE, contentValues);
    }

}
