package com.tigerbus.ui.main.sub;

import com.squareup.sqlbrite3.BriteDatabase;
import com.tigerbus.base.BasePresenter;
import com.tigerbus.sqlite.data.RemindStop;
import com.tigerbus.sqlite.data.RemindStopInterface;

import java.util.List;

import io.reactivex.Observable;

public final class RemindPresenter extends BasePresenter<RemindView> implements RemindStopInterface {

    private BriteDatabase briteDatabase;

    public RemindPresenter(BriteDatabase briteDatabase) {
        this.briteDatabase = briteDatabase;
    }

    @Override
    public void bindIntent() {
        Observable<Boolean> observable = getView().bindInitData();
        observable.subscribe(this::initData);
    }

    public void initData(boolean b){
        addDisposable(initRemindStopAll(briteDatabase));
    }


    @Override
    public void initRemindStopResult(List<RemindStop> remindStops) {
        render(RemindViewState.Success.create(remindStops));
    }

    @Override
    public void initRemindStopError(Throwable throwable) {
        render(RemindViewState.Exception.create(throwable.toString()));
    }
}
