package com.tigerbus.app;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.app.ActivityCompat;
import android.view.inputmethod.InputMethodManager;

import com.tigerbus.TigerApplication;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public abstract class BaseActivity<V extends BaseView, P extends BasePresenter<V>>
        extends MvpActivity<V, P> implements BaseUIInterface, TigerDialog {

    protected final static int PRIMISSION_SUCCESS = 0;
    private final static String[] primissionList = new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION};
    protected TigerApplication application;
    protected Context context;
    private OnPrimissionListener onPrimissionListener;
    private InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        application = (TigerApplication) getApplication();
        context = this;
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
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
//        inputMethodManager.hideSoftInputFromWindow(
//                getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        fragmentTransaction.replace(viewId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
