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
import com.tigerbus.data.bus.RouteStop;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public final class RemindService extends Service implements CityBusInterface {

    private final static String TAG = RemindService.class.getSimpleName();
    private final HashMap<String, RouteStop> routeStops = new HashMap<>();
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
                .flatMap(routeStop -> Observable.zip(
                        cityBusService.getBusEstimateTime(
                                routeStop.getBusRoute().getCityName().getEn(), getRemindQuery(routeStop)),
                        Observable.just(routeStop),
                        (busEstimateTimes, routeStopZ) -> {
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(BUS_ESTIMATE_TIME, busEstimateTimes.get(0));
                            bundle.putParcelable(BUS_ROUTESTOP, routeStopZ);
                            return bundle;
                        })
                )
                .filter(bundle -> {
                    BusEstimateTime busEstimateTime = bundle.getParcelable(BUS_ESTIMATE_TIME);
                    return busEstimateTime.getEstimateTime() < 180;
                })
                .subscribe(bundle -> {
                    RouteStop routeStop = bundle.getParcelable(BUS_ROUTESTOP);
                    routeStops.remove(getKey(routeStop));
                    sendNotification();
                }, throwable -> TigerApplication.printLog(TlogType.error, TAG, throwable.toString()));
    }

    private String getKey(RouteStop routeStop) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(routeStop.getBusRoute().getRouteUID());
        stringBuffer.append(routeStop.getBusSubRoute().getSubRouteUID());
        stringBuffer.append(routeStop.getStop().getStopUID());
        return stringBuffer.toString();
    }

    private void sendNotification() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TigerApplication.printLog(TlogType.debug, TAG, "onStartCommand");
        if (intent.hasExtra(BUS_ROUTESTOP)) {
            RouteStop routeStop = intent.getParcelableExtra(BUS_ROUTESTOP);
            routeStops.put(getKey(routeStop), routeStop);
            TigerApplication.printLog(TlogType.debug, TAG, TigerApplication.object2String(routeStop));
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public class RemindBinder extends Binder {
        public RemindService getService() {
            return RemindService.this;
        }
    }
}
