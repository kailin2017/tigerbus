package com.tigerbus.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tigerbus.base.annotation.ActivityInjects;
import com.tigerbus.base.annotation.ActivityView;
import com.tigerbus.base.delegate.activity.ActivityMvpDelegate;
import com.tigerbus.base.delegate.activity.ActivityMvpDelegateCallback;
import com.tigerbus.base.delegate.activity.ActivityMvpDelegateImpl;
import com.tigerbus.base.delegate.activity.ActivityMvpDelegateImplnon;


public abstract class MvpActivity<V extends MvpView, P extends MvpPresenter<V>>
        extends AppCompatActivity implements MvpView, ActivityMvpDelegateCallback<V, P> {

    protected ActivityMvpDelegate mvpDelegate;
    protected P presenter;
    protected boolean retainInstance, isMvpPatten = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ActivityView activityView = getClass().getAnnotation(ActivityView.class);
        if (activityView != null) {
            isMvpPatten = activityView.mvp();
            ActivityInjects.inject(getClass(), this);
        }
        super.onCreate(savedInstanceState);
        getMvpDelegate().onCreate(savedInstanceState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getMvpDelegate().onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getMvpDelegate().onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getMvpDelegate().onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getMvpDelegate().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getMvpDelegate().onDestroy();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        getMvpDelegate().onContentChanged();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getMvpDelegate().onPostCreate(savedInstanceState);
    }

    @NonNull
    protected ActivityMvpDelegate<V, P> getMvpDelegate() {
        synchronized (ActivityMvpDelegate.class){
            if (mvpDelegate == null) {
                mvpDelegate = isMvpPatten ? new ActivityMvpDelegateImpl(this) : new ActivityMvpDelegateImplnon();
            }
            return mvpDelegate;
        }
    }

    @NonNull
    public abstract P createPresenter();

    @NonNull
    @Override
    public P getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(@NonNull P presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public V getMvpView() {
        return (V) this;
    }

    @Override
    public boolean isRetainInstance() {
        return retainInstance;
    }

    @Override
    public boolean shouldInstanceBeRetained() {
        return retainInstance && isChangingConfigurations();
    }

    @Override
    public void setRetainInstance(boolean retainInstance) {
        this.retainInstance = retainInstance;
    }

    @Override
    public Object onRetainCustomInstance() {
        return null;
    }

    @Override
    public Object getRetainCustomInstance() {
        return null;
    }

    @Override
    public final Object onRetainCustomNonConfigurationInstance() {
        return getMvpDelegate().onRetainCustomNonConfigurationInstance();
    }

    @Override
    public final Object getCustomInstance() {
        return getMvpDelegate().getNonConfigurationInstance();
    }

}
