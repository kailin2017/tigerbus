package com.tigerbus.base.delegate.activity;

import com.tigerbus.base.MvpPresenter;
import com.tigerbus.base.MvpView;

public class ActivityMvpNonConfigurationInstances <V extends MvpView, P extends MvpPresenter<V>>{

    P presenter;

    Object nonMosbyCustomConfigurationInstance;

    ActivityMvpNonConfigurationInstances(P presenter, Object nonMosbyCustomConfigurationInstance) {
        this.presenter = presenter;
        this.nonMosbyCustomConfigurationInstance = nonMosbyCustomConfigurationInstance;
    }
}
