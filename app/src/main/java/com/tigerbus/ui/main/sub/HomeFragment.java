package com.tigerbus.ui.main.sub;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.tigerbus.R;
import com.tigerbus.base.BaseFragment;
import com.tigerbus.base.ViewStateRender;
import com.tigerbus.base.annotation.FragmentView;
import com.tigerbus.base.annotation.ViewInject;
import com.tigerbus.data.autovalue.HomePresenterAutoValue;
import com.tigerbus.sqlite.BriteDB;
import com.tigerbus.sqlite.data.CommonStopType;
import com.tigerbus.ui.widget.PagerRecyclerAdapter;
import com.tigerbus.ui.widget.PagerRecyclerObj;

import java.util.ArrayList;
import java.util.TreeMap;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

@FragmentView(layout = R.layout.home_fragment)
public final class HomeFragment extends BaseFragment<HomeView, HomePresenter>
        implements HomeView<ViewStateRender>, ViewStateRender<TreeMap<CommonStopType, HomePresenterAutoValue>> {

    private PublishSubject<Boolean> initDataSubject = PublishSubject.create();
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
    }

    @Override
    public void renderSuccess(TreeMap<CommonStopType, HomePresenterAutoValue> result) {
        ArrayList<PagerRecyclerObj> pagerRecyclerObjs = new ArrayList<>();
        for (CommonStopType commonStopType : result.keySet()) {
            HomePresenterAutoValue homePresenterAutoValue = result.get(commonStopType);
            HomeAdapter homeAdapter = new HomeAdapter(homePresenterAutoValue.publishSubject());
            pagerRecyclerObjs.add(new PagerRecyclerObj(homePresenterAutoValue.commonStopType().type(), homeAdapter, context));
        }
        initTabPager(viewPager,tabLayout,new PagerRecyclerAdapter(tabLayout, pagerRecyclerObjs));
    }
}
