package com.tigerbus.base;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.HashMap;

interface TigerDialog {

    String PROGRESSDIALOG = "PROGRESSDIALOG";
    String MESSAGEDIALOG = "MESSAGEDIALOG";
    String LISTDIALOG = "LISTDIALOG";
    String EDITTEXTDIALOG = "EDITTEXTDIALOG";

    HashMap<String, AlertDialog> dialogMap = new HashMap<>();
    DialogInterface.OnClickListener defaultListener = (dimess, i) -> dimess.dismiss();

    default void initProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        dialogMap.put(PROGRESSDIALOG, progressDialog);
    }

    default void showProgressDialog(Context context) {
        if (dialogMap.containsKey(PROGRESSDIALOG)) {
            initProgressDialog(context);
        }
        ProgressDialog progressDialog = (ProgressDialog) dialogMap.get(PROGRESSDIALOG);
        progressDialog.show();
    }

    default void dimessProgressDialog() {
        dimessDialog(dialogMap.get(PROGRESSDIALOG));
    }

    default void showMessageDialog(Context context, String msg) {
        showMessageDialog(context, msg, null);
    }

    default void showMessageDialog(Context context, String message, DialogInterface.OnClickListener listener) {
        if (dialogMap.containsKey(MESSAGEDIALOG)) {
            AlertDialog messageDialog = dialogMap.get(MESSAGEDIALOG);
            if (messageDialog.isShowing())
                messageDialog.dismiss();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage(message);
        if (listener == null) {
            builder.setPositiveButton(context.getText(R.string.dialog_ok1), defaultListener);
        } else {
            builder.setPositiveButton(context.getText(R.string.dialog_ok1), listener);
            builder.setNegativeButton(context.getText(R.string.dialog_cancel), defaultListener);
        }
        AlertDialog messageDialog = builder.show();
        dialogMap.put(MESSAGEDIALOG, messageDialog);
    }

    default void dimessMessageDialog() {
        dimessDialog(dialogMap.get(MESSAGEDIALOG));
    }

    default void showListDialog(Context context, CharSequence[] items, DialogInterface.OnClickListener listener) {
        if (dialogMap.containsKey(LISTDIALOG)) {
            AlertDialog listDialog = dialogMap.get(LISTDIALOG);
            if (listDialog.isShowing())
                listDialog.dismiss();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setNegativeButton(context.getText(R.string.dialog_cancel), defaultListener);
        builder.setItems(items,listener);
        AlertDialog listDialog = builder.show();
        dialogMap.put(LISTDIALOG, listDialog);
    }

    default void dimessListDialog(){
        dimessDialog(dialogMap.get(LISTDIALOG));
    }

    default void showEditTextDialog(Context context, String title, DialogInterface.OnClickListener listener){
        if (dialogMap.containsKey(EDITTEXTDIALOG)) {
            AlertDialog listDialog = dialogMap.get(LISTDIALOG);
            if (listDialog.isShowing())
                listDialog.dismiss();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setPositiveButton(context.getText(R.string.dialog_ok1), listener);
        builder.setNegativeButton(context.getText(R.string.dialog_cancel), defaultListener);

        EditText input = new EditText(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(layoutParams);
        builder.setView(input);

        AlertDialog editTextDialog = builder.show();
        dialogMap.put(EDITTEXTDIALOG, editTextDialog);
    }

    default void dimessEditTextDialog(){
        dimessDialog(dialogMap.get(EDITTEXTDIALOG));
    }

    default void dimessDialog(AlertDialog alertDialog) {
        if (alertDialog != null)
            if (!alertDialog.isShowing())
                alertDialog.dismiss();
    }
}
