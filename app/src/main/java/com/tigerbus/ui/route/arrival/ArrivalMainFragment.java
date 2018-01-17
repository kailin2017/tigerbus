package com.tigerbus.ui.route.arrival;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.tigerbus.R;
import com.tigerbus.base.BaseFragment;
import com.tigerbus.base.ViewStateRender;
import com.tigerbus.base.annotation.FragmentView;
import com.tigerbus.base.annotation.ViewInject;
import com.tigerbus.data.CityBusService;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusStopOfRoute;
import com.tigerbus.data.bus.BusSubRoute;
import com.tigerbus.ui.route.adapter.ArrivalPagerAdapter;
import com.tigerbus.ui.route.adapter.ArrivalRecyclerObj;
import com.tigerbus.ui.route.adapter.ArrivalRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

@FragmentView(layout = R.layout.route_arrival_fragment)
public final class ArrivalMainFragment extends BaseFragment<ArrivalView, ArrivalPresenter>
        implements ArrivalView, ViewStateRender<Bundle> {

    protected PublishSubject<ArrayList<BusEstimateTime>> publishSubject = PublishSubject.create();
    @ViewInject(R.id.tablayout)
    protected TabLayout tabLayout;
    @ViewInject(R.id.viewpager)
    protected ViewPager viewPager;

    public static ArrivalMainFragment newInstance(@NonNull Bundle bundle) {
        ArrivalMainFragment arrivalMainFragment = new ArrivalMainFragment();
        arrivalMainFragment.setArguments(bundle);
        return arrivalMainFragment;
    }

    @Override
    public void setEstimateSubject(@NonNull PublishSubject<ArrayList<BusEstimateTime>> estimateSubject) {
        this.publishSubject = estimateSubject;
    }

    @Override
    public ArrivalPresenter createPresenter() {
        return createArrivalPresenter();
    }


    @Override
    public Observable<Bundle> initDataIntent() {
        return bundle2Obserable(getArguments());
    }


    @Override
    public void renderLoading() {
        showProgress();
    }

    @Override
    public void renderSuccess(Bundle bundle) {
        ArrayList<ArrivalRecyclerObj> objects = new ArrayList<>();
        HashMap<String, BusStopOfRoute> busStopOfRouteMap = (HashMap<String, BusStopOfRoute>) bundle.getSerializable(CityBusService.BUS_STOP_OF_ROUTE);
        BusRoute route = bundle.getParcelable(CityBusService.BUS_ROUTE);

        for (BusSubRoute subRoute : route.getSubRoutes()) {
            BusStopOfRoute busStopOfRoute = busStopOfRouteMap.get(getKey(subRoute));
            ArrivalRecyclerAdapter arrivalRecyclerAdapter = new ArrivalRecyclerAdapter(busStopOfRoute, publishSubject);
            objects.add(new ArrivalRecyclerObj(getTitle(context, route, subRoute), arrivalRecyclerAdapter, context));
            presenter.addDisposable(arrivalRecyclerAdapter.getDiaposable());
            presenter.addDisposable(arrivalRecyclerAdapter.getClickSubject().subscribe(stop -> {}));
        }

        publishSubject.onNext(bundle.getParcelableArrayList(CityBusService.BUS_ESTIMATE_TIME));
        initView(viewPager, tabLayout, new ArrivalPagerAdapter(tabLayout, objects));
    }

    @Override
    public void renderFinish() {
        dimessMessage();
    }
}
