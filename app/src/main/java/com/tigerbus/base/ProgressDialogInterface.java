package com.tigerbus.base;

import android.app.ProgressDialog;

public interface ProgressDialogInterface {

    void initProgress() ;

    void showProgress();

    void dimessProgress();

    ProgressDialog getProgressDialog();
}
