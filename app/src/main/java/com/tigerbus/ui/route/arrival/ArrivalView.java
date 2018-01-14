package com.tigerbus.ui.route.arrival;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.tigerbus.R;
import com.tigerbus.base.BaseView;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusSubRoute;
import com.tigerbus.data.bus.BusSubRouteInterface;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public interface ArrivalView extends BaseView {

    Observable<Bundle> initDataIntent();

    void setEstimateSubject(@NonNull PublishSubject<ArrayList<BusEstimateTime>> estimateSubject);

    default ArrivalPresenter createArrivalPresenter() {
        return new ArrivalPresenter();
    }

    default void initView(ViewPager viewPager, TabLayout tabLayout, PagerAdapter adapter) {
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    default Observable<Bundle> bundle2Obserable(Bundle bundle){
        return Observable.just(bundle);
    }

    default <T extends BusSubRouteInterface> String getKey(@NonNull T t) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(t.getSubRouteUID());
        stringBuffer.append("_");
        stringBuffer.append(t.getDirection());
        return stringBuffer.toString();
    }

    default String getTitle(@NonNull Context context, @NonNull BusRoute route, @NonNull BusSubRoute subRoute) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(route.getSubRoutes().size() > 2 ? subRoute.getSubRouteName().getZh_tw() + "\n" : "");
        stringBuffer.append(context.getString(R.string.route_go));
        stringBuffer.append(subRoute.getDirection().equals("0") ?
                route.getDestinationStopNameZh() : route.getDepartureStopNameZh());
        return stringBuffer.toString();
    }
}
