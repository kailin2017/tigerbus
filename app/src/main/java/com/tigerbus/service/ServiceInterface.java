package com.tigerbus.service;

import com.tigerbus.connection.RetrofitModel;
import com.tigerbus.data.CityBusService;
import com.tigerbus.data.bus.RouteStop;

/**
 * Created by Kailin on 2018/1/21.
 */

public interface ServiceInterface {

     String EXTRA_ROUTESTOP = RouteStop.class.getSimpleName();
     String EXTRA_ESTIMATE = CityBusService.BUS_ESTIMATE_TIME;
     CityBusService cityBusService = RetrofitModel.getInstance().create(CityBusService.class);

    default String getKey(RouteStop routeStop) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(routeStop.getBusRoute().getRouteUID());
        stringBuffer.append(routeStop.getBusSubRoute().getSubRouteUID());
        stringBuffer.append(routeStop.getStop().getStopUID());
        return stringBuffer.toString();
    }
}
