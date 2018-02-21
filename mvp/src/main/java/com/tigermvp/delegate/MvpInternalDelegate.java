package com.tigermvp.delegate;

import com.tigermvp.MvpPresenter;
import com.tigermvp.MvpView;

public class MvpInternalDelegate <V extends MvpView, P extends MvpPresenter<V>>{

    protected MvpDelegateCallback<V, P> delegateCallback;

    public MvpInternalDelegate(MvpDelegateCallback<V, P> delegateCallback) {
        if (delegateCallback == null) {
            throw new NullPointerException("MvpDelegateCallback is null!");
        }
        this.delegateCallback = delegateCallback;
    }

    public void createPresenter() {
        P presenter = delegateCallback.getPresenter();
        if (presenter == null) {
            presenter = delegateCallback.createPresenter();
        }
        if (presenter == null) {
            throw new NullPointerException("Presenter is null! Do you return null in createPresenter()?");
        }
        delegateCallback.setPresenter(presenter);
    }

    public void attachView() {
        V view = delegateCallback.getMvpView();
        getPresenter().attachView(view);
    }

    public void detachView() {
        getPresenter().detachView(delegateCallback.shouldInstanceBeRetained());
    }

    private P getPresenter() {
        P presenter = delegateCallback.getPresenter();
        if (presenter == null) {
            throw new NullPointerException("Presenter returned from getPresenter() is null");
        }
        return presenter;
    }
}
