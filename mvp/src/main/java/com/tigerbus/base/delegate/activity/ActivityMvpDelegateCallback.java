package com.tigerbus.base.delegate.activity;

import com.tigerbus.base.MvpPresenter;
import com.tigerbus.base.MvpView;
import com.tigerbus.base.delegate.MvpDelegateCallback;

public interface ActivityMvpDelegateCallback<V extends MvpView, P extends MvpPresenter<V>>
        extends MvpDelegateCallback<V, P> {

    Object onRetainCustomInstance();

    Object getRetainCustomInstance();

    Object getCustomInstance();
}
