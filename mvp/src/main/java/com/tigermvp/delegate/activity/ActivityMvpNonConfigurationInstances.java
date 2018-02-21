package com.tigermvp.delegate.activity;

import com.tigermvp.MvpPresenter;
import com.tigermvp.MvpView;

public class ActivityMvpNonConfigurationInstances <V extends MvpView, P extends MvpPresenter<V>>{

    P presenter;

    Object nonMosbyCustomConfigurationInstance;

    ActivityMvpNonConfigurationInstances(P presenter, Object nonMosbyCustomConfigurationInstance) {
        this.presenter = presenter;
        this.nonMosbyCustomConfigurationInstance = nonMosbyCustomConfigurationInstance;
    }
}
