package com.tigerbus.base;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.app.ActivityCompat;
import android.view.KeyEvent;

import com.tigerbus.TigerApplication;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public abstract class BaseActivity<V extends BaseView, P extends BasePresenter<V>>
        extends MvpActivity<V, P> implements BaseUIInterface {

    public final static int PRIMISSION_SUCCESS = 0;
    private final static String[] primissionList = new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION};
    public TigerApplication application;
    public ProgressDialog progressDialog;
    public AlertDialog messageDialog;
    public AlertDialog listDialog;
    public Context context;
    private OnPrimissionListener onPrimissionListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        application = (TigerApplication) getApplication();
        context = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStop() {
        if (presenter != null)
            presenter.clearUiDisposable();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null)
            presenter.clearDisposable();
        super.onDestroy();
    }

    public void checkPrimission(@NonNull OnPrimissionListener onPrimissionListener) {
        this.onPrimissionListener = onPrimissionListener;
        for (String primission : primissionList) {
            if (ActivityCompat.checkSelfPermission(context, primission) == PackageManager.PERMISSION_DENIED) {
                requestPermissions();
                return;
            }
        }
        onPrimissionListener.onSuccess();
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, primissionList, PRIMISSION_SUCCESS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PRIMISSION_SUCCESS:
                for (int result : grantResults) {
                    if (result == PackageManager.PERMISSION_DENIED) {
                        onPrimissionListener.onFail();
                        return;
                    }
                }
                onPrimissionListener.onSuccess();
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    protected void nextFragment(@NonNull int viewId, @NonNull Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        fragmentTransaction.replace(viewId,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
    public void showListAlert(CharSequence[] items, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(items, listener);
        listDialog = builder.create();
    }

    @Override
    public void dimessListAlert() {
        if (listDialog != null)
            if (listDialog.isShowing())
                listDialog.dismiss();
    }

    protected interface OnPrimissionListener {
        void onSuccess();

        void onFail();
    }
}
