package com.tigerbus.app;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public interface TigerDialog {

    String PROGRESSDIALOG = "PROGRESSDIALOG";
    String MESSAGEDIALOG = "MESSAGEDIALOG";
    String LISTDIALOG = "LISTDIALOG";
    String MULTICHOICEDIALOG = "MULTICHOICEDIALOG";
    String EDITTEXTDIALOG = "EDITTEXTDIALOG";

    HashMap<String, AlertDialog> dialogMap = new HashMap<>();
    DialogInterface.OnClickListener defaultListener = (dimess, i) -> dimess.dismiss();

    default void showProgressDialog(Context context) {
        if (!dialogMap.containsKey(PROGRESSDIALOG)) {
            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            dialogMap.put(PROGRESSDIALOG, progressDialog);
        }
        ProgressDialog progressDialog = (ProgressDialog) dialogMap.get(PROGRESSDIALOG);
        if (!progressDialog.isShowing())
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
            builder.setPositiveButton(context.getText(com.tigerbus.R.string.dialog_ok1), defaultListener);
        } else {
            builder.setPositiveButton(context.getText(com.tigerbus.R.string.dialog_ok1), listener);
            builder.setNegativeButton(context.getText(com.tigerbus.R.string.dialog_add), defaultListener);
        }
        AlertDialog messageDialog = builder.show();
        dialogMap.put(MESSAGEDIALOG, messageDialog);
    }

    default void dimessMessageDialog() {
        dimessDialog(dialogMap.get(MESSAGEDIALOG));
    }

    default void showMultiChoiceItems(Context context, CharSequence[] items, MultiChoiceDialogListener listener) {
        if (dialogMap.containsKey(MULTICHOICEDIALOG)) {
            AlertDialog listDialog = dialogMap.get(MULTICHOICEDIALOG);
            if (listDialog.isShowing())
                listDialog.dismiss();
        }
        ArrayList<Integer> selectionItems = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMultiChoiceItems(items, null, (dialog, indexSelect, isChicked) -> {
            if (isChicked) {
                selectionItems.add(indexSelect);
            } else {
                if (selectionItems.contains(indexSelect))
                    selectionItems.remove(indexSelect);
            }
        });
        builder.setNegativeButton(context.getText(com.tigerbus.R.string.dialog_cancel), defaultListener);
        builder.setPositiveButton(context.getText(com.tigerbus.R.string.dialog_ok1), (d, i) -> {
            ArrayList<CharSequence> results = new ArrayList<>();
            for (int integer : selectionItems) {
                results.add(items[integer]);
            }
            listener.onChoice(results);
        });
    }

    default void dimessMultiChoiceItems() {
        dimessDialog(dialogMap.get(MULTICHOICEDIALOG));
    }

    default void showListDialog(Context context, CharSequence[] items, DialogInterface.OnClickListener listener) {
        if (dialogMap.containsKey(LISTDIALOG)) {
            AlertDialog listDialog = dialogMap.get(LISTDIALOG);
            if (listDialog.isShowing())
                listDialog.dismiss();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setNegativeButton(context.getText(com.tigerbus.R.string.dialog_cancel), defaultListener);
        builder.setItems(items, listener);
        AlertDialog listDialog = builder.show();
        dialogMap.put(LISTDIALOG, listDialog);
    }

    default void dimessListDialog() {
        dimessDialog(dialogMap.get(LISTDIALOG));
    }

    default void showEditTextDialog(Context context, String title, EditTextDialogAddListener listener) {
        if (!dialogMap.containsKey(EDITTEXTDIALOG)) {
            AlertDialog listDialog = dialogMap.get(LISTDIALOG);
            if (listDialog.isShowing())
                listDialog.dismiss();
        }
        EditText input = new EditText(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(layoutParams);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(input);
        builder.setTitle(title);
        builder.setNegativeButton(context.getText(com.tigerbus.R.string.dialog_cancel), defaultListener);
        builder.setPositiveButton(context.getText(com.tigerbus.R.string.dialog_ok1), (d, i) -> {
            d.dismiss();
            listener.onAdd(input.getText().toString());
        });

        AlertDialog editTextDialog = builder.show();
        dialogMap.put(EDITTEXTDIALOG, editTextDialog);
    }

    default void dimessEditTextDialog() {
        dimessDialog(dialogMap.get(EDITTEXTDIALOG));
    }

    default void dimessDialog(AlertDialog alertDialog) {
        if (alertDialog != null)
            if (alertDialog.isShowing())
                alertDialog.dismiss();
    }

    @FunctionalInterface
    interface EditTextDialogAddListener {

        void onAdd(String string);
    }

    @FunctionalInterface
    interface MultiChoiceDialogListener {

        void onChoice(ArrayList<CharSequence> selectionItems);
    }
}
