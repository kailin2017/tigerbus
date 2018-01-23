package com.tigerbus.base;


import android.content.DialogInterface;

interface Dialog {

    interface Message{
        void initMessageDialog();

        void showMessage(String msg);

        void dimessMessage();
    }

    interface Progress{
        void initProgress() ;

        void showProgress();

        void dimessProgress();
    }

    interface ListAlert{
        void showListAlert(CharSequence[] items,  DialogInterface.OnClickListener listener);

        void dimessListAlert();
    }
}
