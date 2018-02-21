package com.tigermvp.delegate.activity;

import android.os.Bundle;

import com.tigermvp.MvpPresenter;
import com.tigermvp.MvpView;
import com.tigermvp.delegate.MvpInternalDelegate;

public class ActivityMvpDelegateImpl<V extends MvpView, P extends MvpPresenter<V>> implements ActivityMvpDelegate {

    protected MvpInternalDelegate<V, P> internalDelegate;
    protected ActivityMvpDelegateCallback<V, P> delegateCallback;

    public ActivityMvpDelegateImpl(ActivityMvpDelegateCallback<V, P> delegateCallback) {
        if (delegateCallback == null) {
            throw new NullPointerException("MvpDelegateCallback is null!");
        }
        this.delegateCallback = delegateCallback;
    }

    protected MvpInternalDelegate<V, P> getInternalDelegate() {
        if (internalDelegate == null) {
            internalDelegate = new MvpInternalDelegate<>(delegateCallback);
        }
        return internalDelegate;
    }


    @Override
    public void onCreate(Bundle bundle) {
        ActivityMvpNonConfigurationInstances<V, P> nci =
                (ActivityMvpNonConfigurationInstances<V, P>) delegateCallback.getRetainCustomInstance();
        if (nci != null && nci.presenter != null) {
            delegateCallback.setPresenter(nci.presenter);
        } else {
            getInternalDelegate().createPresenter();
        }
        getInternalDelegate().attachView();
    }

    @Override
    public void onDestroy() {
        getInternalDelegate().detachView();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onContentChanged() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {

    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        P presenter = delegateCallback.shouldInstanceBeRetained() ? delegateCallback.getPresenter() : null;
        Object instance = delegateCallback.onRetainCustomInstance();

        return (presenter == null && instance == null) ? null :
                new ActivityMvpNonConfigurationInstances<>(presenter, instance);
    }

    @Override
    public Object getNonConfigurationInstance() {
        ActivityMvpNonConfigurationInstances last =
                (ActivityMvpNonConfigurationInstances) delegateCallback.getRetainCustomInstance();
        return last == null ? null : last.nonMosbyCustomConfigurationInstance;
    }


}
