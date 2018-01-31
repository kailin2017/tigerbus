package com.tigerbus.service;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.squareup.sqlbrite3.BriteDatabase;
import com.tigerbus.BuildConfig;
import com.tigerbus.R;
import com.tigerbus.TigerApplication;
import com.tigerbus.base.log.TlogType;
import com.tigerbus.data.CityBusInterface;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.notification.NotificationChannelType;
import com.tigerbus.sqlite.BriteApi;
import com.tigerbus.sqlite.BriteDB;
import com.tigerbus.sqlite.data.RemindStop;
import com.tigerbus.sqlite.data.WeekStatus;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public final class RemindService extends Service implements CityBusInterface {

    private final static String TAG = RemindService.class.getSimpleName();
    private final RemindBinder remindBinder = new RemindBinder();
    private SoftReference<ArrayList<RemindStop>> remindsReference;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
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
        initBind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private void initData() {
        Calendar calendar = Calendar.getInstance(Locale.TAIWAN);
        WeekStatus.Week week = WeekStatus.Week.int2Week(calendar.get(Calendar.DAY_OF_WEEK));
        String query1 = RemindStop.QUERY1;
        String query2 = String.format(RemindStop.QUERY2, week.getWeek(), BriteApi.BOOLEAN_TRUE);
        Observable
                .zip(
                        briteDatabase.createQuery(RemindStop.QUERY_TABLES, query1).mapToList(RemindStop::mapper),
                        briteDatabase.createQuery(RemindStop.QUERY_TABLES, query2).mapToList(RemindStop::mapper),
                        (remindStops1, remindStops2) -> {
                            ArrayList<RemindStop> remindStops = new ArrayList<>();
                            remindStops.addAll(remindStops1);
                            remindStops.addAll(remindStops2);
                            TigerApplication.printLog(TlogType.wtf, TAG, TigerApplication.object2String(remindStops));
                            return remindStops;
                        }
                )
                .doOnSubscribe(compositeDisposable::add)
                .subscribe(remindStops -> remindsReference = new SoftReference<>(remindStops));
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
            initData();
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
                            remindStop.routeStop().busRoute().getRouteName(),
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

    public class RemindBinder extends Binder {
        public RemindService getService() {
            return RemindService.this;
        }
    }
}
