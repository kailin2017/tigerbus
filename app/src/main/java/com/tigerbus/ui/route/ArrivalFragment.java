package com.tigerbus.ui.route;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tigerbus.R;
import com.tigerbus.base.BaseFragment;
import com.tigerbus.base.annotation.FragmentView;
import com.tigerbus.base.annotation.ViewInject;
import com.tigerbus.data.CityBusService;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusStopOfRoute;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

@FragmentView(layout = R.layout.route_arrival_fragment)
public final class ArrivalFragment extends BaseFragment<ArrivalView, ArrivalPresenter> implements ArrivalView {

    private PublishSubject<ArrayList<BusStopOfRoute>> stopOfRouteSubject;
    private PublishSubject<ArrayList<BusEstimateTime>> estimateSubject;

    @ViewInject(R.id.tablayout)
    private TabLayout tabLayout;
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;
    private BusRoute busRoute;

    public static ArrivalFragment newInstance(BusRoute busRoute) {
        Bundle args = new Bundle();
        args.putParcelable(CityBusService.BUS_ROUTE, busRoute);
        ArrivalFragment fragment = new ArrivalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        busRoute = bundle.getParcelable(CityBusService.BUS_ROUTE);
    }

    @Override
    public ArrivalPresenter createPresenter() {
        return new ArrivalPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (stopOfRouteSubject != null)
            presenter.addDisposable(stopOfRouteSubject.subscribe(busStopOfRoutes ->
                    viewPager.setAdapter(new ArrivalPagerAdapter(context, busRoute, busStopOfRoutes))));
        if (estimateSubject != null)
            presenter.addDisposable(estimateSubject.subscribe());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setSubject(
            PublishSubject<ArrayList<BusStopOfRoute>> stopOfRouteSubject,
            PublishSubject<ArrayList<BusEstimateTime>> estimateSubject) {
        this.stopOfRouteSubject = stopOfRouteSubject;
        this.estimateSubject = estimateSubject;
    }
}
