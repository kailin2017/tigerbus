package com.tigerbus.service;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.squareup.sqlbrite3.BriteDatabase;
import com.tigerbus.BuildConfig;
import com.tigerbus.TigerApplication;
import com.tigerbus.base.log.TlogType;
import com.tigerbus.data.CityBusInterface;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.sqlite.BriteApi;
import com.tigerbus.sqlite.BriteDB;
import com.tigerbus.sqlite.data.RemindStop;
import com.tigerbus.sqlite.data.WeekStatus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public final class RemindService extends Service implements CityBusInterface {

    private final static String TAG = RemindService.class.getSimpleName();
    private final RemindBinder remindBinder = new RemindBinder();
    private Disposable disposable;
    private BriteDatabase briteDatabase;

    @Override
    public IBinder onBind(Intent arg0) {
        return remindBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        briteDatabase = BriteDB.getInstance(getApplication());
        initBind();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (!disposable.isDisposed())
//            initBind();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    private void initBind() {
//        Observable.interval(BuildConfig.firstTime, BuildConfig.updateTime, TimeUnit.SECONDS, Schedulers.io())
//                .doOnSubscribe(this::doOnSubscribe)
//                .flatMap(this::flatMap1)
//                .flatMap(this::flatMap2)
//                .flatMap(this::flatMap3)
//                .filter(this::filter1)
//                .filter(this::filter2)
//                .filter(this::filter3)
//                .subscribe(this::onNext, this::onError, this::onComplete);
    }

    private void doOnSubscribe(Disposable disposable) {
        this.disposable = disposable;
    }

    private Observable<ArrayList<RemindStop>> flatMap1(long aLong) {
        Calendar calendar = Calendar.getInstance(Locale.TAIWAN);
        WeekStatus.Week week = WeekStatus.Week.int2Week(calendar.get(Calendar.DAY_OF_WEEK));
        String query1 = RemindStop.QUERY1;
        String query2 = String.format(RemindStop.QUERY2, week.getWeek(), BriteApi.BOOLEAN_TRUE);
        Observable observable = Observable.zip(
                briteDatabase.createQuery(RemindStop.QUERY_TABLES, query1).mapToList(RemindStop::mapper),
                briteDatabase.createQuery(RemindStop.QUERY_TABLES, query2).mapToList(RemindStop::mapper),
                (remindStops1, remindStops2) -> {
                    List<RemindStop> remindStops = new ArrayList<>();
                    remindStops.addAll(remindStops1);
                    remindStops.addAll(remindStops2);
                    return remindStops1;
                }
        );
        return observable;
    }

    private Observable<RemindStop> flatMap2(List<RemindStop> remindStops) {
        if (remindStops.size() == 0)
            disposable.dispose();
        return Observable.fromIterable(remindStops);
    }

    private Observable<Bundle> flatMap3(RemindStop remindStop) {
        return Observable.zip(
                cityBusService.getBusEstimateTime(
                        remindStop.routeStop().busRoute().getCityName().getEn(),
                        getRemindQuery(remindStop.routeStop())),
                Observable.just(remindStop),
                (busEstimateTimes, remindStop1) -> {
                    Bundle bundle = new Bundle();
                    if (busEstimateTimes.size() > 0) {
                        bundle.putParcelable(BUS_ESTIMATE_TIME, busEstimateTimes.get(0));
                    }
                    bundle.putParcelable(BUS_ROUTESTOP, remindStop1);
                    return bundle;
                });
    }

    private boolean filter1(Bundle bundle) {
        return bundle.getParcelable(BUS_ESTIMATE_TIME) != null;
    }

    private boolean filter2(Bundle bundle) {
        boolean result = false;
        BusEstimateTime busEstimateTime = bundle.getParcelable(BUS_ESTIMATE_TIME);
        RemindStop remindStop = bundle.getParcelable(BUS_ROUTESTOP);
        if (busEstimateTime.getEstimateTime() < remindStop.remindMinute()) {
            TigerApplication.sendNotification();
            result = true;
        }
        return result;
    }

    private boolean filter3(Bundle bundle) {
        boolean result = false;
        RemindStop remindStop = bundle.getParcelable(BUS_ROUTESTOP);
        if (remindStop.isOne()) {
            result = true;
        }
        return result;
    }

    private void onNext(Bundle bundle) {
        RemindStop remindStop = bundle.getParcelable(BUS_ROUTESTOP);
        ContentValues contentValues = new RemindStop.SqlBuilder().isRun(false).build();
        briteDatabase.update(RemindStop.TABLE, SQLiteDatabase.CONFLICT_FAIL,
                contentValues, RemindStop.ID + "= ? ", String.valueOf(remindStop.id()));
    }

    private void onError(Throwable throwable) {
        TigerApplication.printLog(TlogType.wtf, TAG, throwable.toString());
    }

    private void onComplete() {

    }

    public class RemindBinder extends Binder {
        public RemindService getService() {
            return RemindService.this;
        }
    }
}
