package com.tigerbus.ui.main.sub;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.tigerbus.R;
import com.tigerbus.base.BaseFragment;
import com.tigerbus.base.ViewStateRender;
import com.tigerbus.base.annotation.FragmentView;
import com.tigerbus.base.annotation.ViewInject;
import com.tigerbus.sqlite.BriteSQL;
import com.tigerbus.sqlite.data.CommonStopType;
import com.tigerbus.ui.widget.PagerRecyclerAdapter;
import com.tigerbus.ui.widget.PagerRecyclerObj;

import java.util.ArrayList;
import java.util.List;

@FragmentView(layout = R.layout.home_fragment)
public final class HomeFragment extends BaseFragment<HomeView, HomePresenter>
        implements HomeView<ViewStateRender>, ViewStateRender<List<CommonStopType>> {

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
    public void renderSuccess(List<CommonStopType> result) {
        ArrayList<PagerRecyclerObj> pagerRecyclerObjs = new ArrayList<>();
        for (CommonStopType commonStopType : result) {
            pagerRecyclerObjs.add(new PagerRecyclerObj(commonStopType.type(), new HomeAdapter(), context));
        }
        viewPager.setAdapter(new PagerRecyclerAdapter(tabLayout, pagerRecyclerObjs));
    }

    @Override
    public void renderFinish() {

    }
}
