package com.tigerbus.ui.route;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.tigerbus.R;
import com.tigerbus.base.BaseActivity;
import com.tigerbus.base.ViewState;
import com.tigerbus.base.ViewStateRender;
import com.tigerbus.base.annotation.ActivityView;
import com.tigerbus.base.annotation.ViewInject;
import com.tigerbus.data.CityBusService;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusStopOfRoute;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

@ActivityView(layout = R.layout.route_activity)
public final class RouteActivity extends BaseActivity<RouteView, RoutePresenter>
        implements RouteView<ViewStateRender>, ViewStateRender<Object> {

    private final static int routeViewId = R.id.routeview;
    private final PublishSubject<BusRoute> bindBusRouteSubject = PublishSubject.create();
    private final PublishSubject<ArrayList<BusStopOfRoute>> stopOfRouteSubject = PublishSubject.create();
    private final PublishSubject<ArrayList<BusEstimateTime>> estimateSubject = PublishSubject.create();
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
        toolbar.setTitle(busRoute.getRouteName().getZh_tw());
        toolbar.setSubtitle(busRoute.getDepartureStopNameZh() + "-" + busRoute.getDestinationStopNameZh());
        toolbar.setNavigationIcon(R.drawable.ic_break);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.route_info:
                    break;
                case R.id.route_map:
                    break;
            }
            return false;
        });
        ArrivalFragment arrivalFragment = ArrivalFragment.newInstance(busRoute);
        getFragmentManager().beginTransaction().replace(routeViewId, arrivalFragment);
    }

    @Override
    public Observable<BusRoute> bindStopOfRoute() {
        return bindBusRouteSubject;
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindBusRouteSubject.onNext(busRoute);
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
    public void render(ViewState viewState) {

    }

    @Override
    public void renderLoading() {
        showProgress();
    }

    @Override
    public void renderSuccess(Object result) {
        if (result instanceof ArrayList) {
            Object object = ((ArrayList) result).get(0);
            if (object instanceof BusStopOfRoute) {
                stopOfRouteSubject.onNext((ArrayList<BusStopOfRoute>) result);
            } else if (object instanceof BusEstimateTime) {
                estimateSubject.onNext((ArrayList<BusEstimateTime>) result);
            } else {
                renderException("Error Data!!!");
            }
        }
    }

    @Override
    public void renderFinish() {
        dimessProgress();
    }




}
