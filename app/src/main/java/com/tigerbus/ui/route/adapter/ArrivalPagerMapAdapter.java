package com.tigerbus.ui.route.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentStatePagerAdapter;


import com.tigerbus.TigerApplication;
import com.tigerbus.app.log.TlogType;

import java.util.ArrayList;

public final class ArrivalPagerMapAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragments = new ArrayList<>();

    public ArrivalPagerMapAdapter(FragmentManager fm, TabLayout tabLayout, ArrayList<MapObj> objects) {
        super(fm);
        tabLayout.setTabMode(objects.size() > 2 ? TabLayout.MODE_SCROLLABLE : TabLayout.MODE_FIXED);
        for (MapObj object : objects) {
            fragments.add(object.getView());
            tabLayout.addTab(tabLayout.newTab().setText(object.getPagerTitle()));
        }
    }

    @Override
    public Fragment getItem(int position) {
        try{
            return fragments.get(position);
        }catch (Exception e){
            TigerApplication.printLog(TlogType.error,"",e.toString());
            return null;
        }
    }



    @Override
    public int getCount() {
        return fragments.size();
    }
}
