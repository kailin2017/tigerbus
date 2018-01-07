package com.tigerbus.ui.route;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tigerbus.R;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusStopOfRoute;

import java.util.ArrayList;

/**
 * Created by Kailin on 2018/1/6.
 */

public final class ArrivalRecyclerAdapter extends RecyclerView.Adapter<ArrivalRecyclerAdapter.ViewHolder> {

    private ArrayList<BusStopOfRoute> busStopOfRoutes;
    private ArrayList<BusEstimateTime> busEstimateTimes;

    public ArrivalRecyclerAdapter(ArrayList<BusStopOfRoute> busStopOfRoutes) {
        this.busStopOfRoutes = busStopOfRoutes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.route_arrival_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BusStopOfRoute busStopOfRoute = busStopOfRoutes.get(position);
        holder.stopName.setText(busStopOfRoute.getRouteName().getZh_tw());
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
