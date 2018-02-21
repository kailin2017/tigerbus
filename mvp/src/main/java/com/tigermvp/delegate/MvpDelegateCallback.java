package com.tigermvp.delegate;

import android.support.annotation.NonNull;

import com.tigermvp.MvpPresenter;
import com.tigermvp.MvpView;

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
