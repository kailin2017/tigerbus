package com.tigerbus.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.tigerbus.BuildConfig;
import com.tigerbus.TigerApplication;
import com.tigerbus.base.log.TlogType;
import com.tigerbus.connection.RetrofitModel;
import com.tigerbus.data.CityBusService;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.RouteStop;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


public final class RemindService extends Service {

    private final static String TAG = RemindService.class.getSimpleName();
    private final static String EXTRA_ROUTESTOP = RouteStop.class.getSimpleName();
    private final static String EXTRA_ESTIMATE = CityBusService.BUS_ESTIMATE_TIME;
    private CityBusService cityBusService = RetrofitModel.getInstance().create(CityBusService.class);
    private HashMap<String, RouteStop> routeStops = new HashMap<>();
    private RemindBinder remindBinder = new RemindBinder();

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
                                routeStop.getBusRoute().getCityName().getEn(), CityBusService.getRemindQuery(routeStop)),
                        Observable.just(routeStop),
                        (busEstimateTimes, routeStopZ) -> {
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(EXTRA_ESTIMATE, busEstimateTimes.get(0));
                            bundle.putParcelable(EXTRA_ROUTESTOP, routeStopZ);
                            return bundle;
                        })
                )
                .subscribe(bundle -> {
                    BusEstimateTime busEstimateTime = bundle.getParcelable(EXTRA_ESTIMATE);
                    RouteStop routeStop = bundle.getParcelable(EXTRA_ROUTESTOP);
                    if (busEstimateTime.getEstimateTime() < 120) {
                        routeStops.remove(getKey(routeStop));
                        sendNotification();
                    }
                }, throwable -> TigerApplication.printLog(TlogType.error, TAG, throwable.toString()));
    }

    private void sendNotification() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TigerApplication.printLog(TlogType.debug, TAG, "onStartCommand");
        if (intent.hasExtra(EXTRA_ROUTESTOP)) {
            RouteStop routeStop = intent.getParcelableExtra(EXTRA_ROUTESTOP);
            routeStops.put(getKey(routeStop), routeStop);
            TigerApplication.printLog(TlogType.debug, TAG, TigerApplication.object2String(routeStop));
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private String getKey(RouteStop routeStop) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(routeStop.getBusRoute().getRouteUID());
        stringBuffer.append(routeStop.getBusSubRoute().getSubRouteUID());
        stringBuffer.append(routeStop.getStop().getStopUID());
        return stringBuffer.toString();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        TigerApplication.printLog(TlogType.debug, TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        TigerApplication.printLog(TlogType.debug, TAG, "onDestroy");
        super.onDestroy();
    }

    public class RemindBinder extends Binder {
        public RemindService getService() {
            return RemindService.this;
        }
    }
}
