package com.tigerbus.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tigerbus.R;
import com.tigerbus.app.BaseActivity;
import com.tigerbus.app.ViewStateRender;
import com.tigermvp.annotation.ActivityView;
import com.tigermvp.annotation.ViewInject;
import com.tigerbus.service.RemindService;
import com.tigerbus.ui.main.memento.CareTaker;
import com.tigerbus.ui.main.memento.Memento;
import com.tigerbus.ui.main.memento.Originator;
import com.tigerbus.ui.main.sub.HomeFragment;
import com.tigerbus.ui.main.sub.RemindFragment;
import com.tigerbus.ui.main.sub.SearchRouteFragment;

import io.reactivex.Observable;

@ActivityView(layout = R.layout.main_activity)
public final class MainActivity extends BaseActivity<MainView, MainPresenter>
        implements MainView<ViewStateRender>, ViewStateRender<Bundle> {

    private final static String TAG = MainActivity.class.getSimpleName();
    private final static int MAINVIEWID = R.id.mainview;
    private final CareTaker careTaker = new CareTaker();
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.drawer_layout)
    private DrawerLayout drawer;
    @ViewInject(R.id.drawer_nav_view)
    private NavigationView leftNavView;
    @ViewInject(R.id.bottom_nav_view)
    private BottomNavigationView bottomNavView;

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initService();
    }

    private void initView() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        drawer.addDrawerListener(toggle);

        leftNavView.setNavigationItemSelectedListener(this::onLeftNavSelection);
        bottomNavView.setOnNavigationItemSelectedListener(this::onBottomNavSelection);
        goHome();
        careTaker.add(new Originator(R.id.home).save());
    }

    private void initService() {
        startService(context, RemindService.class, new Bundle());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public Observable<Boolean> getInitDataSubject() {
        return Observable.just(true);
    }

    @Override
    public void goHome() {
        nextFragment(MAINVIEWID, HomeFragment.newInstance());
    }

    @Override
    public void goSearch() {
        nextFragment(MAINVIEWID, SearchRouteFragment.newInstance());
    }

    @Override
    public void goRemind() {
        nextFragment(MAINVIEWID, RemindFragment.newInstance());
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            renderFinish();
            Memento backMemento = careTaker.popLast();
            if (backMemento != null)
                bottomNavView.setSelectedItemId(backMemento.getItemId());
            super.onBackPressed();
        }
    }

    public boolean onLeftNavSelection(MenuItem item) {
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

    public boolean onBottomNavSelection(MenuItem item) {
        int itemId = item.getItemId();
        careTaker.add(new Originator(itemId).save());
        switch (itemId) {
            case R.id.home:
                goHome();
                break;
            case R.id.search:
                goSearch();
                break;
            case R.id.remind:
                goRemind();
                break;
        }
        return true;
    }

    @Override
    public void renderLoading() {
        showProgressDialog(context);
    }

    @Override
    public void renderSuccess(Bundle result) {
    }

    @Override
    public void renderFinish() {
        dimessProgressDialog();
    }
}
