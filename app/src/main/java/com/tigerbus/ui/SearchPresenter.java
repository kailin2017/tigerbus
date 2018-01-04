package com.tigerbus.ui;

import android.os.Bundle;

import com.tigerbus.base.BasePresenter;
import com.tigerbus.base.ViewState;
import com.tigerbus.connection.RetrofitModel;
import com.tigerbus.data.CityBusService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kailin on 2017/12/31.
 */

public final class SearchPresenter extends BasePresenter<SearchActivityView> {

    private final CityBusService cityBusService = RetrofitModel.getInstance().create(CityBusService.class);

    @Override
    public void bindIntent() {
        getView().bindIntent().filter(b -> b)
                .flatMap(b -> cityBusService.getBusRoute("Taipei"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> render(SearchViewState.Finish.create()))
                .doOnComplete(() -> render(SearchViewState.Finish.create()))
                .subscribe(busRoutes -> {
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList(SearchActivityView.BUSROUTES, busRoutes);
                            render(SearchViewState.Success.create(bundle));
                        },
                        throwable -> render(SearchViewState.Exception.create(throwable.toString())));
    }

    private void render(ViewState viewState) {
        getView().render(viewState);
    }
}
