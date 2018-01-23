package com.tigerbus.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.tigerbus.BuildConfig;
import com.tigerbus.TigerApplication;
import com.tigerbus.base.log.TlogType;
import com.tigerbus.data.CityBusInterface;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.sqlite.data.CommodStop;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public final class RemindService extends Service implements CityBusInterface {

    private final static String TAG = RemindService.class.getSimpleName();
    private final HashMap<String, CommodStop> routeStops = new HashMap<>();
    private final RemindBinder remindBinder = new RemindBinder();

    @Override
    public IBinder onBind(Intent arg0) {
        return remindBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
    }

    private void initData() {
        Observable.interval(BuildConfig.updateTime, TimeUnit.SECONDS, Schedulers.io())
                .flatMap(aLong -> Observable.fromIterable(routeStops.values()))
                .flatMap(commodStop -> Observable.zip(
                        cityBusService.getBusEstimateTime(
                                commodStop.busRoute().getCityName().getEn(), getRemindQuery(commodStop)),
                        Observable.just(commodStop),
                        (busEstimateTimes, commodStopS) -> {
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(BUS_ESTIMATE_TIME, busEstimateTimes.get(0));
                            bundle.putSerializable(BUS_ROUTESTOP, commodStopS);
                            return bundle;
                        })
                )
                .filter(bundle -> {
                    BusEstimateTime busEstimateTime = bundle.getParcelable(BUS_ESTIMATE_TIME);
                    return busEstimateTime.getEstimateTime() < 180;
                })
                .subscribe(bundle -> {
                    CommodStop commodStop = (CommodStop) bundle.getSerializable(BUS_ROUTESTOP);
                    routeStops.remove(CommodStop.getKey(commodStop));
                    sendNotification();
                }, throwable -> TigerApplication.printLog(TlogType.error, TAG, throwable.toString()));
    }


    private void sendNotification() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TigerApplication.printLog(TlogType.debug, TAG, "onStartCommand");
        if (intent.hasExtra(BUS_ROUTESTOP)) {
            CommodStop commodStop = (CommodStop) intent.getSerializableExtra(BUS_ROUTESTOP);
            routeStops.put(CommodStop.getKey(commodStop), commodStop);
            TigerApplication.printLog(TlogType.debug, TAG, TigerApplication.object2String(commodStop));
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public class RemindBinder extends Binder {
        public RemindService getService() {
            return RemindService.this;
        }
    }
}
