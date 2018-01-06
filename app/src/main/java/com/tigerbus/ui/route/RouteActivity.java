package com.tigerbus.ui.route;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.tigerbus.R;
import com.tigerbus.base.BaseActivity;
import com.tigerbus.base.ViewStateRender;
import com.tigerbus.base.annotation.ActivityView;
import com.tigerbus.base.annotation.ViewInject;
import com.tigerbus.data.bus.BusData;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.CityBusService;
import com.tigerbus.data.bus.BusStopOfRoute;

import java.util.ArrayList;

@ActivityView(layout = R.layout.route_activity)
public final class RouteActivity extends BaseActivity<RouteView, RoutePresenter>
        implements RouteView<ViewStateRender>, ViewStateRender<ArrayList<BusData>> {

    private final static int routeViewId = R.id.routeview;
    private BusRoute busRoute;
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initItem();
        initView();
    }

    protected void initItem() {
        busRoute = getIntent().getParcelableExtra(CityBusService.BUS_ROUTE);
    }

    protected void initView() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_break);
        toolbar.setNavigationOnClickListener(v -> onDestroy());
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.route_info:
                    break;
                case R.id.route_map:
                    break;
            }
            return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.route_toolbar, menu);
        return true;
    }


    @NonNull
    @Override
    public RoutePresenter createPresenter() {
        return new RoutePresenter();
    }

    @Override
    public void renderLoading() {
        showProgress();
    }

    @Override
    public void renderSuccess(ArrayList<BusData> result) {
        BusData busData = result.get(0);
        if (busData instanceof BusStopOfRoute) {

        } else if (busData instanceof BusEstimateTime) {

        }
    }

    @Override
    public void renderFinish() {
        dimessProgress();
    }
}
