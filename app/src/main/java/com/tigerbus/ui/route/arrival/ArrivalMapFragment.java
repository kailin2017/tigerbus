package com.tigerbus.ui.route.arrival;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tigerbus.R;
import com.tigerbus.base.BaseFragment;
import com.tigerbus.base.ViewStateRender;
import com.tigerbus.base.annotation.FragmentView;
import com.tigerbus.base.annotation.ViewInject;
import com.tigerbus.data.CityBusInterface;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusStopOfRoute;
import com.tigerbus.data.bus.BusSubRoute;
import com.tigerbus.ui.route.adapter.ArrivalMapAdapter;
import com.tigerbus.ui.route.adapter.MapObj;
import com.tigerbus.ui.route.adapter.ArrivalPagerMapAdapter;
import com.tigerbus.util.LocationUtil;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

@FragmentView(layout = R.layout.route_arrival_fragment)
public final class ArrivalMapFragment extends BaseFragment<ArrivalMapView, ArrivalMapPresenter>
        implements ArrivalMapView, ViewStateRender<Bundle>, LocationUtil.LocationStatusListener {

    protected PublishSubject<ArrayList<BusEstimateTime>> estimateSubject = PublishSubject.create();
    protected PublishSubject<Location> locationSubject = PublishSubject.create();
    @ViewInject(R.id.tablayout)
    protected TabLayout tabLayout;
    @ViewInject(R.id.viewpager)
    protected ViewPager viewPager;

    public static ArrivalMapFragment newInstance(@NonNull Bundle bundle) {
        ArrivalMapFragment arrivalFragment = new ArrivalMapFragment();
        arrivalFragment.setArguments(bundle);
        return arrivalFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LocationUtil locationUtil = LocationUtil.getInstance(getContext());
        locationUtil.initLocation(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setEstimateSubject(@NonNull PublishSubject<ArrayList<BusEstimateTime>> estimateSubject) {
        this.estimateSubject = estimateSubject;
    }

    @Override
    public ArrivalMapPresenter createPresenter() {
        return new ArrivalMapPresenter();
    }


    @Override
    public Observable<Bundle> bindInitData() {
        return bundle2Obserable(getArguments());
    }

    @Override
    public void renderLoading() {
        showProgress();
    }

    @Override
    public void renderSuccess(Bundle bundle) {
        ArrayList<MapObj> objects = new ArrayList<>();
        HashMap<String, BusStopOfRoute> busStopOfRouteMap = (HashMap<String, BusStopOfRoute>) bundle.getSerializable(CityBusInterface.BUS_STOP_OF_ROUTE);
        BusRoute route = bundle.getParcelable(CityBusInterface.BUS_ROUTE);

        for (BusSubRoute subRoute : route.getSubRoutes()) {
            BusStopOfRoute busStopOfRoute = busStopOfRouteMap.get(getKey(subRoute));
            ArrivalMapAdapter arrivalMapAdapter =
                    new ArrivalMapAdapter(context, busStopOfRoute, estimateSubject, locationSubject);
            objects.add(new MapObj(getTitle(context, route, subRoute), arrivalMapAdapter));
            presenter.addDisposable(arrivalMapAdapter.getDiaposables());
        }

        estimateSubject.onNext(bundle.getParcelableArrayList(CityBusInterface.BUS_ESTIMATE_TIME));

        ArrivalPagerMapAdapter adapter = new ArrivalPagerMapAdapter(getFragmentManager(), tabLayout, objects);
        initTabPager(viewPager, tabLayout, adapter);
    }

    @Override
    public void renderFinish() {
        dimessProgress();
    }

    @Override
    public void onLocationChanged(Location location) {
        locationSubject.onNext(location);
    }
}
