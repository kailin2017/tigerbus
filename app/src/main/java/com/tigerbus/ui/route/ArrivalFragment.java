package com.tigerbus.ui.route;

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

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

@FragmentView(layout = R.layout.route_arrival_fragment)
public final class ArrivalFragment extends BaseFragment<ArrivalView, ArrivalPresenter>
        implements ArrivalView, ViewStateRender<Bundle> {

    private PublishSubject<ArrayList<BusEstimateTime>> publishSubject = PublishSubject.create();
    @ViewInject(R.id.tablayout)
    private TabLayout tabLayout;
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;

    public static ArrivalFragment newInstance(@NonNull Bundle bundle) {
        ArrivalFragment arrivalFragment = new ArrivalFragment();
        arrivalFragment.setArguments(bundle);
        return arrivalFragment;
    }

    public void setPublishSubject(@NonNull PublishSubject<ArrayList<BusEstimateTime>> publishSubject) {
        this.publishSubject = publishSubject;
    }

    @Override
    public ArrivalPresenter createPresenter() {
        return new ArrivalPresenter();
    }


    @Override
    public Observable<Bundle> initDataIntent() {
        return Observable.just(getArguments());
    }


    // presenter & ui元件在super.onCreateView完成後才會實體化
    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void renderLoading() {

    }

    @Override
    public void renderSuccess(Bundle bundle) {
        ArrayList<ArrivalPagerObject> objects = new ArrayList<>();
        HashMap<String, BusStopOfRoute> busStopOfRouteMap = (HashMap<String, BusStopOfRoute>) bundle.getSerializable(CityBusService.BUS_STOP_OF_ROUTE);
        BusRoute route = bundle.getParcelable(CityBusService.BUS_ROUTE);

        for (BusSubRoute subRoute : route.getSubRoutes()) {
            BusStopOfRoute busStopOfRoute = busStopOfRouteMap.get(getKey(subRoute));
            ArrivalRecyclerAdapter arrivalRecyclerAdapter = new ArrivalRecyclerAdapter(busStopOfRoute, publishSubject);
            objects.add(new ArrivalPagerObject(getTitle(context, route, subRoute), arrivalRecyclerAdapter, context));
            presenter.addDisposable(arrivalRecyclerAdapter.getDiaposable());
        }

        publishSubject.onNext(bundle.getParcelableArrayList(CityBusService.BUS_ESTIMATE_TIME));
        initView(objects);
    }

    private void initView(ArrayList<ArrivalPagerObject> objects) {
        ArrivalPagerAdapter adapter = new ArrivalPagerAdapter( tabLayout, objects);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void renderFinish() {

    }
}
