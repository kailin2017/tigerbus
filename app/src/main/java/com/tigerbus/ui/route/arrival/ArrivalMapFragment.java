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
import com.tigerbus.ui.route.adapter.ArrivalPagerMapAdapter;
import com.tigerbus.ui.route.adapter.MapObj;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

@FragmentView(layout = R.layout.route_map_fragment)
public final class ArrivalMapFragment extends BaseFragment<ArrivalMapView, ArrivalMapPresenter>
        implements ArrivalMapView, ViewStateRender<ArrayList<MapObj>> {

    private PublishSubject<Boolean> bindOnTimeSubject = PublishSubject.create();
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
    public void onStart() {
        super.onStart();
        bindOnTimeSubject.onNext(true);
    }

    @Override
    public ArrivalMapPresenter createPresenter() {
        return new ArrivalMapPresenter(context);
    }


    @Override
    public Observable<Bundle> bindInitData() {
        return bundle2Obserable(getArguments());
    }

    @Override
    public Observable<Boolean> bindOnTimeData() {
        return bindOnTimeSubject;
    }

    @Override
    public void renderLoading() {
        showProgress();
    }

    @Override
    public void renderSuccess(ArrayList<MapObj> mapObjs) {
        ArrivalPagerMapAdapter arrivalPagerMapAdapter = new ArrivalPagerMapAdapter(getFragmentManager(), tabLayout, mapObjs);
        initTabPager(viewPager, tabLayout, arrivalPagerMapAdapter);
    }

    @Override
    public void renderFinish() {
        dimessProgress();
    }

}
