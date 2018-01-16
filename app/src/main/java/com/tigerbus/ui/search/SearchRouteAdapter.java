package com.tigerbus.ui.search;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tigerbus.R;
import com.tigerbus.TigerApplication;
import com.tigerbus.base.log.TlogType;
import com.tigerbus.data.bus.BusRoute;

import java.util.ArrayList;

import io.reactivex.subjects.PublishSubject;

public final class SearchRouteAdapter extends RecyclerView.Adapter<SearchRouteAdapter.ViewHolder> {

    private final static String TAG = SearchRouteAdapter.class.getSimpleName();
    private PublishSubject<BusRoute> publishSubject = PublishSubject.create();
    private ArrayList<BusRoute> busRoutes = new ArrayList<>();
    private View.OnClickListener onClickListener = v -> {
        BusRoute busRoute = (BusRoute) v.getTag();
        TigerApplication.printLog(TlogType.debug, TAG, "Click Route : " + busRoute.getRouteName().getZh_tw());
        publishSubject.onNext(busRoute);
    };

    public SearchRouteAdapter(PublishSubject<ArrayList<BusRoute>> adapterSubject) {
        adapterSubject.subscribe(busRoutes -> {
            SearchRouteAdapter.this.busRoutes = busRoutes;
            SearchRouteAdapter.this.notifyDataSetChanged();
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BusRoute busRoute = busRoutes.get(position);
        holder.itemView.setTag(busRoute);
        holder.itemView.setOnClickListener(onClickListener);
        holder.routeName.setText(busRoute.getRouteName().getZh_tw());
        holder.routeDestination.setText(busRoute.getDepartureStopNameZh() + "-" + busRoute.getDestinationStopNameZh());
        holder.routeCity.setText(busRoute.getCityName().getZh_tw());
    }

    @Override
    public int getItemCount() {
        return busRoutes.size();
    }

    public PublishSubject<BusRoute> getPublishSubject() {
        return publishSubject;
    }

    final static class ViewHolder extends RecyclerView.ViewHolder {

        public ConstraintLayout itemView;
        public TextView routeName, routeDestination, routeCity;

        public ViewHolder(View view) {
            super(view);
            itemView = view.findViewById(R.id.item);
            routeName = view.findViewById(R.id.route_name);
            routeDestination = view.findViewById(R.id.route_destination);
            routeCity = view.findViewById(R.id.route_city);
        }
    }
}
