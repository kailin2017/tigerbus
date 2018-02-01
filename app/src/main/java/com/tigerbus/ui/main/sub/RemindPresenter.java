package com.tigerbus.ui.main.sub;

import com.squareup.sqlbrite3.BriteDatabase;
import com.tigerbus.base.BasePresenter;

import io.reactivex.Observable;


public final class RemindPresenter extends BasePresenter<RemindView> {

    private BriteDatabase briteDatabase;

    public RemindPresenter(BriteDatabase briteDatabase){
        this.briteDatabase = briteDatabase;
    }

    @Override
    public void bindIntent() {
        Observable<Boolean> observable = getView().bindInit();

    }


}
