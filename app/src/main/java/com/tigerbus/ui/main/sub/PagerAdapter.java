package com.tigerbus.ui.main.sub;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Kailin on 2018/1/20.
 */

public final class PagerAdapter extends android.support.v4.view.PagerAdapter {



    public PagerAdapter(TabLayout tabLayout, ArrayList<RecyclerView> recyclerViews){

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }
}
