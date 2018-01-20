package com.tigerbus.ui.main;

import android.content.Intent;
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
import com.tigerbus.base.BaseActivity;
import com.tigerbus.base.ViewStateRender;
import com.tigerbus.base.annotation.ActivityView;
import com.tigerbus.base.annotation.ViewInject;
import com.tigerbus.ui.main.sub.HomeFragment;
import com.tigerbus.ui.main.sub.RemindFragment;
import com.tigerbus.ui.search.SearchRouteActivity;

import io.reactivex.Observable;

@ActivityView(layout = R.layout.main_activity)
public final class MainActivity extends BaseActivity<MainView, MainPresenter>
        implements MainView<ViewStateRender>, ViewStateRender<Bundle>{

    private final static String TAG = MainActivity.class.getSimpleName();
    private final static int MAINVIEWID = R.id.mainview;
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.drawer_layout)
    private DrawerLayout drawer;
    @ViewInject(R.id.drawer_nav_view)
    private NavigationView navigationView;
    @ViewInject(R.id.bottom_nav_view)
    private BottomNavigationView bottomNavigationView;

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

    private void initView() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        drawer.addDrawerListener(toggle);

        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

        goHome();
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
//        nextFragment(MAINVIEWID, SearchRouteFragment.newInstance());
        startActivity(SearchRouteActivity.class);
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
            super.onBackPressed();
        }
    }

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
        showProgress();
    }

    @Override
    public void renderSuccess(Bundle result) {
    }

    @Override
    public void renderFinish() {
        dimessProgress();
    }
}
