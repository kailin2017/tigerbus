package com.tigerbus.ui.main.sub;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tigerbus.R;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusSubRoute;
import com.tigerbus.sqlite.data.CommodStopQueryResult;
import com.tigerbus.sqlite.data.CommonStop;
import com.tigerbus.ui.widget.RecyclerItemTouchHelper;
import com.tigerbus.util.Sec2Min;

import java.util.ArrayList;
import java.util.Collections;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public final class HomeAdapter
        extends RecyclerView.Adapter<HomeAdapter.ViewHolder>
        implements Sec2Min, RecyclerItemTouchHelper.ItemTouchHelperCallback {

    private final ArrayList<CommodStopQueryResult> commodStopQueryResults = new ArrayList<>();
    private final PublishSubject<Bundle> adapterEventSubject;
    private Context context;

    public HomeAdapter(@NonNull PublishSubject<Bundle> adapterEventSubject,
                       @NonNull PublishSubject<ArrayList<CommodStopQueryResult>> publishSubject) {
        this.adapterEventSubject = adapterEventSubject;
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
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.home_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CommodStopQueryResult commodStopQueryResult = commodStopQueryResults.get(position);
        holder.itemLayout.setTag(commodStopQueryResult.commonStop());
        holder.routeName.setText(getRouteNameString(commodStopQueryResult.commonStop()));
        holder.stopName.setText(commodStopQueryResult.commonStop().routeStop().stop().getStopName().getZh_tw());
        holder.estimateTime.setText(sec2Min(context, commodStopQueryResult.busEstimateTime().getEstimateTime()));
    }

    private String getRouteNameString(CommonStop commonStop) {
        BusRoute busRoute = commonStop.routeStop().busRoute();
        BusSubRoute busSubRoute = commonStop.routeStop().busSubRoute();

        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append(busSubRoute.getSubRouteName().getZh_tw());
        } catch (Exception e) {
            stringBuffer.append(busRoute.getRouteName().getZh_tw());
        } finally {
            stringBuffer.append(" 往 ");
            stringBuffer.append(busSubRoute.getDirection().equalsIgnoreCase("0") ?
                    busRoute.getDestinationStopNameZh() : busRoute.getDepartureStopNameZh());
        }
        return stringBuffer.toString();
    }

    @Override
    public int getItemCount() {
        return commodStopQueryResults.size();
    }

    @Override
    public void onItemDelete(int positon) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(HomeView.COMMONEVENT_DELECT, commodStopQueryResults.get(positon).commonStop());
        adapterEventSubject.onNext(bundle);
        commodStopQueryResults.remove(positon);
        notifyItemRemoved(positon);
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(commodStopQueryResults, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--)
                Collections.swap(commodStopQueryResults, i, i - 1);
        }
        notifyItemMoved(fromPosition, toPosition);
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
            itemLayout.setOnClickListener(this::itemOnClick);
        }

        private void itemOnClick(View view) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(HomeView.COMMONEVENT_GO, (CommonStop) view.getTag());
            adapterEventSubject.onNext(bundle);
        }
    }
}
