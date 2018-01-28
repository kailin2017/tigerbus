package com.tigerbus.ui.main.sub;

import android.os.Bundle;

import com.squareup.sqlbrite3.BriteDatabase;
import com.tigerbus.BuildConfig;
import com.tigerbus.TigerApplication;
import com.tigerbus.base.BasePresenter;
import com.tigerbus.data.CityBusInterface;
import com.tigerbus.data.autovalue.HomePresenterAutoValue;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.sqlite.data.CommodStopQueryResult;
import com.tigerbus.sqlite.data.CommonStop;
import com.tigerbus.sqlite.data.CommonStopInterface;
import com.tigerbus.sqlite.data.CommonStopType;
import com.tigerbus.sqlite.data.CommonStopTypeInterface;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public final class HomePresenter extends BasePresenter<HomeView>
        implements CityBusInterface, CommonStopInterface, CommonStopTypeInterface {

    private BriteDatabase briteDatabase;
    private TreeMap<CommonStopType, HomePresenterAutoValue> homePresenterAutoValueHashMap = new TreeMap<>();
    private SoftReference<List<CommonStop>> commonStopsReference;

    public HomePresenter(BriteDatabase briteDatabase) {
        this.briteDatabase = briteDatabase;
    }

    @Override
    public void bindIntent() {
        Observable<Boolean> observable = getView().bindInitData();
        observable.doOnSubscribe(this::addDisposable).subscribe(this::initData);
    }

    private void initData(boolean b) {
        initCommonStopDatas();
        initRefreshData();
    }

    private void initCommonStopDatas(){
        initCommodStopTypes(briteDatabase);
        initCommodStops(briteDatabase);
    }

    private void initRefreshData() {
        Observable.interval(BuildConfig.firstTime, BuildConfig.updateTime, TimeUnit.SECONDS, Schedulers.io())
                .doOnSubscribe(this::addDisposable)
                .flatMap(this::flatMap1)
                .flatMap(this::flatMap2)
                .subscribe(this::onNext, this::throwable);
    }

    private Observable<CommonStop> flatMap1(long aLong) {
        if (commonStopsReference.get() == null) {
            initCommonStopDatas();
        }
        return Observable.fromIterable(commonStopsReference.get())
                .doOnSubscribe(this::flatMap1Subcribe).doOnComplete(this::flatMap1Complete);
    }

    private void flatMap1Subcribe(Disposable disposable) {
        for (HomePresenterAutoValue homePresenterAutoValue : homePresenterAutoValueHashMap.values()) {
            homePresenterAutoValue.commodStopQueryResults().clear();
        }
    }

    private void flatMap1Complete() {
        Observable.fromIterable(homePresenterAutoValueHashMap.values())
                .filter(homePresenterAutoValue ->
                        homePresenterAutoValue.commodStopQueryResults().size() > 0)
                .subscribe(homePresenterAutoValue ->
                        homePresenterAutoValue.publishSubject()
                                .onNext(homePresenterAutoValue.commodStopQueryResults()));
    }

    private Observable<Bundle> flatMap2(CommonStop commonStop) {
        return Observable.zip(
                cityBusService.getBusEstimateTime(
                        commonStop.routeStop().busRoute().getCityName().getEn(),
                        getRemindQuery(commonStop.routeStop())),
                Observable.just(commonStop),
                (busEstimateTimes, commonStop1) -> {
                    Bundle bundle = new Bundle();
                    if (busEstimateTimes.size() > 0)
                        bundle.putParcelable(BUS_ESTIMATE_TIME, busEstimateTimes.get(0));
                    bundle.putParcelable(BUS_ROUTESTOP, commonStop1);
                    return bundle;
                });
    }

    private void onNext(Bundle bundle) {
        BusEstimateTime busEstimateTime = bundle.getParcelable(BUS_ESTIMATE_TIME);
        if (busEstimateTime == null)
            busEstimateTime = new BusEstimateTime();
        CommonStop commonStop = bundle.getParcelable(BUS_ROUTESTOP);
        homePresenterAutoValueHashMap.get(commonStop.commonStopType())
                .commodStopQueryResults().add(CommodStopQueryResult.create(commonStop, busEstimateTime));
    }

    @Override
    public void initCommodStopTypes(List<CommonStopType> commonStopTypes) {
        TigerApplication.setCommodStopTypes(commonStopTypes);
        for (CommonStopType commonStopType : commonStopTypes) {
            homePresenterAutoValueHashMap.put(commonStopType,
                    HomePresenterAutoValue.create(commonStopType, PublishSubject.create(), new ArrayList<>()));
        }
        render(HomeViewState.Success.create(homePresenterAutoValueHashMap));
    }

    @Override
    public void initCommodStopTypes(Throwable throwable) {
        render(HomeViewState.Exception.create(throwable.toString()));
    }

    @Override
    public void initCommodStops(List<CommonStop> commonStops) {
        commonStopsReference = new SoftReference<>(commonStops);
    }

    @Override
    public void initCommodStops(Throwable throwable) {
        render(HomeViewState.Exception.create(throwable.toString()));
    }
}
