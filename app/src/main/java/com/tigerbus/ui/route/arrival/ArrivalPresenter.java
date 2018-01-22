package com.tigerbus.ui.route.arrival;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.tigerbus.base.BasePresenter;
import com.tigerbus.data.CityBusInterface;
import com.tigerbus.data.CityBusService;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusRouteInterface;
import com.tigerbus.data.bus.BusStopOfRoute;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;

public class ArrivalPresenter<V extends ArrivalView> extends BasePresenter<V> implements CityBusInterface{

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
        BusRoute busRoute = defaultBundle.getParcelable(BUS_ROUTE);
        ArrayList<BusStopOfRoute> busStopOfRoutesTemp = defaultBundle.getParcelableArrayList(BUS_STOP_OF_ROUTE);
        ArrayList<BusEstimateTime> busEstimateTimesTemp = defaultBundle.getParcelableArrayList(BUS_ESTIMATE_TIME);
        ArrayList<BusStopOfRoute> busStopOfRoutes = filterBusRoute(busStopOfRoutesTemp, busRoute);
        ArrayList<BusEstimateTime> busEstimateTimes = filterBusRoute(busEstimateTimesTemp, busRoute);

        HashMap<String, BusStopOfRoute> busStopOfRouteMap = new HashMap<>();
        for (BusStopOfRoute busStopOfRoute : busStopOfRoutes)
            busStopOfRouteMap.put(getView().getKey(busStopOfRoute), busStopOfRoute);

        Bundle nextBundle = new Bundle();
        nextBundle.putParcelable(BUS_ROUTE, busRoute);
        nextBundle.putSerializable(BUS_STOP_OF_ROUTE, busStopOfRouteMap);
        nextBundle.putParcelableArrayList(BUS_ESTIMATE_TIME, busEstimateTimes);

        render(ArrivalViewState.Success.create(nextBundle));
    }
}
