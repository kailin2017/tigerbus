package com.tigerbus.ui.main.sub;

import com.tigerbus.base.BasePresenter;

import io.reactivex.Observable;


public final class RemindPresenter extends BasePresenter<RemindView> {

    @Override
    public void bindIntent() {
        Observable<Boolean> observable = getView().bindInit();

    }


}
