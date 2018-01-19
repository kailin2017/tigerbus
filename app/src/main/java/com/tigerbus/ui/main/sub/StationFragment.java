package com.tigerbus.ui.main.sub;

import android.os.Bundle;

import com.tigerbus.base.BaseFragment;
import com.tigerbus.base.MvpPresenter;

public final class StationFragment extends SubFragment{

    public static StationFragment newInstance() {

        Bundle args = new Bundle();

        StationFragment fragment = new StationFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public int getIconRes() {
        return 0;
    }

    @Override
    public MvpPresenter createPresenter() {
        return null;
    }
}
