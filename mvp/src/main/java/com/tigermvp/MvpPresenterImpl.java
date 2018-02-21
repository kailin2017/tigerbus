package com.tigermvp;

import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

import java.lang.ref.WeakReference;

public abstract class MvpPresenterImpl<V extends MvpView> implements MvpPresenter<V> {

    protected WeakReference<V> viewRef;

    @UiThread
    @Override
    public void attachView(V view) {
        viewRef = new WeakReference<>(view);
        bindIntent();
    }

    @UiThread
    @Nullable
    public V getView() {
        return viewRef == null ? null : viewRef.get();
    }

    @UiThread
    public boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    @Override
    public void bindIntent(){
    }

    @UiThread
    @Override
    public void detachView(boolean retainInstance) {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }


}
