package com.tigerbus.ui.route.arrival;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.TextView;

import com.tigerbus.R;
import com.tigerbus.base.BaseFragment;
import com.tigerbus.base.ViewStateRender;
import com.tigerbus.base.annotation.FragmentView;
import com.tigerbus.base.annotation.ViewInject;
import com.tigerbus.data.CityBusInterface;
import com.tigerbus.data.CityBusService;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusStopOfRoute;
import com.tigerbus.data.bus.BusSubRoute;
import com.tigerbus.data.bus.RouteStop;
import com.tigerbus.service.RemindService;
import com.tigerbus.ui.route.adapter.ArrivalRecyclerAdapter;
import com.tigerbus.ui.widget.PagerRecyclerAdapter;
import com.tigerbus.ui.widget.PagerRecyclerObj;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

@FragmentView(layout = R.layout.route_arrival_fragment)
public final class ArrivalMainFragment extends BaseFragment<ArrivalMainView, ArrivalMainPresenter>
        implements ArrivalMainView, ViewStateRender<Bundle> {

    private PublishSubject<ArrayList<BusEstimateTime>> publishSubject = PublishSubject.create();
    private PublishSubject<RouteStop> stopSubject = PublishSubject.create();
    private BottomSheetBehavior bottomSheetBehavior;
    private BusRoute busRoute;

    @ViewInject(R.id.tablayout)
    private TabLayout tabLayout;
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.bottom_sheet)
    private NestedScrollView sheetView;

    @ViewInject(R.id.sheetitem_remind)
    private TextView remidSheetItem;
    @ViewInject(R.id.sheetitem_station_save)
    private TextView stationSaveSheetItem;
    @ViewInject(R.id.sheetitem_station_bus)
    private TextView stationAllBusSheetItem;
    @ViewInject(R.id.sheetitem_station_lcaotion)
    private TextView stasionLocationSheetItem;
    @ViewInject(R.id.sheetitem_station_view)
    private TextView stationViewSheetItem;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        busRoute = getArguments().getParcelable(CityBusInterface.BUS_ROUTE);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public ArrivalMainPresenter createPresenter() {
        return new ArrivalMainPresenter();
    }

    @Override
    public Observable<Bundle> bindInitData() {
        return bundle2Obserable(getArguments());
    }

    @Override
    public Observable<Object> bindClickRemind() {
        return rxClick(remidSheetItem);
    }

    @Override
    public Observable<Object> bindClickStationSave() {
        return rxClick(stationSaveSheetItem);
    }

    @Override
    public Observable<Object> bindClickSataionAllBus() {
        return rxClick(stationAllBusSheetItem);
    }

    @Override
    public Observable<Object> bindClickStationLocation() {
        return rxClick(stasionLocationSheetItem);
    }

    @Override
    public Observable<Object> bindClickStationView() {
        return rxClick(stationViewSheetItem);
    }

    @Override
    public Observable<RouteStop> bindSaveStation() {
        return stopSubject;
    }

    @Override
    public void bindService(RouteStop routeStop) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(RouteStop.class.getSimpleName(), routeStop);
        startService(context, RemindService.class, bundle);
    }

    @Override
    public void hiddenSheet() {
        if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    public void initView() {
        bottomSheetBehavior = BottomSheetBehavior.from(sheetView);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        hiddenSheet();
    }

    @Override
    public void renderLoading() {
        showProgress();
    }

    @Override
    public void renderSuccess(Bundle bundle) {
        ArrayList<PagerRecyclerObj> objects = new ArrayList<>();
        HashMap<String, BusStopOfRoute> busStopOfRouteMap = (HashMap<String, BusStopOfRoute>) bundle.getSerializable(CityBusInterface.BUS_STOP_OF_ROUTE);
        BusRoute route = bundle.getParcelable(CityBusInterface.BUS_ROUTE);

        for (BusSubRoute subRoute : route.getSubRoutes()) {
            BusStopOfRoute busStopOfRoute = busStopOfRouteMap.get(getKey(subRoute));
            ArrivalRecyclerAdapter arrivalRecyclerAdapter = new ArrivalRecyclerAdapter(route, subRoute, busStopOfRoute, publishSubject);
            objects.add(new PagerRecyclerObj(getTitle(context, route, subRoute), arrivalRecyclerAdapter, context));
            presenter.addDisposable(arrivalRecyclerAdapter.getDiaposable());
            presenter.addDisposable(arrivalRecyclerAdapter.getClickSubject().subscribe(stop -> {
                hiddenSheet();
                stopSubject.onNext(stop);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }));
        }

        publishSubject.onNext(bundle.getParcelableArrayList(CityBusInterface.BUS_ESTIMATE_TIME));
        initView(viewPager, tabLayout, new PagerRecyclerAdapter(tabLayout, objects));
    }

    @Override
    public void renderFinish() {
        dimessMessage();
    }


}
