package com.tigerbus.base.delegate.activity;

import android.os.Bundle;

import com.tigerbus.base.MvpPresenter;
import com.tigerbus.base.MvpView;
import com.tigerbus.base.delegate.MvpDelegate;


public interface ActivityMvpDelegate<V extends MvpView, P extends MvpPresenter<V>> extends MvpDelegate {

    void onRestart();

    void onContentChanged();

    void onSaveInstanceState(Bundle outState);

    void onPostCreate(Bundle savedInstanceState);

    Object onRetainCustomNonConfigurationInstance();

    Object getNonConfigurationInstance();
}
