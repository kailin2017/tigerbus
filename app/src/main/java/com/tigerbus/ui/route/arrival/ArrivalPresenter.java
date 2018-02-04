package com.tigerbus.ui.route.arrival;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.tigerbus.BuildConfig;
import com.tigerbus.R;
import com.tigerbus.base.BasePresenter;
import com.tigerbus.data.CityBusInterface;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusRouteInterface;
import com.tigerbus.data.bus.BusShape;
import com.tigerbus.data.bus.BusStopOfRoute;
import com.tigerbus.data.bus.BusSubRoute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public abstract class ArrivalPresenter<V extends ArrivalView> extends BasePresenter<V> implements CityBusInterface{

    protected String cityNameEn, routeUID;
    protected BusRoute busRoute;
    protected ArrayList<BusShape> busShapes;
    protected HashMap<String, BusStopOfRoute> busStopOfRouteMap = new HashMap<>();

    @Override
    public void bindIntent() {
        getView().bindInitData().subscribe(this::initData);
    }

    protected  <T extends BusRouteInterface, F extends BusRouteInterface> ArrayList<T> filterBusRoute(@NonNull ArrayList<T> tArrayList, @NonNull F f) {
        ArrayList<T> fitlers = new ArrayList<>();
        Observable.fromIterable(tArrayList)
                .filter(busStopOfRoute -> busStopOfRoute.getRouteUID().equalsIgnoreCase(f.getRouteUID()))
                .subscribe(busStopOfRoute -> fitlers.add(busStopOfRoute));
        return fitlers;
    }


    protected void initData(Bundle defaultBundle) {
        busRoute = defaultBundle.getParcelable(BUS_ROUTE);
        cityNameEn = busRoute.getCityName().getEn();
        routeUID = getRoureUIDQuery(busRoute.getRouteUID());

        busShapes = defaultBundle.getParcelableArrayList(BUS_SHAPE);
        ArrayList<BusStopOfRoute> busStopOfRoutesTemp = defaultBundle.getParcelableArrayList(BUS_STOP_OF_ROUTE);
        ArrayList<BusStopOfRoute> busStopOfRoutes = filterBusRoute(busStopOfRoutesTemp, busRoute);

        for (BusStopOfRoute busStopOfRoute : busStopOfRoutes)
            busStopOfRouteMap.put(getKey(busStopOfRoute), busStopOfRoute);

        initSuccess();
    }

    protected abstract void initSuccess();

    protected Observable<Long> startInterval(boolean b) {
        return Observable.interval(BuildConfig.firstTime, BuildConfig.updateTime, TimeUnit.SECONDS, threadIO()).doOnSubscribe(this::addDisposable);
    }

    public <T extends BusRouteInterface> String getKey(@NonNull T t) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(t.getSubRouteUID());
        stringBuffer.append("_");
        stringBuffer.append(t.getDirection());
        return stringBuffer.toString();
    }

    public String getTitle(@NonNull Context context, @NonNull BusRoute route, @NonNull BusSubRoute subRoute) {
        String routeName = route.getSubRoutes().size() > 2 ? subRoute.getSubRouteName().getZh_tw() + "\n" : "";
        String direction = subRoute.getDirection().equals("0") ? route.getDestinationStopNameZh() : route.getDepartureStopNameZh();
        return String.format(context.getString(R.string.route_go1), routeName, direction);
    }
}
