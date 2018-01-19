package com.tigerbus.ui.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.tigerbus.ui.main.sub.SubFragment;

import java.util.ArrayList;

public final class MainAdapter extends FragmentStatePagerAdapter{

    private ArrayList<SubFragment> fragments = new ArrayList<>();

    public MainAdapter(FragmentManager fragmentManager,ArrayList<SubFragment> fragments) {
        super(fragmentManager);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
