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

    @Override
    public void bindIntent() {
    }

    private void render(ViewState viewState) {
        getView().render(viewState);
    }
}
