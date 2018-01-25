package com.tigerbus.service;

import android.app.Fragment;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.squareup.sqlbrite3.BriteDatabase;
import com.tigerbus.BuildConfig;
import com.tigerbus.TigerApplication;
import com.tigerbus.base.log.TlogType;
import com.tigerbus.data.CityBusInterface;
import com.tigerbus.data.autovalue.HomePresenterAutoValue;
import com.tigerbus.sqlite.BriteApi;
import com.tigerbus.sqlite.BriteDB;
import com.tigerbus.sqlite.data.CommonStops;
import com.tigerbus.sqlite.data.RemindStop;
import com.tigerbus.sqlite.data.RouteStop;
import com.tigerbus.sqlite.data.WeekStatus;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public final class RemindService extends Service implements CityBusInterface {

    private final static String TAG = RemindService.class.getSimpleName();
    private final RemindBinder remindBinder = new RemindBinder();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private BriteDatabase briteDatabase;

    @Override
    public IBinder onBind(Intent arg0) {
        return remindBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        briteDatabase = BriteDB.getInstance(getApplication());

        initData();
    }

    private void initData() {
        Observable.interval(BuildConfig.firstTime, BuildConfig.updateTime, TimeUnit.SECONDS, Schedulers.io())
                .flatMap(this::flatMap1)
                .flatMap(this::flatMap2)
                .flatMap(this::flatMap3);

    }

    private Observable<List<RemindStop>> flatMap1(long aLong) {
        return briteDatabase.createQuery(RemindStop.QUERY_TABLES, RemindStop.QUERY).mapToList(RemindStop::mapper);
    }

    private Observable<RemindStop> flatMap2(List<RemindStop> remindStops) {
        return Observable.fromIterable(remindStops)
                .doOnSubscribe(compositeDisposable::add).doOnComplete(this::flatMap2Complete);
    }

    private void flatMap2Complete() {
    }

    private Observable<Bundle> flatMap3(RemindStop remindStop) {
        return Observable.zip(
                cityBusService.getBusEstimateTime(
                        remindStop.routeStop().busRoute().getCityName().getEn(),
                        getRemindQuery(remindStop.routeStop())),
                Observable.just(remindStop),
                (busEstimateTimes, remindStop1) -> {
                    Bundle bundle = new Bundle();
                    if (busEstimateTimes.size() > 0)
                        bundle.putParcelable(BUS_ESTIMATE_TIME, busEstimateTimes.get(0));
                    bundle.putParcelable(BUS_ROUTESTOP, remindStop1);
                    return bundle;
                });
    }


    private void sendNotification() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public class RemindBinder extends Binder {
        public RemindService getService() {
            return RemindService.this;
        }
    }
}
