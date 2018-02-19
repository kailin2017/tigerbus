package com.tigerbus.app;

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
    public void onDestroyView() {
        if (presenter != null){
            presenter.clearDisposable();
            presenter.clearUiDisposable();
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
