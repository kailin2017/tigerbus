package com.tigerbus.ui.route.arrival;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.tigerbus.R;
import com.tigerbus.base.BaseView;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusRouteInterface;
import com.tigerbus.data.bus.BusSubRoute;
import com.tigerbus.ui.widget.TabPager;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import android.content.Context;

public interface ArrivalView extends BaseView, TabPager {

    Observable<Bundle> bindInitData();

    void setEstimateSubject(@NonNull PublishSubject<ArrayList<BusEstimateTime>> estimateSubject);

    default Observable<Bundle> bundle2Obserable(Bundle bundle) {
        return Observable.just(bundle);
    }

    default <T extends BusRouteInterface> String getKey(@NonNull T t) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(t.getSubRouteUID());
        stringBuffer.append("_");
        stringBuffer.append(t.getDirection());
        return stringBuffer.toString();
    }

    default String getTitle(@NonNull Context context, @NonNull BusRoute route, @NonNull BusSubRoute subRoute) {
        String routeName = route.getSubRoutes().size() > 2 ? subRoute.getSubRouteName().getZh_tw() + "\n" : "";
        String direction = subRoute.getDirection().equals("0") ? route.getDestinationStopNameZh() : route.getDepartureStopNameZh();
        return String.format(context.getString(R.string.route_go1), routeName, direction);
    }
}
