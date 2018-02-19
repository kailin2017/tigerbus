package com.tigerbus.ui.main.sub;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.tigerbus.R;
import com.tigerbus.app.BaseFragment;
import com.tigerbus.app.ViewStateRender;
import com.tigerbus.app.annotation.FragmentView;
import com.tigerbus.app.annotation.ViewInject;
import com.tigerbus.data.CityBusInterface;
import com.tigerbus.sqlite.BriteDB;
import com.tigerbus.sqlite.data.RemindStop;
import com.tigerbus.ui.route.RouteActivity;
import com.tigerbus.ui.widget.PagerRecyclerAdapter;
import com.tigerbus.ui.widget.PagerRecyclerObj;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

@FragmentView(layout = R.layout.remind_fragment)
public final class RemindFragment extends BaseFragment<RemindView, RemindPresenter>
        implements RemindView<ViewStateRender>, ViewStateRender<List<RemindStop>> {

    private PublishSubject<Boolean> initDataSubject = PublishSubject.create();
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.tablayout)
    private TabLayout tabLayout;

    public static RemindFragment newInstance() {
        RemindFragment fragment = new RemindFragment();
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        initDataSubject.onNext(true);
    }

    @Override
    public RemindPresenter createPresenter() {
        return new RemindPresenter(BriteDB.getInstance(application));
    }

    @Override
    public Observable<Boolean> bindInitData() {
        return initDataSubject;
    }

    @Override
    public void renderSuccess(List<RemindStop> result) {
        ArrayList<RemindStop> runingRemindStops = new ArrayList<>(), finishRemindStops = new ArrayList<>();
        for (RemindStop remindStop : result) {
            if (remindStop.isRun()) {
                runingRemindStops.add(remindStop);
            } else {
                finishRemindStops.add(remindStop);
            }
        }
        ArrayList<PagerRecyclerObj> pagerRecyclerObjs = new ArrayList<>();
        RemindAdapter runningRemindAdapter = new RemindAdapter(runingRemindStops);
        RemindAdapter finishRemindAdapter = new RemindAdapter(finishRemindStops);

        runningRemindAdapter.getObservable()
                .doOnSubscribe(presenter::addDisposable).subscribe(this::itemClick);
        finishRemindAdapter.getObservable()
                .doOnSubscribe(presenter::addDisposable).subscribe(this::itemClick);

        pagerRecyclerObjs.add(new PagerRecyclerObj(getString(R.string.status_progress), runningRemindAdapter, context));
        pagerRecyclerObjs.add(new PagerRecyclerObj(getString(R.string.status_finish), finishRemindAdapter, context));
        PagerRecyclerAdapter pagerRecyclerAdapter = new PagerRecyclerAdapter(tabLayout, pagerRecyclerObjs);
        initTabPager(viewPager, tabLayout, pagerRecyclerAdapter);
    }

    public void itemClick(RemindStop remindStop) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(CityBusInterface.BUS_ROUTE, remindStop.routeStop().busRoute());
        startActivity(context, RouteActivity.class, bundle);
    }
}
