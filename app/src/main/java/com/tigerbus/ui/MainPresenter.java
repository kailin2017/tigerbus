package com.tigerbus.ui;

import com.tigerbus.TigerApplication;
import com.tigerbus.base.BasePresenter;
import com.tigerbus.connection.RetrofitModel;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.CityBusService;
import com.tigerbus.data.detail.NameType;
import com.tigerbus.key.City;

import java.util.ArrayList;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class MainPresenter extends BasePresenter<MainView> {

    private final static WeakHashMap<String, ArrayList<BusRoute>> weakHashMap = new WeakHashMap<>();
    private CityBusService cityBusService = RetrofitModel.getInstance().create(CityBusService.class);
    private String[] strings = new String[]{"Taipei", "NewTaipei", "Taoyuan", "Taichung", "Tainan", "Kaohsiung"};

    @Override
    public void bindIntent() {
        Observable<Boolean> observable = getView().getInitDataSubject();
        observable.filter(b -> b)
                .flatMap(b -> Observable.fromArray(strings))
                .doOnSubscribe(renderDisposableConsumer)
                .doOnComplete(() -> render(MainViewState.Finish.create()))
                .subscribe(city -> checkData(city), throwableConsumer);
    }

    private void checkData(String city) {
        cityBusService.getBusVersion(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(defaultDisposableConsumer)
                .subscribe(result -> {
                    String keyBusVersion = cityBusService.BUS_VERSION + city;
                    String keyRoute = cityBusService.BUS_ROUTE + city;
                    int localVersion = TigerApplication.getInt(keyBusVersion);
                    TigerApplication.putInt(keyBusVersion, result.getVersionID());
                    if (localVersion < result.getVersionID()) {
                        loadData(city, keyRoute);
                    } else {
                        ArrayList<BusRoute> busRoutes = TigerApplication.getObjectArrayList(keyRoute, BusRoute[].class, false);
                        weakHashMap.put(city, busRoutes);
                    }
                }, throwableConsumer, () -> {
                    ArrayList<BusRoute> busRoutes = new ArrayList<>();
                    for (ArrayList<BusRoute> bus : weakHashMap.values())
                        busRoutes.addAll(bus);
                    TigerApplication.setBusRouteData(busRoutes);
                });
    }

    private void loadData(String city, String key) {
        cityBusService.getBusRoute(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(defaultDisposableConsumer)
                .subscribe(result -> {
                    for (BusRoute busRoute : result)
                        busRoute.setCityName(new NameType(City.valueOf(city).getCity(), city));
                    TigerApplication.putObject(key, result, false);
                    weakHashMap.put(city, result);
                }, throwableConsumer);
    }


}
