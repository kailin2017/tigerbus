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
import com.tigerbus.sqlite.BriteSQL;
import com.tigerbus.sqlite.data.CommonStopType;
import com.tigerbus.ui.widget.PagerRecyclerAdapter;
import com.tigerbus.ui.widget.PagerRecyclerObj;

import java.util.ArrayList;
import java.util.HashMap;

@FragmentView(layout = R.layout.home_fragment)
public final class HomeFragment extends BaseFragment<HomeView, HomePresenter>
        implements HomeView<ViewStateRender>, ViewStateRender<HashMap<CommonStopType, HomePresenterAutoValue>> {

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
        return new HomePresenter(BriteSQL.getInstance(application));
    }

    @Override
    public void renderLoading() {

    }

    @Override
    public void renderSuccess(HashMap<CommonStopType, HomePresenterAutoValue> result) {
        ArrayList<PagerRecyclerObj> pagerRecyclerObjs = new ArrayList<>();
        for (HomePresenterAutoValue homePresenterAutoValue : result.values()) {
            HomeAdapter homeAdapter = new HomeAdapter(homePresenterAutoValue.publishSubject());
            pagerRecyclerObjs.add(new PagerRecyclerObj(homePresenterAutoValue.commonStopType().type(), homeAdapter, context));
        }
        viewPager.setAdapter(new PagerRecyclerAdapter(tabLayout, pagerRecyclerObjs));
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
