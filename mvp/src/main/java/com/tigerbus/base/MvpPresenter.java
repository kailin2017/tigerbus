package com.tigerbus.base;

public interface MvpPresenter<V extends MvpView> {

    void attachView(V view);

    void detachView(boolean retainInstance);

    void bindIntent();
}
