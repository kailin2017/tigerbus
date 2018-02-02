package com.tigerbus.util;


import android.content.Context;
import android.support.annotation.NonNull;

import com.tigerbus.R;
import com.tigerbus.sqlite.data.RouteStop;

public interface RouteTitle {

    default String getRouteDirectionTitle(@NonNull Context context, RouteStop routeStop) {
        String routeName = "", direction;
        try {
            routeName = routeStop.busSubRoute().getSubRouteName().getZh_tw();
        } catch (Exception e) {
            routeName = routeStop.busRoute().getRouteName().getZh_tw();
        } finally {
            direction = routeStop.busSubRoute().getDirection().equalsIgnoreCase("1") ?
                    routeStop.busRoute().getDepartureStopNameZh() : routeStop.busRoute().getDestinationStopNameZh();
            return String.format(context.getString(R.string.route_go2), routeName, direction);
        }
    }

}
