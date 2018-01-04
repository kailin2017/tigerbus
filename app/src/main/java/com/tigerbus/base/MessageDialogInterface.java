package com.tigerbus.base;

import android.app.AlertDialog;

public interface MessageDialogInterface {

    void initMessageDialog();

    void showMessage(String msg);

    void dimessMessage();

    AlertDialog getMessageDialog();
}
