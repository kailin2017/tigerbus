package com.tigerbus.ui.main.sub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tigerbus.R;
import com.tigerbus.sqlite.data.RemindStop;
import com.tigerbus.util.RouteTitle;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


public final class RemindAdapter
        extends RecyclerView.Adapter<RemindAdapter.ViewHolder> implements RouteTitle {

    private PublishSubject<RemindStop> publishSubject = PublishSubject.create();
    private ArrayList<RemindStop> remindStops;
    private Context context;

    public RemindAdapter(@NonNull ArrayList<RemindStop> remindStops) {
        this.remindStops = remindStops;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(
                context).inflate(R.layout.remind_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RemindStop remindStop = remindStops.get(position);
        holder.item.setTag(remindStop);
        holder.routeName.setText(getRouteDirectionTitle(context, remindStop.routeStop()));
        holder.stopName.setText(remindStop.routeStop().stop().getStopName().getZh_tw());
        holder.remindTime.setText(String.format(
                context.getString(R.string.remind_time), remindStop.remindMinute()));
        if (remindStop.isOne()) {
            holder.remindStatus.setVisibility(View.VISIBLE);
        } else {
            holder.remindWeek.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return remindStops.size();
    }

    public Observable<RemindStop> getObservable(){
        return publishSubject;
    }

    final class ViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout item;
        private TextView routeName, stopName, remindTime, remindStatus, remindWeek;

        public ViewHolder(View view) {
            super(view);
            item = view.findViewById(R.id.item);
            item.setOnClickListener(v -> publishSubject.onNext((RemindStop) v.getTag()));
            routeName = view.findViewById(R.id.routename);
            stopName = view.findViewById(R.id.stopname);
            remindTime = view.findViewById(R.id.remind_time);
            remindStatus = view.findViewById(R.id.remind_status);
            remindWeek = view.findViewById(R.id.remind_week);
        }
    }
}
