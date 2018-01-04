package com.tigerbus.ui;

import com.google.gson.Gson;
import com.tigerbus.TigerApplication;
import com.tigerbus.base.BasePresenter;
import com.tigerbus.base.ViewState;
import com.tigerbus.connection.RetrofitModel;
import com.tigerbus.data.BusRoute;
import com.tigerbus.data.CityBusService;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public final class MainPresenter extends BasePresenter<MainView> {

    private final static String TAG = MainPresenter.class.getSimpleName();
    private Gson gson = new Gson();
    private CityBusService cityBusService = RetrofitModel.getInstance().create(CityBusService.class);
    private Consumer<Throwable> throwableConsumer = throwable -> render(MainViewState.Exception.create(throwable.toString()));
    private String[] strings = new String[]{"Taipei", "NewTaipei", "Taichung"};

    @Override
    public void bindIntent() {
        getView().getInitDataSubject().filter(b -> b)
                .flatMap(b -> Observable.fromArray(strings))
                .doOnSubscribe(disposable -> render(MainViewState.Loading.create(disposable)))
                .doOnComplete(() -> render(MainViewState.Finish.create()))
                .subscribe(city -> checkData(city), throwableConsumer);
    }

    private void checkData(String city) {
        cityBusService.getBusVersion(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    String keyBusVersion = cityBusService.getBusVersion + city;
                    String keyRoute = cityBusService.getBusRoute + city;
                    int localVersion = TigerApplication.getInt(keyBusVersion);
                    TigerApplication.putInt(keyBusVersion, result.getVersionID());
                    if (localVersion < result.getVersionID()) {
                        loadData(city, keyRoute);
                    } else {
                        ArrayList<BusRoute> busRoutes = TigerApplication.getObjectArrayList(keyRoute, BusRoute[].class, false);
                        TigerApplication.weakHashMap.put(city, busRoutes);
                    }
                }, throwableConsumer);
    }

    private void loadData(String city, String key) {
        cityBusService.getBusRoute(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    TigerApplication.putString(key, gson.toJson(result).toString());
                    TigerApplication.weakHashMap.put(city, result);
                }, throwableConsumer);
    }

    private void render(ViewState viewState) {
        getView().render(viewState);
    }
}
