package com.tigerbus.ui.route.arrival;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.MotionEvent;

import com.squareup.sqlbrite3.BriteDatabase;
import com.tigerbus.TigerApplication;
import com.tigerbus.sqlite.data.CommonStop;
import com.tigerbus.sqlite.data.CommonStopType;
import com.tigerbus.sqlite.data.RemindStop;
import com.tigerbus.sqlite.data.RouteStop;
import com.tigerbus.sqlite.data.WeekStatus;

import io.reactivex.Observable;

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
        insertRemindStop();
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
            insertCommonStop(commonStopType);
        }
    }

    private void insert(String tableName, ContentValues contentValues) {
        briteDatabase.insert(tableName, SQLiteDatabase.CONFLICT_FAIL, contentValues);
    }

    private void insertCommonStop(CommonStopType commonStopType) {
        insertRouteStop();
        ContentValues contentValues =
                new CommonStop.SqlBuilder().routeStop(routeStop.id()).type(commonStopType.id()).build();
        insert(CommonStop.TABLE, contentValues);
    }

    private void insertCommmonStopType(String typename) {
        ContentValues contentValues = new CommonStopType.SqlBuilder().type(typename).build();
        insert(CommonStopType.TABLE, contentValues);
    }

    private void insertRouteStop() {
        String query = String.format(RouteStop.QUQRYID, RouteStop.getKey(routeStop));
        briteDatabase.createQuery(RouteStop.TABLE, query).mapToList(RouteStop::mapper)
                .subscribeOn(threadIO())
                .flatMap(routeStops -> Observable.just(routeStops))
                .filter(routeStops -> routeStops.size() == 0)
                .subscribe(routeStops -> {
                    ContentValues contentValues = new RouteStop.SqlBuilder().routeStop(routeStop).Build();
                    insert(RouteStop.TABLE, contentValues);
                });
    }

    private void insertRemindStop() {
        insertRouteStop();
        String weekId = insertWeekStutas();
        String routeStopId = routeStop.id();
        ContentValues contentValues = new RemindStop.SqlBuilder().isRun(true).isOne(true)
                .remindMinute(3).weekStatus(weekId).routeStop(routeStopId).build();
        insert(RemindStop.TABLE, contentValues);
    }

    private String insertWeekStutas() {
        WeekStatus weekStatus = WeekStatus.create();
        ContentValues contentValues = new WeekStatus.SqlBuilder().weekStatus(weekStatus).build();
        insert(WeekStatus.TABLE, contentValues);
        return weekStatus.id();
    }



}
