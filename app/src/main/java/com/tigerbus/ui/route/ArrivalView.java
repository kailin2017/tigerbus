package com.tigerbus.ui.route;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.tigerbus.R;
import com.tigerbus.base.BaseView;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusSubRoute;
import com.tigerbus.data.bus.BusSubRouteInterface;

import io.reactivex.Observable;

public interface ArrivalView extends BaseView {

    Observable<Bundle> initDataIntent();

    default <T extends BusSubRouteInterface> String getKey(@NonNull T t) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(t.getSubRouteUID());
        stringBuffer.append("_");
        stringBuffer.append(t.getDirection());
        return stringBuffer.toString();
    }

    default String getTitle(@NonNull Context context, @NonNull BusRoute route, @NonNull BusSubRoute subRoute) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(route.getSubRoutes().size() > 2 ? subRoute.getSubRouteName().getZh_tw() + "\n" : "");
        stringBuffer.append(context.getString(R.string.route_go));
        stringBuffer.append(subRoute.getDirection().equals("0") ?
                route.getDestinationStopNameZh() : route.getDepartureStopNameZh());
        return stringBuffer.toString();
    }
}
