package com.tigerbus.ui.route;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tigerbus.R;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusStopOfRoute;
import com.tigerbus.data.detail.NameType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Kailin on 2018/1/6.
 */

public final class ArrivalRecyclerAdapter extends RecyclerView.Adapter<ArrivalRecyclerAdapter.ViewHolder> {


    private ArrayList<BusStopOfRoute> busStopOfRoutes;
    private ArrayList<BusEstimateTime> busEstimateTimes;
    private Map<String, BusEstimateTime> busEstimateTimeMap = new HashMap<>();
    private PublishSubject<ArrayList<BusEstimateTime>> publishSubject;
    private Disposable disposable;

    public ArrivalRecyclerAdapter(@NonNull ArrayList<BusStopOfRoute> busStopOfRoutes,
                                  @NonNull PublishSubject<ArrayList<BusEstimateTime>> publishSubject) {
        this.busStopOfRoutes = busStopOfRoutes;
        this.publishSubject = publishSubject;
        initPublicsh();
    }

    public void initPublicsh() {
        disposable = publishSubject
                .flatMap(busEstimateTimes -> Observable.fromIterable(busEstimateTimes))
                .subscribe(busEstimateTime -> busEstimateTimeMap.put(busEstimateTime.getRouteUID(), busEstimateTime));
    }

    public Disposable getDiaposable(){
        return disposable;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.route_arrival_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BusStopOfRoute busStopOfRoute = busStopOfRoutes.get(position);
        holder.stopName.setText(busStopOfRoute.getRouteName().getZh_tw());
        if (busEstimateTimes != null) {
            BusEstimateTime busEstimateTime = busEstimateTimeMap.get(busStopOfRoute.getRouteUID());
            holder.estimateTime.setText(busEstimateTime.getEstimateTime() % 60);
        }
    }

    @Override
    public int getItemCount() {
        return busStopOfRoutes.size();
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
