package com.tigermvp.delegate.activity;

import com.tigermvp.MvpPresenter;
import com.tigermvp.MvpView;
import com.tigermvp.delegate.MvpDelegateCallback;

public interface ActivityMvpDelegateCallback<V extends MvpView, P extends MvpPresenter<V>>
        extends MvpDelegateCallback<V, P> {

    Object onRetainCustomInstance();

    Object getRetainCustomInstance();

    Object getCustomInstance();
}
