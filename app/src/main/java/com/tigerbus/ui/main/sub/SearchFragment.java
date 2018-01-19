package com.tigerbus.ui.main.sub;

import android.os.Bundle;

import com.tigerbus.base.MvpPresenter;

/**
 * Created by Kailin on 2018/1/16.
 */

public final class SearchFragment extends SubFragment{

    public static SearchFragment newInstance() {

        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
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
