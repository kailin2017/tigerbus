package com.tigerbus.ui.route;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
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

import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

@FragmentView(layout = R.layout.route_arrival_fragment)
public final class ArrivalFragment extends BaseFragment<ArrivalView, ArrivalPresenter> implements ArrivalView {

//    private PublishSubject<ArrayList<BusStopOfRoute>> stopOfRouteSubject;
//    private PublishSubject<ArrayList<BusEstimateTime>> estimateSubject;
    private PublishSubject<Boolean> onCreateViewSubject = PublishSubject.create();
    private Disposable onCreateViewSubjectDisposable;

    @ViewInject(R.id.tablayout)
    private TabLayout tabLayout;
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;
    private BusRoute busRoute;

    public static ArrivalFragment newInstance(@NonNull BusRoute busRoute) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(CityBusService.BUS_ROUTE, busRoute);
        ArrivalFragment arrivalFragment = new ArrivalFragment();
        arrivalFragment.setArguments(bundle);
        return arrivalFragment;
    }

    @Override
    public ArrivalPresenter createPresenter() {
        return new ArrivalPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        busRoute = getArguments().getParcelable(CityBusService.BUS_ROUTE);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // presenter & ui元件在super.onCreateView完成後才會實體化
    @Override
    public void onStart() {
        super.onStart();
        initView();
        onCreateViewSubject.onNext(true);
    }

    protected void initView(){
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
    public void setSubject(
            PublishSubject<ArrayList<BusStopOfRoute>> stopOfRouteSubject,
            PublishSubject<ArrayList<BusEstimateTime>> estimateSubject) {
        onCreateViewSubjectDisposable = onCreateViewSubject.filter(b -> b).subscribe(b -> {
            Disposable stopOfRouteSubjectDisposable = stopOfRouteSubject.subscribe(
                    busStopOfRoutes -> viewPager.setAdapter(new ArrivalPagerAdapter(context, tabLayout,busRoute, busStopOfRoutes)));
            Disposable estimateSubjectDisposable = estimateSubject.subscribe(busStopOfRoutes -> {

            });
            presenter.addDisposable(stopOfRouteSubjectDisposable);
            presenter.addDisposable(estimateSubjectDisposable);
            presenter.addDisposable(onCreateViewSubjectDisposable);
        });

    }
}
