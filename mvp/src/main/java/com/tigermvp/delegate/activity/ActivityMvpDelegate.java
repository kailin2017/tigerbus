package com.tigermvp.delegate.activity;

import android.os.Bundle;

import com.tigermvp.MvpPresenter;
import com.tigermvp.MvpView;
import com.tigermvp.delegate.MvpDelegate;

public interface ActivityMvpDelegate<V extends MvpView, P extends MvpPresenter<V>> extends MvpDelegate {

    void onRestart();

    void onContentChanged();

    void onSaveInstanceState(Bundle outState);

    void onPostCreate(Bundle savedInstanceState);

    Object onRetainCustomNonConfigurationInstance();

    Object getNonConfigurationInstance();
}
