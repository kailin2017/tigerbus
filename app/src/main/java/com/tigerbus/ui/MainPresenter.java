package com.tigerbus.ui;

import com.google.gson.Gson;
import com.tigerbus.TigerApplication;
import com.tigerbus.base.BasePresenter;
import com.tigerbus.base.ViewState;
import com.tigerbus.base.log.TlogType;
import com.tigerbus.connection.RetrofitModel;
import com.tigerbus.data.CityBusService;

import java.util.ArrayList;

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
                    String localVersion = TigerApplication.readPreferences(cityBusService.getBusVersion + city);
                    TigerApplication.writePreferences(cityBusService.getBusVersion + city, result.getVersionID() + "");
                    if (localVersion == "") {
                        loadData(city);
                    } else {
                        if (Integer.parseInt(localVersion) == result.getVersionID()) {
                            String routeString = TigerApplication.readPreferences(cityBusService.getBusRoute + city);
                            TigerApplication.weakHashMap.put(city, gson.fromJson(routeString, ArrayList.class));
                        } else {
                            loadData(city);
                        }
                    }
                }, throwableConsumer);
    }

    private void loadData(String city) {
        cityBusService.getBusRoute(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    TigerApplication.writePreferences(cityBusService.getBusRoute + city, gson.toJson(result).toString());
                    TigerApplication.weakHashMap.put(city, result);
                }, throwableConsumer);
    }

    private void render(ViewState viewState) {
        getView().render(viewState);
    }
}
