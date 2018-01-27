package com.tigerbus.ui.main.sub;

import android.support.constraint.ConstraintLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tigerbus.R;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusSubRoute;
import com.tigerbus.sqlite.data.CommodStopQueryResult;
import com.tigerbus.util.DiffListCallBack;

import java.util.ArrayList;
import java.util.Collection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public final class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private final ArrayList<CommodStopQueryResult> commodStopQueryResults = new ArrayList<>();

    public HomeAdapter(@NonNull PublishSubject<ArrayList<CommodStopQueryResult>> publishSubject) {
        publishSubject.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(this::initData);
    }

    public void initData(ArrayList<CommodStopQueryResult> result) {
        this.commodStopQueryResults.clear();
        this.commodStopQueryResults.addAll((ArrayList<CommodStopQueryResult>) result.clone());
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CommodStopQueryResult commodStopQueryResult = commodStopQueryResults.get(position);

        BusEstimateTime busEstimateTime = commodStopQueryResult.busEstimateTime();
        BusRoute busRoute = commodStopQueryResult.commonStop().routeStop().busRoute();
        BusSubRoute busSubRoute = commodStopQueryResult.commonStop().routeStop().busSubRoute();

        holder.routeName.setText(busRoute.getRouteName().getZh_tw() + " 往" +
                (busSubRoute.getDirection().equalsIgnoreCase("0") ? busRoute.getDestinationStopNameZh() : busRoute.getDepartureStopNameZh()));
        holder.stopName.setText(commodStopQueryResult.commonStop().routeStop().stop().getStopName().getZh_tw());
        holder.estimateTime.setText(busEstimateTime.getEstimateTime() + "");
    }

    @Override
    public int getItemCount() {
        return commodStopQueryResults.size();
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {

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
