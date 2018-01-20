package com.tigerbus.ui.main.sub;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.tigerbus.R;
import com.tigerbus.base.BaseFragment;
import com.tigerbus.base.MvpPresenter;
import com.tigerbus.base.annotation.FragmentView;
import com.tigerbus.base.annotation.ViewInject;

@FragmentView(mvp = false, layout = R.layout.remind_fragment)
public final class RemindFragment extends BaseFragment {

    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.tablayout)
    private TabLayout tabLayout;

    public static RemindFragment newInstance() {
        RemindFragment fragment = new RemindFragment();
        return fragment;
    }

    @Override
    public MvpPresenter createPresenter() {
        return null;
    }
}
