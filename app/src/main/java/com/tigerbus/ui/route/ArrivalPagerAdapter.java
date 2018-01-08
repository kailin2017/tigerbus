package com.tigerbus.ui.route;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.tigerbus.R;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusStopOfRoute;
import com.tigerbus.data.bus.BusSubRoute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;

public final class ArrivalPagerAdapter extends PagerAdapter {

    private final static String KEY_SUBROUTENAME = "KEY_SUBROUTENAME", KEY_STOPFORROUTE = "KEY_STOPFORROUTE";
    private ArrayList<RecyclerView> recyclerViews = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();
    private BusRoute busRoute;
    private Map<String,PublishSubject<ArrayList<BusEstimateTime>>> publishSubjectMap = new HashMap<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ArrivalPagerAdapter(Context context, BusRoute busRoute, ArrayList<BusStopOfRoute> busStopOfRoutes) {
        this.busRoute = busRoute;
        Observable.fromIterable(busRoute.getSubRoutes())
                .flatMap(busSubRoute -> {
                    // 產生title
                    StringBuffer title = new StringBuffer();
                    title.append(busSubRoute.getSubRouteName().getZh_tw());
                    title.append(context.getString(R.string.route_go));
                    title.append(busSubRoute.getDirection().equals("0") ?
                            busRoute.getDepartureStopNameZh() : busRoute.getDestinationStopNameZh());
                    titles.add(busSubRoute.getSubRouteName().getZh_tw());
                    // 產生mapkey
                    StringBuffer mapKey = new StringBuffer();
                    mapKey.append(busSubRoute.getSubRouteUID());
                    mapKey.append("_");
                    mapKey.append(busSubRoute.getDirection());
                    // 打包rx
                    Observable<Bundle> observable = Observable.zip(
                            Observable.fromIterable(busStopOfRoutes),
                            Observable.just(busSubRoute.getSubRouteName().getZh_tw()),
                            Observable.just(mapKey.toString()),
                            (busStopOfRoute, routeName,key) -> {
                                Bundle bundle = new Bundle();
                                bundle.putParcelable(KEY_STOPFORROUTE, busStopOfRoute);
                                bundle.putString(KEY_SUBROUTENAME, routeName);
                                return bundle;
                            }
                    );
                    return observable;
                })
                .filter(bundle -> {
                    BusStopOfRoute busStopOfRoute = bundle.getParcelable(KEY_STOPFORROUTE);
                    String routeName = bundle.getString(KEY_SUBROUTENAME);
                    return routeName.equalsIgnoreCase(busStopOfRoute.getRouteName().getZh_tw());
                })
                .subscribe(busSubRoute -> {
//                    ArrivalRecyclerAdapter adapter = new ArrivalRecyclerAdapter(busStopOfRoutes);
//                    RecyclerView recyclerView = new RecyclerView(context);
//                    recyclerView.setAdapter(adapter);
//                    recyclerViews.add(recyclerView);
//                    compositeDisposable.add(adapter.getDiaposable());
                });

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return busRoute.getSubRoutes().size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        RecyclerView recyclerView = recyclerViews.get(position);
        view.addView(recyclerView);
        return recyclerView;
    }
}
