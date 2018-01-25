package com.tigerbus.service;

import android.app.Service;
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
import com.tigerbus.sqlite.BriteDB;
import com.tigerbus.sqlite.data.CommonStop;
import com.tigerbus.sqlite.data.WeekStatus;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public final class RemindService extends Service implements CityBusInterface {

    private final static String TAG = RemindService.class.getSimpleName();
    private final HashMap<String, CommonStop> routeStops = new HashMap<>();
    private final RemindBinder remindBinder = new RemindBinder();
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
                .subscribe(p -> TigerApplication.printLog(TlogType.wtf, TAG, "SUCCESS"));
//        Observable.interval(BuildConfig.firstTime, BuildConfig.updateTime, TimeUnit.SECONDS, Schedulers.io())
//                .flatMap(aLong -> Observable.fromIterable(routeStops.values()))
//                .flatMap(commodStop -> Observable.zip(
//                        cityBusService.getBusEstimateTime(
//                                commodStop.busRoute().getCityName().getEn(), getRemindQuery(commodStop)),
//                        Observable.just(commodStop),
//                        (busEstimateTimes, commodStopS) -> {
//                            Bundle bundle = new Bundle();
//                            bundle.putParcelable(BUS_ESTIMATE_TIME, busEstimateTimes.get(0));
//                            bundle.putParcelable(BUS_ROUTESTOP, commodStopS);
//                            return bundle;
//                        })
//                )
//                .filter(bundle -> {
//                    BusEstimateTime busEstimateTime = bundle.getParcelable(BUS_ESTIMATE_TIME);
//                    return busEstimateTime.getEstimateTime() < 180;
//                })
//                .subscribe(bundle -> {
//                    CommonStop commonStop = bundle.getParcelable(BUS_ROUTESTOP);
//                    routeStops.remove(CommonStop.getKey(commonStop));
//                    sendNotification();
//                }, throwable -> TigerApplication.printLog(TlogType.error, TAG, throwable.toString()));
    }

    private Observable<List<CommonStop>> flatMap1(long aLong) {
        return briteDatabase.createQuery(CommonStop.QUERY_TABLES, CommonStop.QUERY).mapToList(CommonStop::mapper);
    }

    private void sendNotification() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        TigerApplication.printLog(TlogType.debug, TAG, "onStartCommand");
//        if (intent.hasExtra(BUS_ROUTESTOP)) {
//            CommonStop commonStop = intent.getParcelableExtra(BUS_ROUTESTOP);
//            routeStops.put(CommonStop.getKey(commonStop), commonStop);
//            TigerApplication.printLog(TlogType.debug, TAG, TigerApplication.object2String(commonStop));
//        }
        return super.onStartCommand(intent, flags, startId);
    }

    public class RemindBinder extends Binder {
        public RemindService getService() {
            return RemindService.this;
        }
    }
}
