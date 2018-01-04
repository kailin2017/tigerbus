package com.tigerbus.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.*;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.tigerbus.R;
import com.tigerbus.base.BaseActivity;
import com.tigerbus.base.ViewState;
import com.tigerbus.base.annotation.ActivityView;
import com.tigerbus.base.annotation.ViewInject;
import com.tigerbus.base.annotation.event.onClick;
import com.tigerbus.base.log.TlogType;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

@ActivityView(layout = R.layout.main_activity)
public final class MainActivity extends BaseActivity<MainView, MainPresenter>
        implements MainView, NavigationView.OnNavigationItemSelectedListener {

    private final static String TAG = MainActivity.class.getSimpleName();
    private Disposable disposable;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.drawer_layout)
    private DrawerLayout drawer;
    @ViewInject(R.id.nav_view)
    private NavigationView navigationView;
    @ViewInject(R.id.recyclerview)
    private RecyclerView recyclerView;

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
        disposableDisposed();
    }

    protected void disposableDisposed() {
        if (disposable.isDisposed())
            disposable.dispose();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        drawer.addDrawerListener(toggle);
    }

    @Override
    public Observable<Boolean> getInitDataSubject() {
        return Observable.just(true);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            renderFinish();
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.nav_camera:
                break;
            case R.id.nav_gallery:
                break;
            case R.id.nav_slideshow:
                break;
            case R.id.nav_manage:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_send:
                break;
        }
        return true;
    }

    @Override
    public void render(ViewState viewState) {
        if (viewState instanceof MainViewState.Loading) {
            renderLoading(((MainViewState.Loading) viewState).disposable());
        } else if (viewState instanceof MainViewState.Exception) {
            renderException(((MainViewState.Exception) viewState).error());
        } else if (viewState instanceof MainViewState.Success) {
            renderSuccess(((MainViewState.Success) viewState).bundle());
        } else if (viewState instanceof MainViewState.Finish) {
            renderFinish();
        }
    }

    private void renderLoading(Disposable disposable) {
        showProgress();
        this.disposable = disposable;
    }

    private void renderException(String error) {
        application.printLog(TlogType.debug, TAG, error);
    }

    private void renderSuccess(Bundle bundle) {

    }

    private void renderFinish() {
        dimessProgress();
        disposableDisposed();
    }
}
