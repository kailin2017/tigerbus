package com.tigerbus.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tigerbus.TigerApplication;

public abstract class BaseActivity<V extends BaseView, P extends BasePresenter<V>> extends MvpActivity<V, P>
        implements DialogInterface.Progress, DialogInterface.Message {

    public TigerApplication application;
    public ProgressDialog progressDialog;
    public AlertDialog messageDialog;
    public Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        application = (TigerApplication) getApplication();
        context = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStop() {
        if(presenter!=null)
            presenter.clearDisposable();
        super.onStop();
    }

    protected void startActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void initProgress() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
    }

    @Override
    public void showProgress() {
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
    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    @Override
    public void initMessageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setNegativeButton(getString(R.string.dialog_ok), (d, i) -> {
//        });
//        builder.setPositiveButton(getString(R.string.dialog_cancel), (d, i) -> {
//        });
        messageDialog = builder.create();
    }

    @Override
    public void showMessage(String msg) {
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
    public AlertDialog getMessageDialog() {
        return messageDialog;
    }
}
