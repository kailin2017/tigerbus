package com.tigerbus.sqlite.data;

import android.provider.BaseColumns;

import com.squareup.sqlbrite3.BriteDatabase;
import com.tigerbus.TigerApplication;
import com.tigerbus.base.log.TlogType;
import com.tigerbus.sqlite.BriteApi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public interface RemindStopInterface {

    String TAG = RemindStopInterface.class.getSimpleName();

    default Disposable initRemindStopRun(BriteDatabase briteDatabase) {
        Calendar calendar = Calendar.getInstance(Locale.TAIWAN);
        WeekStatus.Week week = WeekStatus.Week.int2Week(calendar.get(Calendar.DAY_OF_WEEK));
        String query1 = RemindStop.QUERY1;
        String query2 = String.format(RemindStop.QUERY2, week.getWeek(), BriteApi.BOOLEAN_TRUE);
        return Observable
                .merge(briteDatabase.createQuery(RemindStop.QUERY_TABLES, query1).mapToList(RemindStop::mapper),
                        briteDatabase.createQuery(RemindStop.QUERY_TABLES, query2).mapToList(RemindStop::mapper))
                .subscribe(this::initRemindStopResult, this::initRemindStopError);
    }

    default Disposable initRemindStopAll(BriteDatabase briteDatabase) {
        return briteDatabase.createQuery(RemindStop.QUERY_TABLES, RemindStop.QUERY_ALL)
                .mapToList(RemindStop::mapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::initRemindStopResult, this::initRemindStopError);
    }

    void initRemindStopResult(List<RemindStop> remindStops);

    void initRemindStopError(Throwable throwable);

}
