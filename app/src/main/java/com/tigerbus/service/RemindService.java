package com.tigerbus.service;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;

import com.squareup.sqlbrite3.BriteDatabase;
import com.tigerbus.BuildConfig;
import com.tigerbus.R;
import com.tigerbus.TigerApplication;
import com.tigerbus.base.log.TlogType;
import com.tigerbus.data.CityBusInterface;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.notification.NotificationChannelType;
import com.tigerbus.sqlite.BriteDB;
import com.tigerbus.sqlite.data.RemindStop;
import com.tigerbus.sqlite.data.RemindStopInterface;

import java.lang.ref.SoftReference;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public final class RemindService extends Service implements CityBusInterface, RemindStopInterface {

    private final static String TAG = RemindService.class.getSimpleName();
    private final RemindBinder remindBinder = new RemindBinder();
    private PowerManager.WakeLock wakeLock;
    private SoftReference<List<RemindStop>> remindsReference;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private BriteDatabase briteDatabase;

    @Override
    public IBinder onBind(Intent arg0) {
        return remindBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        PowerManager powerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "");
        wakeLock.acquire();
        briteDatabase = BriteDB.getInstance(getApplication());
        compositeDisposable.add(initRemindStopRun(briteDatabase));
        initBind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        wakeLock.release();
        compositeDisposable.dispose();
    }

    private void initBind() {
        Observable.interval(BuildConfig.firstTime, BuildConfig.updateTime, TimeUnit.SECONDS, Schedulers.io())
                .doOnSubscribe(compositeDisposable::add)
                .flatMap(this::flatMap1).flatMap(this::flatMap2)
                .filter(this::filter1).filter(this::filter2).filter(this::filter3)
                .subscribe(this::onNext, this::onError, this::onComplete);
    }

    private Observable<RemindStop> flatMap1(long aLong) {
        if (remindsReference.get() == null) {
            compositeDisposable.add(initRemindStopRun(briteDatabase));
        }
        return Observable.fromIterable(remindsReference.get());
    }

    private Observable<Bundle> flatMap2(RemindStop remindStop) {
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
        if (busEstimateTime.getEstimateTime() < remindStop.remindMinute() * 60) {
            Context context = getApplicationContext();
            TigerApplication.sendNotification(context, NotificationChannelType.channel_remind, context.getString(R.string.notification_channel_remind),
                    String.format(context.getString(R.string.notification_channel_remind_msg),
                            remindStop.routeStop().busRoute().getRouteName().getZh_tw(),
                            remindStop.routeStop().stop().getStopName().getZh_tw()));
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
                contentValues, RemindStop.ID + "= ? ", remindStop.id());
    }

    private void onError(Throwable throwable) {
        TigerApplication.printLog(TlogType.wtf, TAG, throwable.toString());
    }

    private void onComplete() {

    }

    @Override
    public void initRemindStopResult(List<RemindStop> remindStops) {
        remindsReference = new SoftReference<>(remindStops);
    }

    @Override
    public void initRemindStopError(Throwable throwable) {

    }

    public class RemindBinder extends Binder {
        public RemindService getService() {
            return RemindService.this;
        }
    }
}
