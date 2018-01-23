package com.tigerbus.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.tigerbus.TigerApplication;

public abstract class BaseFragment<V extends BaseView, P extends BasePresenter<V>>
        extends MvpFragment<V, P> implements BaseUIInterface {

    public TigerApplication application;
    public ProgressDialog progressDialog;
    public AlertDialog messageDialog;
    public AlertDialog listDialog;
    public Context context;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        application = (TigerApplication) context.getApplicationContext();
        initProgress();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStop() {
        if (presenter != null)
            presenter.clearUiDisposable();
        if (presenter != null)
            presenter.clearDisposable();
        super.onStop();
    }

    @Override
    public void initProgress() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
    }

    @Override
    public synchronized void showProgress() {
        synchronized (ProgressDialog.class) {
            if (progressDialog == null)
                initProgress();
            if (!progressDialog.isShowing())
                progressDialog.show();
        }
    }

    @Override
    public void dimessProgress() {
        if (progressDialog != null)
            if (progressDialog.isShowing())
                progressDialog.dismiss();
    }

    @Override
    public void initMessageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setNegativeButton(getString(R.string.dialog_ok), (d, i) -> {
        });
        builder.setPositiveButton(getString(R.string.dialog_cancel), (d, i) -> {
        });
        messageDialog = builder.create();
    }

    @Override
    public synchronized void showMessage(String msg) {
        synchronized (AlertDialog.class) {
            if (messageDialog == null)
                initMessageDialog();
            if (messageDialog.isShowing())
                dimessMessage();
            messageDialog.setMessage(msg);
        }
    }

    @Override
    public void dimessMessage() {
        if (messageDialog != null)
            if (messageDialog.isShowing())
                messageDialog.dismiss();
    }

    @Override
    public void showListAlert(CharSequence[] items, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(items, listener);
        builder.setCancelable(false);
        listDialog = builder.create();
    }

    @Override
    public void dimessListAlert() {
        if (listDialog != null)
            if (listDialog.isShowing())
                listDialog.dismiss();
    }
}
