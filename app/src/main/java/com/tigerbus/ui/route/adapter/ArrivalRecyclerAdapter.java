package com.tigerbus.ui.route.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tigerbus.R;
import com.tigerbus.data.autovalue.BusA2DataListAutoValue;
import com.tigerbus.data.bus.BusA2Data;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusRouteInterface;
import com.tigerbus.data.bus.BusStopOfRoute;
import com.tigerbus.data.bus.BusSubRoute;
import com.tigerbus.data.detail.Stop;
import com.tigerbus.sqlite.data.RouteStop;
import com.tigerbus.util.Sec2Min;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.PublishSubject;

public final class ArrivalRecyclerAdapter
        extends RecyclerView.Adapter<ArrivalRecyclerAdapter.ViewHolder> implements Sec2Min {

    private BusStopOfRoute busStopOfRoute;
    private Map<String, BusEstimateTime> busEstimateTimeMap = new HashMap<>();
    private Map<String, BusA2Data> busA2DataMap = new HashMap<>();
    private PublishSubject<RouteStop> itemSubject = PublishSubject.create();
    private BusRoute busRoute;
    private BusSubRoute busSubRoute;
    private Context context;

    public ArrivalRecyclerAdapter(@NonNull BusRoute busRoute,
                                  @NonNull BusSubRoute busSubRoute,
                                  @NonNull BusStopOfRoute busStopOfRoute,
                                  @NonNull PublishSubject<BusA2DataListAutoValue> onTimeDataSubject) {
        this.busRoute = busRoute;
        this.busSubRoute = busSubRoute;
        this.busStopOfRoute = busStopOfRoute;
        onTimeDataSubject.subscribe(busEstimateTimes -> initData(busEstimateTimes));
    }

    private void initData(BusA2DataListAutoValue busA2DataListAutoValue) {
        // 不可使用rxJava 否則會有notifyDataSetChanged失效問題
        for (BusEstimateTime busEstimateTime : busA2DataListAutoValue.busEstimateTimes()) {
            if (busEstimateTime.getDirection().equalsIgnoreCase(busStopOfRoute.getDirection()) &&
                    (busEstimateTime.getSubRouteUID() != null ? busEstimateTime.getSubRouteUID().equalsIgnoreCase(busStopOfRoute.getSubRouteUID()) : true)) {
                busEstimateTimeMap.put(busEstimateTime.getStopUID(), busEstimateTime);
            }
        }
        for (BusA2Data busA2Data : busA2DataListAutoValue.busA2Datas()) {
            if (busA2Data.getDirection().equalsIgnoreCase(busStopOfRoute.getDirection()) &&
                    (busA2Data.getSubRouteUID() != null ? busA2Data.getSubRouteUID().equalsIgnoreCase(busStopOfRoute.getSubRouteUID()) : true)) {
                busA2DataMap.put(busA2Data.getStopUID(), busA2Data);
            }
        }
        notifyDataSetChanged();
    }

    public PublishSubject<RouteStop> getClickSubject() {
        return itemSubject;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.route_arrival_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Stop stop = busStopOfRoute.getStops().get(position);
        holder.itemLayout.setTag(RouteStop.create(stop, busRoute, busSubRoute));
        holder.stopName.setText(stop.getStopName().getZh_tw());
        BusEstimateTime busEstimateTime = busEstimateTimeMap.get(stop.getStopUID());
        if (busEstimateTime != null) {
            holder.estimateTime.setText(sec2Min(context, busEstimateTime.getEstimateTime()));
        }
    }

    @Override
    public int getItemCount() {
        return busStopOfRoute.getStops().size();
    }

    final class ViewHolder extends RecyclerView.ViewHolder {

        public TextView stopName, estimateTime;
        public ConstraintLayout itemLayout;

        public ViewHolder(View view) {
            super(view);
            stopName = view.findViewById(R.id.stopname);
            estimateTime = view.findViewById(R.id.estimatetime);
            itemLayout = view.findViewById(R.id.item);
            itemLayout.setOnClickListener(v -> itemSubject.onNext((RouteStop) v.getTag()));
        }
    }
}
