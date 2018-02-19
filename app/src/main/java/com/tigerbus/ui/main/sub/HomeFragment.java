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
import com.tigerbus.data.autovalue.HomePresenterAutoValue;
import com.tigerbus.sqlite.BriteDB;
import com.tigerbus.sqlite.data.CommonStop;
import com.tigerbus.sqlite.data.CommonStopType;
import com.tigerbus.ui.route.RouteActivity;
import com.tigerbus.ui.widget.PagerRecyclerAdapter;
import com.tigerbus.ui.widget.PagerRecyclerObj;
import com.tigerbus.ui.widget.RecyclerItemTouchHelper;

import java.util.ArrayList;
import java.util.TreeMap;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

@FragmentView(layout = R.layout.home_fragment)
public final class HomeFragment extends BaseFragment<HomeView, HomePresenter>
        implements HomeView<ViewStateRender>, ViewStateRender<TreeMap<CommonStopType, HomePresenterAutoValue>> {

    private PublishSubject<Boolean> initDataSubject = PublishSubject.create();
    private PublishSubject<Bundle> adapterEventSubject = PublishSubject.create();
    @ViewInject(R.id.tablayout)
    private TabLayout tabLayout;
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter(BriteDB.getInstance(application));
    }

    @Override
    public Observable<Boolean> bindInitData() {
        return initDataSubject;
    }

    @Override
    public void onStart() {
        super.onStart();
        initDataSubject.onNext(true);
        adapterEventSubject.subscribe(this::adapteSubjectOnNext);
    }

    private void adapteSubjectOnNext(Bundle bundle) {
        if (bundle.getParcelable(COMMONEVENT_GO) != null) {
            goCommon(bundle.getParcelable(COMMONEVENT_GO));
        } else if (bundle.getParcelable(COMMONEVENT_DELECT) != null) {
            presenter.removeCommon(bundle.getParcelable(COMMONEVENT_DELECT));
        } else if (bundle.getParcelableArray(COMMONEVENT_MOVE) != null) {
            presenter.updateCommonOrder(bundle.getParcelable(COMMONEVENT_MOVE));
        }
    }

    private void goCommon(CommonStop commonStop) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(CityBusInterface.BUS_ROUTE, commonStop.routeStop().busRoute());
        startActivity(context, RouteActivity.class, bundle);
    }

    @Override
    public void renderLoading() {
        showProgressDialog(context);
    }

    @Override
    public void renderFinish() {
        dimessProgressDialog();
    }

    @Override
    public void renderSuccess(TreeMap<CommonStopType, HomePresenterAutoValue> result) {
        ArrayList<PagerRecyclerObj> pagerRecyclerObjs = new ArrayList<>();
        for (CommonStopType commonStopType : result.keySet()) {
            HomePresenterAutoValue homePresenterAutoValue = result.get(commonStopType);
            HomeAdapter homeAdapter = new HomeAdapter(adapterEventSubject, homePresenterAutoValue.publishSubject());
            pagerRecyclerObjs.add(new PagerRecyclerObj(homePresenterAutoValue.commonStopType().type(),
                    homeAdapter, context, new RecyclerItemTouchHelper(homeAdapter)));
        }
        initTabPager(viewPager, tabLayout, new PagerRecyclerAdapter(tabLayout, pagerRecyclerObjs));
    }

}
