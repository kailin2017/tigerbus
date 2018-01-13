package com.tigerbus.ui.route;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tigerbus.R;
import com.tigerbus.TigerApplication;
import com.tigerbus.base.log.TlogType;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusStopOfRoute;
import com.tigerbus.data.detail.Stop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Kailin on 2018/1/6.
 */

public final class ArrivalRecyclerAdapter extends RecyclerView.Adapter<ArrivalRecyclerAdapter.ViewHolder> {

    private final static String TAG = ArrivalRecyclerAdapter.class.getSimpleName();
    private BusStopOfRoute busStopOfRoute;
    private Map<String, BusEstimateTime> busEstimateTimeMap = new HashMap<>();
    private Disposable disposable;

    public ArrivalRecyclerAdapter(@NonNull BusStopOfRoute busStopOfRoute,
                                  @NonNull PublishSubject<ArrayList<BusEstimateTime>> publishSubject) {
        this.busStopOfRoute = busStopOfRoute;
        this.disposable = publishSubject.subscribe(busEstimateTimes -> initData(busEstimateTimes));
    }

    private void initData(ArrayList<BusEstimateTime> busEstimateTimes) {
        // 不可使用rxJava 否則會有notifyDataSetChanged失效問題
        for (BusEstimateTime busEstimateTime : busEstimateTimes) {
            if (busEstimateTime.getDirection().equalsIgnoreCase(busStopOfRoute.getDirection()) &&
                    (busEstimateTime.getSubRouteUID() != null ?
                            busEstimateTime.getSubRouteUID().equalsIgnoreCase(busStopOfRoute.getSubRouteUID()) : true)) {
                busEstimateTimeMap.put(busEstimateTime.getStopUID(), busEstimateTime);
            }
        }
        notifyDataSetChanged();
    }

    public Disposable getDiaposable() {
        return disposable;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.route_arrival_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Stop stop = busStopOfRoute.getStops().get(position);
        holder.stopName.setText(stop.getStopName().getZh_tw());
        BusEstimateTime busEstimateTime = busEstimateTimeMap.get(stop.getStopUID());
        if (busEstimateTime != null) {
            holder.estimateTime.setText(busEstimateTime.getEstimateTime() + "");
        }
    }

    @Override
    public int getItemCount() {
        return busStopOfRoute.getStops().size();
    }

    final static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView stopName, estimateTime;

        public ViewHolder(View view) {
            super(view);
            stopName = view.findViewById(R.id.stopname);
            estimateTime = view.findViewById(R.id.estimatetime);
        }
    }
}
