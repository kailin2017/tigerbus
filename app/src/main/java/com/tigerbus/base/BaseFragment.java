package com.tigerbus.base;

import android.content.Context;

import com.tigerbus.TigerApplication;

public abstract class BaseFragment<V extends BaseView, P extends BasePresenter<V>>
        extends MvpFragment<V, P> implements BaseUIInterface, TigerDialog {

    public TigerApplication application;
    public Context context;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        this.application = (TigerApplication) context.getApplicationContext();
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        if (presenter != null){
            presenter.clearDisposable();
            presenter.clearUiDisposable();
        }
        super.onDestroy();
    }
}
