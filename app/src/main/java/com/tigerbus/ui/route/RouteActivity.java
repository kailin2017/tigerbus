package com.tigerbus.ui.route;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.tigerbus.R;
import com.tigerbus.base.BaseActivity;
import com.tigerbus.base.ViewStateRender;
import com.tigerbus.base.annotation.ActivityView;
import com.tigerbus.base.annotation.ViewInject;
import com.tigerbus.data.CityBusInterface;
import com.tigerbus.data.CityBusService;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.ui.route.arrival.ArrivalInfoFragment;
import com.tigerbus.ui.route.arrival.ArrivalMainFragment;
import com.tigerbus.ui.route.arrival.ArrivalMapFragment;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

@ActivityView(layout = R.layout.route_activity)
public final class RouteActivity extends BaseActivity<RouteView, RoutePresenter>
        implements RouteView<ViewStateRender>, ViewStateRender<Bundle> {

    private final static int routeViewId = R.id.routeview;
    private final PublishSubject<BusRoute> bindBusRouteSubject = PublishSubject.create();
    private final PublishSubject<ArrayList<BusEstimateTime>> estimateSubject = PublishSubject.create();
    private final PublishSubject<Bundle> busA1Data = PublishSubject.create();
    private final PublishSubject<Bundle> busA2Data = PublishSubject.create();
    private BusRoute busRoute;
    private Bundle initResut;

    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    private OnPrimissionListener onPrimissionListener = new OnPrimissionListener() {
        @Override
        public void onSuccess() {
            goArrivalMap();
        }

        @Override
        public void onFail() {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initItem();
        initView();
    }

    protected void initItem() {
        busRoute = getIntent().getParcelableExtra(CityBusInterface.BUS_ROUTE);
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
                    goArrivalInfo();
                    break;
                case R.id.route_map:
                    checkPrimission(onPrimissionListener);
                    break;
            }
            return false;
        });
    }


    @Override
    public Observable<BusRoute> bindIntent() {
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
        return new RoutePresenter(estimateSubject);
    }

    @Override
    public void renderLoading() {
        showProgress();
    }

    @Override
    public void renderSuccess(Bundle bundle) {
        this.initResut = bundle;
        goArrival();
    }

    @Override
    public void renderFinish() {
        dimessProgress();
    }

    private void goArrival() {
        ArrivalMainFragment arrivalFragment = ArrivalMainFragment.newInstance(initResut);
        arrivalFragment.setEstimateSubject(estimateSubject);
        nextFragment(routeViewId, arrivalFragment);
    }

    private void goArrivalInfo() {
        ArrivalInfoFragment arrivalInfoFragment = ArrivalInfoFragment.newInstance(busRoute);
        nextFragment(routeViewId, arrivalInfoFragment);
    }

    private void goArrivalMap() {
        ArrivalMapFragment arrivalMapFragment = ArrivalMapFragment.newInstance(initResut);
        arrivalMapFragment.setEstimateSubject(estimateSubject);
        nextFragment(routeViewId, arrivalMapFragment);
    }



}
