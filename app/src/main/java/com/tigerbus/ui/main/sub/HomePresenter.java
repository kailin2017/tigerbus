package com.tigerbus.ui.main.sub;

import android.os.Bundle;

import com.squareup.sqlbrite3.BriteDatabase;
import com.tigerbus.BuildConfig;
import com.tigerbus.TigerApplication;
import com.tigerbus.base.BasePresenter;
import com.tigerbus.base.ViewState;
import com.tigerbus.base.log.TlogType;
import com.tigerbus.data.CityBusInterface;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.sqlite.data.CommodStopQueryResult;
import com.tigerbus.sqlite.data.CommonStopTypeApi;
import com.tigerbus.sqlite.data.CommonStop;
import com.tigerbus.sqlite.data.CommonStopType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public final class HomePresenter extends BasePresenter<HomeView>
        implements CityBusInterface, CommonStopTypeApi {

    private final static String TAG = HomePresenter.class.getSimpleName();
    private BriteDatabase briteDatabase;
    private ArrayList<CommodStopQueryResult> commodStopQueryResults = new ArrayList<>();

    public HomePresenter(BriteDatabase briteDatabase) {
        this.briteDatabase = briteDatabase;
        initCommodStopTypes(briteDatabase);
    }

    @Override
    public void bindIntent() {
        initCommodStopTypes(briteDatabase);
        initCommodStop();
    }

    private void initCommodStop() {
        Observable.interval(BuildConfig.firstTime, BuildConfig.updateTime, TimeUnit.SECONDS, Schedulers.io())
                .doOnSubscribe(this::addDisposable)
                .flatMap(this::flatMap1)
                .flatMap(this::flatMap2)
                .flatMap(this::flatMap3)
                .subscribe(bundle -> {
                    BusEstimateTime busEstimateTime = bundle.getParcelable(BUS_ESTIMATE_TIME);
                    CommonStop commonStop = bundle.getParcelable(BUS_ROUTESTOP);
                    commodStopQueryResults.add(CommodStopQueryResult.create(commonStop, busEstimateTime));
                }, this::throwable);
    }

    private Observable<List<CommonStop>> flatMap1(long aLong) {
        return briteDatabase.createQuery(CommonStop.QUERY_TABLES, CommonStop.QUERY).mapToList(CommonStop::mapper);

    }

    private Observable<CommonStop> flatMap2(List<CommonStop> commonStops) {
        return Observable.fromIterable(commonStops)
                .doOnSubscribe(disposable -> commodStopQueryResults.clear())
                .doOnComplete(() -> {

                });

    }

    private Observable<Bundle> flatMap3(CommonStop commonStop) {
        return Observable.zip(
                cityBusService.getBusEstimateTime(
                        commonStop.busRoute().getCityName().getEn(), getRemindQuery(commonStop)),
                Observable.just(commonStop),
                (busEstimateTimes, commonStop1) -> {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(BUS_ESTIMATE_TIME, busEstimateTimes.get(0));
                    bundle.putParcelable(BUS_ROUTESTOP, commonStop1);
                    return bundle;
                });
    }


    @Override
    public void initCommodStopTypes(List<CommonStopType> commonStopTypes) {
        TigerApplication.setCommodStopTypes(commonStopTypes);

    }

    @Override
    public void initCommodStopTypes(Throwable throwable) {
        TigerApplication.printLog(TlogType.wtf, TAG, throwable.toString());
    }
}
