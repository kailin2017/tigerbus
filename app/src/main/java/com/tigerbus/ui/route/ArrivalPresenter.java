package com.tigerbus.ui.route;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.tigerbus.base.BasePresenter;
import com.tigerbus.data.CityBusService;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusRouteInterface;
import com.tigerbus.data.bus.BusStopOfRoute;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;

public final class ArrivalPresenter extends BasePresenter<ArrivalView> {

    @Override
    public void bindIntent() {
        getView().initDataIntent().subscribe(this::initData);
    }

    private void initData(Bundle defaultBundle) {
        BusRoute busRoute = defaultBundle.getParcelable(CityBusService.BUS_ROUTE);
        ArrayList<BusStopOfRoute> busStopOfRoutesTemp = defaultBundle.getParcelableArrayList(CityBusService.BUS_STOP_OF_ROUTE);
        ArrayList<BusEstimateTime> busEstimateTimesTemp = defaultBundle.getParcelableArrayList(CityBusService.BUS_ESTIMATE_TIME);
        ArrayList<BusStopOfRoute> busStopOfRoutes = filterBusRoute(busStopOfRoutesTemp, busRoute);
        ArrayList<BusEstimateTime> busEstimateTimes = filterBusRoute(busEstimateTimesTemp, busRoute);

        HashMap<String, BusStopOfRoute> busStopOfRouteMap = new HashMap<>();
        for (BusStopOfRoute busStopOfRoute : busStopOfRoutes)
            busStopOfRouteMap.put(getView().getKey(busStopOfRoute), busStopOfRoute);

        Bundle nextBundle = new Bundle();
        nextBundle.putParcelable(CityBusService.BUS_ROUTE, busRoute);
        nextBundle.putSerializable(CityBusService.BUS_STOP_OF_ROUTE, busStopOfRouteMap);
        nextBundle.putParcelableArrayList(CityBusService.BUS_ESTIMATE_TIME, busEstimateTimes);

        render(ArrivalViewState.Success.create(nextBundle));
    }


    private <T extends BusRouteInterface, F extends BusRouteInterface> ArrayList<T> filterBusRoute(@NonNull ArrayList<T> tArrayList, @NonNull F f) {
        ArrayList<T> fitlers = new ArrayList<>();
        Observable.fromIterable(tArrayList)
                .filter(busStopOfRoute -> busStopOfRoute.getRouteUID().equalsIgnoreCase(f.getRouteUID()))
                .subscribe(busStopOfRoute -> fitlers.add(busStopOfRoute));
        return fitlers;
    }

}
