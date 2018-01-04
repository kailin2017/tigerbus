package com.tigerbus.base.delegate;

import android.support.annotation.NonNull;

import com.tigerbus.base.MvpPresenter;
import com.tigerbus.base.MvpView;

public interface MvpDelegateCallback<V extends MvpView, P extends MvpPresenter<V>> {


    @NonNull
    P createPresenter();

    P getPresenter();

    void setPresenter(P presenter);

    V getMvpView();

    boolean isRetainInstance();

    void setRetainInstance(boolean retainingInstance);

    boolean shouldInstanceBeRetained();
}
