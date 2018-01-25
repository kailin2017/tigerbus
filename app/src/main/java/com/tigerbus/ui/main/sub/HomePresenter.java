package com.tigerbus.ui.main.sub;

import android.app.Fragment;
import android.os.Bundle;

import com.squareup.sqlbrite3.BriteDatabase;
import com.tigerbus.BuildConfig;
import com.tigerbus.TigerApplication;
import com.tigerbus.base.BasePresenter;
import com.tigerbus.data.CityBusInterface;
import com.tigerbus.data.autovalue.HomePresenterAutoValue;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.sqlite.data.CommodStopQueryResult;
import com.tigerbus.sqlite.data.CommonStopType;
import com.tigerbus.sqlite.data.CommonStopTypeApi;
import com.tigerbus.sqlite.data.CommonStops;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public final class HomePresenter extends BasePresenter<HomeView>
        implements CityBusInterface, CommonStopTypeApi {

    private BriteDatabase briteDatabase;
    private TreeMap<CommonStopType, HomePresenterAutoValue> homePresenterAutoValueHashMap = new TreeMap<>();

    public HomePresenter(BriteDatabase briteDatabase) {
        this.briteDatabase = briteDatabase;
    }

    @Override
    public void bindIntent() {
        Observable<Boolean> observable = getView().bindInitData();
        observable.doOnSubscribe(this::addDisposable).subscribe(this::initData);
    }

    public void initData(boolean b) {
        initCommodStopTypes(briteDatabase);
        Observable.interval(BuildConfig.firstTime, BuildConfig.updateTime, TimeUnit.SECONDS, Schedulers.io())
                .doOnSubscribe(this::addDisposable)
                .flatMap(this::flatMap1)
                .flatMap(this::flatMap2)
                .flatMap(this::flatMap3)
                .subscribe(this::onNext, this::throwable);
    }

    private Observable<List<CommonStops>> flatMap1(long aLong) {
        return briteDatabase.createQuery(CommonStops.QUERY_TABLES, CommonStops.QUERY).mapToList(CommonStops::mapper);
    }

    private Observable<CommonStops> flatMap2(List<CommonStops> commonStops) {
        return Observable.fromIterable(commonStops)
                .doOnSubscribe(this::flatMap2Subcribe).doOnComplete(this::flatMap2Complete);
    }

    private void flatMap2Subcribe(Disposable disposable) {
        for (HomePresenterAutoValue homePresenterAutoValue : homePresenterAutoValueHashMap.values()) {
            homePresenterAutoValue.commodStopQueryResults().clear();
        }
    }

    private void flatMap2Complete() {
        ((Fragment) getView()).getActivity().runOnUiThread(() -> {
            for (HomePresenterAutoValue homePresenterAutoValue : homePresenterAutoValueHashMap.values()) {
                homePresenterAutoValue.publishSubject().onNext(homePresenterAutoValue.commodStopQueryResults());
            }
        });
    }

    private Observable<Bundle> flatMap3(CommonStops commonStop) {
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
        CommonStops commonStop = bundle.getParcelable(BUS_ROUTESTOP);
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


}
