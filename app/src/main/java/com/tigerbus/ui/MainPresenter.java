package com.tigerbus.ui;

import android.os.Bundle;

import com.tigerbus.TigerApplication;
import com.tigerbus.base.BasePresenter;
import com.tigerbus.base.log.TlogType;
import com.tigerbus.connection.RetrofitModel;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.CityBusService;
import com.tigerbus.data.bus.BusVersion;
import com.tigerbus.data.detail.NameType;
import com.tigerbus.key.City;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public final class MainPresenter extends BasePresenter<MainView> {

    private final static String KEY_CITY = "city", KEY_BUS_VERSION = CityBusService.BUS_VERSION, KEY_BUS_ROUTE = CityBusService.BUS_ROUTE;
    private final static WeakHashMap<String, ArrayList<BusRoute>> weakHashMap = new WeakHashMap<>();
    private CityBusService cityBusService = RetrofitModel.getInstance().create(CityBusService.class);
    private String[] citys = new String[]{"Taipei", "NewTaipei", "Taoyuan", "Taichung", "Tainan", "Kaohsiung", "Keelung",
            "Hsinchu", "HsinchuCounty", "MiaoliCounty", "ChanghuaCounty", "NantouCounty", "YunlinCounty", "Chiayi", "ChiayiCounty",
            "PingtungCounty", "YilanCounty", "HualienCounty", "TaitungCounty", "PenghuCounty", "KinmenCounty"};

    @Override
    public void bindIntent() {
        initData();
    }

    private void initData() {
        Observable<Boolean> observable = getView().getInitDataSubject();
        observable
                .filter(aBoolean -> aBoolean)
                .flatMap(aBoolean -> Observable.fromArray(citys))
                .flatMap(city -> {
                    Observable<Bundle> busVersionObserable = Observable.zip(
                            cityBusService.getBusVersion(city), Observable.just(city),
                            (busVersion, cityName) -> {
                                Bundle bundle = new Bundle();
                                bundle.putParcelable(KEY_BUS_VERSION, busVersion);
                                bundle.putString(KEY_CITY, cityName);
                                return bundle;
                            });
                    return rxSwitchThread(busVersionObserable);
                })
                .flatMap(bundle -> {
                    BusVersion busVersion = bundle.getParcelable(KEY_BUS_VERSION);
                    String city = bundle.getString(KEY_CITY);
                    String keyVersion = KEY_BUS_VERSION + city;
                    String keyRoute = KEY_BUS_ROUTE + city;
                    boolean isReload;
                    if (TigerApplication.getInt(keyVersion) < busVersion.getVersionID()) {
                        isReload = true;
                        TigerApplication.putInt(keyVersion, busVersion.getVersionID());
                    } else {
                        isReload = false;
                        ArrayList<BusRoute> busRoutes = TigerApplication.getObjectArrayList(keyRoute, BusRoute[].class, false);
                        weakHashMap.put(city, busRoutes);
                    }
                    return Observable.just(isReload ? city : "");
                })
                .filter(city -> !city.isEmpty())
                .flatMap(city -> {
                    Observable<Bundle> busRoutesObservable = Observable.zip(
                            cityBusService.getBusRoute(city), Observable.just(city),
                            (busRoutes, cityName) -> {
                                Bundle bundle = new Bundle();
                                bundle.putParcelableArrayList(KEY_BUS_ROUTE, busRoutes);
                                bundle.putString(KEY_CITY, cityName);
                                return bundle;
                            });
                    return rxSwitchThread(busRoutesObservable);
                })
                .doOnSubscribe(renderDisposableConsumer)
                .subscribe(
                        bundle -> {
                            ArrayList<BusRoute> busVersion = bundle.getParcelableArrayList(KEY_BUS_ROUTE);
                            String city = bundle.getString(KEY_CITY);
                            String keyRoute = KEY_BUS_ROUTE + city;
                            for (BusRoute busRoute : busVersion)
                                busRoute.setCityName(new NameType(City.valueOf(city).getCity(), city));
                            TigerApplication.putObject(keyRoute, busVersion, false);
                            weakHashMap.put(city, busVersion);
                            render(MainViewState.LogInfo.create(city + "onNext"));
                        },
                        throwableConsumer,
                        () -> {
                            ArrayList<BusRoute> busRoutes = new ArrayList<>();
                            for (ArrayList<BusRoute> bus : weakHashMap.values())
                                busRoutes.addAll(bus);
                            TigerApplication.setBusRouteData(busRoutes);
                            render(MainViewState.LogInfo.create("onComplete"));
                            render(MainViewState.Finish.create());
                        }
                );
    }


}
