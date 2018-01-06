package com.tigerbus.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;

interface DialogInterface {

    interface Message{
        void initMessageDialog();

        void showMessage(String msg);

        void dimessMessage();

        AlertDialog getMessageDialog();
    }

    interface Progress{
        void initProgress() ;

        void showProgress();

        void dimessProgress();

        ProgressDialog getProgressDialog();
    }
}
