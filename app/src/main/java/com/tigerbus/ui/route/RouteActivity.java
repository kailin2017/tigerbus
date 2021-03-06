package com.tigerbus.ui.route;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.tigerbus.R;
import com.tigerbus.app.BaseActivity;
import com.tigerbus.app.OnPrimissionListener;
import com.tigerbus.app.ViewStateRender;
import com.tigermvp.annotation.ActivityView;
import com.tigermvp.annotation.ViewInject;
import com.tigerbus.data.CityBusInterface;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.sqlite.data.RouteStop;
import com.tigerbus.ui.route.arrival.ArrivalInfoFragment;
import com.tigerbus.ui.route.arrival.ArrivalMainFragment;
import com.tigerbus.ui.route.arrival.ArrivalMapFragment;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

@ActivityView(layout = R.layout.route_activity)
public final class RouteActivity extends BaseActivity<RouteView, RoutePresenter>
        implements RouteView<ViewStateRender>, ViewStateRender<Bundle>, RouteInterface {

    private final static int routeViewId = R.id.routeview;
    private final PublishSubject<BusRoute> bindBusRouteSubject = PublishSubject.create();
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
        getSupportActionBar().setTitle(busRoute.getRouteName().getZh_tw());
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
        return new RoutePresenter();
    }


    @Override
    public void renderSuccess(Bundle bundle) {
        this.initResut = bundle;
        goArrival();
    }

    private void goArrival() {
        ArrivalMainFragment arrivalFragment = ArrivalMainFragment.newInstance(initResut);
        nextFragment(routeViewId, arrivalFragment);
    }

    private void goArrivalInfo() {
        ArrivalInfoFragment arrivalInfoFragment = ArrivalInfoFragment.newInstance(busRoute);
        nextFragment(routeViewId, arrivalInfoFragment);
    }

    private void goArrivalMap() {
        goArrivalMap(initResut);
    }

    private void goArrivalMap(Bundle bundle) {
        ArrivalMapFragment arrivalMapFragment = ArrivalMapFragment.newInstance(bundle);
        nextFragment(routeViewId, arrivalMapFragment);
    }

    @Override
    public void goArrivalMap(RouteStop routeStop) {
        Bundle bundle = new Bundle();
        bundle.putAll(initResut);
        bundle.putParcelable(RouteStop.TAG, routeStop);
        goArrivalMap(bundle);
    }


}
