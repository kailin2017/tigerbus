package com.tigerbus.ui.main.sub;

import android.os.Bundle;

import com.tigerbus.R;
import com.tigerbus.base.BaseFragment;
import com.tigerbus.base.ViewStateRender;
import com.tigerbus.base.annotation.FragmentView;
import com.tigerbus.sqlite.BriteSQL;

@FragmentView(layout = R.layout.default_recycler)
public final class HomeFragment extends BaseFragment<HomeView, HomePresenter>
        implements HomeView<ViewStateRender>, ViewStateRender {

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
    public void renderSuccess(Object result) {

    }

    @Override
    public void renderFinish() {

    }
}
