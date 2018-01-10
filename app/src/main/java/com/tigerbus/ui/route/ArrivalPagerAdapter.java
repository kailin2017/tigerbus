package com.tigerbus.ui.route;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.tigerbus.R;
import com.tigerbus.TigerApplication;
import com.tigerbus.base.log.TlogType;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusStopOfRoute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

public final class ArrivalPagerAdapter extends PagerAdapter {

    private final static String TAG = ArrivalPagerAdapter.class.getSimpleName();
    private final static String KEY_SUBROUTENAME = "KEY_SUBROUTENAME", KEY_SUBROUTEkEY = "KEY_SUBROUTEkEY", KEY_STOPFORROUTE = "KEY_STOPFORROUTE";
    private ArrayList<RecyclerView> recyclerViews = new ArrayList<>();
    private BusRoute busRoute;
    private Map<String, PublishSubject<ArrayList<BusEstimateTime>>> publishSubjectMap = new HashMap<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ArrivalPagerAdapter(Context context, TabLayout tabLayout, BusRoute busRoute, ArrayList<BusStopOfRoute> busStopOfRoutes) {
        this.busRoute = busRoute;
        Map<String, BusStopOfRoute> busStopOfRouteMap = initBusStopOfRouteMap(busStopOfRoutes);
        initSubject(context, tabLayout, busStopOfRouteMap);
    }

    private Map<String, BusStopOfRoute> initBusStopOfRouteMap(ArrayList<BusStopOfRoute> busStopOfRoutes) {
        Map<String, BusStopOfRoute> busStopOfRouteMap = new HashMap<>();
        for (BusStopOfRoute busStopOfRoute : busStopOfRoutes) {
            busStopOfRouteMap.put(busStopOfRoute.getDirection(), busStopOfRoute);
        }
        return busStopOfRouteMap;
    }

    private void initSubject(Context context, TabLayout tabLayout, Map<String, BusStopOfRoute> busStopOfRouteMap) {
        Observable.fromIterable(busRoute.getSubRoutes())
                .subscribe(busSubRoute -> {
                    // 產生title
                    StringBuffer title = new StringBuffer();
                    if (busRoute.getSubRoutes().size() > 2)
                        title.append(busSubRoute.getSubRouteName().getZh_tw() + "\n");
                    title.append(context.getString(R.string.route_go));
                    title.append(busSubRoute.getDirection().equals("0") ?
                            busRoute.getDepartureStopNameZh() : busRoute.getDestinationStopNameZh());
                    tabLayout.addTab(tabLayout.newTab().setText(title.toString()));
                    // 產生mapkey
                    StringBuffer mapKey = new StringBuffer();
                    mapKey.append(busSubRoute.getSubRouteUID());
                    mapKey.append("_");
                    mapKey.append(busSubRoute.getDirection());
                    //產生view及綁定subject
                    PublishSubject<ArrayList<BusEstimateTime>> publishSubject = PublishSubject.create();
                    ArrivalRecyclerAdapter adapter = new ArrivalRecyclerAdapter(
                            busStopOfRouteMap.get(busSubRoute.getDirection()), publishSubject);
                    RecyclerView recyclerView = new RecyclerView(context);
                    recyclerView.setAdapter(adapter);
                    recyclerViews.add(recyclerView);
                    compositeDisposable.add(adapter.getDiaposable());
                    publishSubjectMap.put(mapKey.toString(), publishSubject);
                }, throwable -> TigerApplication.printLog(TlogType.error, TAG, throwable.toString()));
    }


    @Override
    public int getCount() {
        return recyclerViews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(recyclerViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        RecyclerView recyclerView = recyclerViews.get(position);
        view.addView(recyclerView);
        return recyclerView;
    }
}
