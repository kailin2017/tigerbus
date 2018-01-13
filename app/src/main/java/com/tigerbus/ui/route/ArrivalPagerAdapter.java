package com.tigerbus.ui.route;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ArrivalPagerAdapter extends PagerAdapter {

    private ArrayList<RecyclerView> recyclerViews = new ArrayList<>();

    public ArrivalPagerAdapter(TabLayout tabLayout, ArrayList<ArrivalPagerObject> objects) {
        tabLayout.setTabMode(objects.size() > 2 ? TabLayout.MODE_SCROLLABLE : TabLayout.MODE_FIXED);
        for (ArrivalPagerObject object : objects) {
            recyclerViews.add(object.getRecyclerView());
            tabLayout.addTab(tabLayout.newTab().setText(object.getPagerTitle()));
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        RecyclerView recyclerView = recyclerViews.get(position);
        container.addView(recyclerView);
        return recyclerView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return recyclerViews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }


}
