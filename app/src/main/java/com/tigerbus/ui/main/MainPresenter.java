package com.tigerbus.ui.main;

import android.os.Bundle;

import com.tigerbus.TigerApplication;
import com.tigerbus.app.BasePresenter;
import com.tigerbus.connection.RetrofitModel;
import com.tigerbus.data.CityBusInterface;
import com.tigerbus.data.CityBusService;
import com.tigerbus.data.CityConfigInterface;
import com.tigerbus.data.CityConfigService;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusVersion;
import com.tigerbus.data.detail.City;
import com.tigerbus.data.detail.NameType;

import java.util.ArrayList;
import java.util.WeakHashMap;

import io.reactivex.Observable;

public final class MainPresenter extends BasePresenter<MainView> implements CityBusInterface, CityConfigInterface {

    private final static String KEY_CITY = CityConfigService.CITYS;
    private final static String KEY_BUS_VERSION = BUS_VERSION;
    private final static String KEY_BUS_ROUTE = BUS_ROUTE;
    private final static WeakHashMap<String, ArrayList<BusRoute>> weakHashMap = new WeakHashMap<>();
    private CityBusService cityBusService = RetrofitModel.getInstance().create(CityBusService.class);
    private CityConfigService defaultService = RetrofitModel.getInstance().create(CityConfigService.class);

    @Override
    public void bindIntent() {
        Observable<Boolean> observable = getView().getInitDataSubject();
        observable
                .doOnSubscribe(this::renderDisposable)
                .observeOn(threadMain())
                .flatMap(this::faltMap1)
                .flatMap(this::faltMap2)
                .flatMap(this::faltMap3)
                .flatMap(this::faltMap4)
                .filter(this::filter2)
                .flatMap(this::faltMap5)
                .subscribe(this::onNext, this::throwable, this::onComplete);
    }

    private boolean filter2(City city) {
        return !city.getZh_tw().isEmpty();
    }

    private Observable<ArrayList<City>> faltMap1(boolean b) {
        // 取得城市列表
        return defaultService.getCitys().subscribeOn(threadIO());
    }

    private Observable<City> faltMap2(ArrayList<City> citys) {
        TigerApplication.setCityData(citys);
        return Observable.fromIterable(citys).subscribeOn(threadIO());
    }

    private Observable<Bundle> faltMap3(City city) {
        // 取得版本資訊並與城市資訊封裝
        Observable<Bundle> busVersionObserable = Observable.zip(
                cityBusService.getBusVersion(city.getEn()), Observable.just(city),
                (busVersion, cityObj) -> {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(KEY_BUS_VERSION, busVersion);
                    bundle.putParcelable(KEY_CITY, cityObj);
                    return bundle;
                });
        return busVersionObserable.subscribeOn(threadIO());
    }

    private Observable<City> faltMap4(Bundle bundle) {
        BusVersion busVersion = bundle.getParcelable(KEY_BUS_VERSION);
        City city = bundle.getParcelable(KEY_CITY);
        String keyVersion = KEY_BUS_VERSION + city.getEn();
        String keyRoute = KEY_BUS_ROUTE + city.getEn();
        // 判斷local是否已存在路線資料,若有的畫清掉中文城市名稱
        if (TigerApplication.getInt(keyVersion) < busVersion.getVersionID()) {
            TigerApplication.putInt(keyVersion, busVersion.getVersionID());
        } else {
            city.setZh_tw("");
            ArrayList<BusRoute> busRoutes = TigerApplication.getObjectArrayList(keyRoute, BusRoute[].class, false);
            weakHashMap.put(city.getEn(), busRoutes);
        }
        return Observable.just(city).subscribeOn(threadIO());
    }

    private Observable<Bundle> faltMap5(City city) {
        // 取得城市公車路線資料,並與城市資訊封裝
        Observable<Bundle> busRoutesObservable = Observable.zip(
                cityBusService.getBusRoute(city.getEn()), Observable.just(city),
                (busRoutes, cityObj) -> {
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(KEY_BUS_ROUTE, busRoutes);
                    bundle.putParcelable(KEY_CITY, cityObj);
                    return bundle;
                });
        return busRoutesObservable.subscribeOn(threadIO());
    }

    private void onNext(Bundle bundle) {
        ArrayList<BusRoute> busVersion = bundle.getParcelableArrayList(KEY_BUS_ROUTE);
        City city = bundle.getParcelable(KEY_CITY);
        String keyRoute = KEY_BUS_ROUTE + city.getEn();
        // 路線資料寫入城市資訊
        for (BusRoute busRoute : busVersion)
            busRoute.setCityName(new NameType(city.getZh_tw(), city.getEn()));
        TigerApplication.putObject(keyRoute, busVersion, false);
        weakHashMap.put(city.getEn(), busVersion);
    }

    private void onComplete() {
        ArrayList<BusRoute> busRoutes = new ArrayList<>();
        for (ArrayList<BusRoute> bus : weakHashMap.values())
            busRoutes.addAll(bus);
        TigerApplication.setBusRouteData(busRoutes);
        render(MainViewState.Finish.create());
    }
}
