package com.tigerbus.ui.route.arrival;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.MotionEvent;

import com.squareup.sqlbrite3.BriteDatabase;
import com.tigerbus.TigerApplication;
import com.tigerbus.data.autovalue.BusA2DataListAutoValue;
import com.tigerbus.data.bus.BusA2Data;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusStopOfRoute;
import com.tigerbus.data.bus.BusSubRoute;
import com.tigerbus.sqlite.data.CommonStop;
import com.tigerbus.sqlite.data.CommonStopType;
import com.tigerbus.sqlite.data.RemindStop;
import com.tigerbus.sqlite.data.RouteStop;
import com.tigerbus.sqlite.data.WeekStatus;
import com.tigerbus.ui.route.adapter.ArrivalRecyclerAdapter;
import com.tigerbus.ui.widget.PagerRecyclerObj;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;

public final class ArrivalMainPresenter extends ArrivalPresenter<ArrivalMainView> {

    private PublishSubject<BusA2DataListAutoValue> publishSubject = PublishSubject.create();
    private RouteStop routeStop;
    private BriteDatabase briteDatabase;
    private Context context;

    public ArrivalMainPresenter(BriteDatabase briteDatabase, Context context) {
        this.briteDatabase = briteDatabase;
        this.context = context;
    }

    @Override
    protected void initSuccess() {
        publishSubject.doOnSubscribe(this::addDisposable);
        ArrayList<PagerRecyclerObj> pagerRecyclerObjs = new ArrayList<>();
        for (BusSubRoute subRoute : busRoute.getSubRoutes()) {
            BusStopOfRoute busStopOfRoute = busStopOfRouteMap.get(getKey(subRoute));

            ArrivalRecyclerAdapter arrivalRecyclerAdapter = new ArrivalRecyclerAdapter(busRoute, subRoute, busStopOfRoute, publishSubject);
            pagerRecyclerObjs.add(new PagerRecyclerObj(getTitle(context, busRoute, subRoute), arrivalRecyclerAdapter, context));
            arrivalRecyclerAdapter.getClickSubject().doOnSubscribe(this::addDisposable).subscribe(routeStop -> {
                ArrivalMainPresenter.this.routeStop = routeStop;
                getView().showBottomSheet();
            });
        }
        render(ArrivalViewState.Success.create(pagerRecyclerObjs));
    }

    @Override
    public void bindIntent() {
        super.bindIntent();
        getView().bindOnTimeData().flatMap(this::startInterval)
                .flatMap(this::loadBusA2Datas).subscribe(this::pushBusA2Datas, this::throwable);

        getView().bindClickRemind().doOnSubscribe(this::addDisposable).subscribe(this::bindClickRemind);
        getView().bindClickStationSave().doOnSubscribe(this::addDisposable).subscribe(this::bindClickStationSave);
        getView().bindClickStationLocation().doOnSubscribe(this::addDisposable).subscribe(this::bindClickStationLocation);
        getView().bindClickStationView().doOnSubscribe(this::addDisposable).subscribe(this::bindClickStationView);
        getView().bindTouchPager().doOnSubscribe(this::addDisposable).subscribe(this::bindTouchPager);
        getView().bindTypeList().doOnSubscribe(this::addDisposable).subscribe(this::bindTypeList);
    }

    private Observable<BusA2DataListAutoValue> loadBusA2Datas(long l) {
        return rxSwitchThread(Observable.zip(
                cityBusService.getBusEstimateTime(cityNameEn, routeUID),
                cityBusService.getBusA2Data(cityNameEn, routeUID),
                (busEstimateTimes, busA2Data) -> BusA2DataListAutoValue.create(busA2Data, busEstimateTimes)
        ).doOnSubscribe(this::addDisposable));
    }

    private void pushBusA2Datas(BusA2DataListAutoValue busA2DataListAutoValue) {
        publishSubject.onNext(busA2DataListAutoValue);
    }

    private void bindClickRemind(Object o) {
        insertRemindStop();
        getView().hideBottomSheet();
    }

    private void bindClickStationSave(Object o) {
        getView().showTypeList();
        getView().hideBottomSheet();
    }

    private void bindClickStationLocation(Object o) {
        getView().goArrivalMap(routeStop);
        getView().hideBottomSheet();

    }

    private void bindClickStationView(Object o) {
        getView().hideBottomSheet();
    }

    private void bindTouchPager(MotionEvent motionEvent) {
        getView().hideBottomSheet();
    }

    private void bindTypeList(int i) {
        CommonStopType commonStopType = TigerApplication.getCommodStopTypes().get(i);
        if (commonStopType == null) {
            getView().showAddType();
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

    public void insertCommmonStopType(String typename) {
        ContentValues contentValues = new CommonStopType.SqlBuilder().type(typename).build();
        insert(CommonStopType.TABLE, contentValues);
        insertCommonStop(TigerApplication.getCommodStopTypes().get(TigerApplication.getCommodStopTypes().size()));
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
