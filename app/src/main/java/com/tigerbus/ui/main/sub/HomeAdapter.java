package com.tigerbus.ui.main.sub;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tigerbus.R;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusSubRoute;
import com.tigerbus.data.detail.Stop;
import com.tigerbus.sqlite.data.CommodStopQueryResult;
import com.tigerbus.sqlite.data.CommonStop;
import com.tigerbus.ui.route.adapter.ArrivalRecyclerAdapter;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.PublishSubject;

public final class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private final ArrayList<CommodStopQueryResult> commodStopQueryResults = new ArrayList<>();

    public HomeAdapter(@NonNull PublishSubject<ArrayList<CommodStopQueryResult>> publishSubject) {
        publishSubject.subscribe(this::initData);
    }

    public void initData(ArrayList<CommodStopQueryResult> result) {
        commodStopQueryResults.clear();
        commodStopQueryResults.addAll(result);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CommodStopQueryResult commodStopQueryResult = commodStopQueryResults.get(position);
        BusEstimateTime busEstimateTime = commodStopQueryResult.busEstimateTime();
        BusRoute busRoute = commodStopQueryResult.commonStop().busRoute();
        BusSubRoute busSubRoute = commodStopQueryResult.commonStop().busSubRoute();
        holder.routeName.setText(busRoute.getRouteName().getZh_tw() + " 往" +
                (busSubRoute.getDirection().equalsIgnoreCase("0")? busRoute.getDestinationStopNameZh():busRoute.getDepartureStopNameZh()));
        holder.stopName.setText(commodStopQueryResult.commonStop().stop().getStopName().getZh_tw());
        holder.estimateTime.setText(busEstimateTime.getEstimateTime() + "");
    }

    @Override
    public int getItemCount() {
        return commodStopQueryResults.size();
    }

    final class ViewHolder extends RecyclerView.ViewHolder {

        public TextView routeName, stopName, estimateTime;
        public ConstraintLayout itemLayout;

        public ViewHolder(View view) {
            super(view);
            routeName = view.findViewById(R.id.routename);
            stopName = view.findViewById(R.id.stopname);
            estimateTime = view.findViewById(R.id.estimatetime);
            itemLayout = view.findViewById(R.id.item);
        }
    }
}
