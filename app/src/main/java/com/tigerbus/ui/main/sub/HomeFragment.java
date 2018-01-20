package com.tigerbus.ui.main.sub;

import android.os.Bundle;

import com.tigerbus.R;
import com.tigerbus.base.BaseFragment;
import com.tigerbus.base.BasePresenter;
import com.tigerbus.base.MvpPresenter;
import com.tigerbus.base.annotation.FragmentView;

@FragmentView(mvp = false, layout = R.layout.default_recycler)
public final class HomeFragment extends BaseFragment {

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public MvpPresenter createPresenter() {
        return null;
    }
}
