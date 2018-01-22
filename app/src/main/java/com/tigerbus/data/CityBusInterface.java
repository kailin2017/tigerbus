package com.tigerbus.data;

import com.tigerbus.connection.RetrofitModel;
import com.tigerbus.data.bus.BusA1Data;
import com.tigerbus.data.bus.BusA2Data;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusShape;
import com.tigerbus.data.bus.BusStopOfRoute;
import com.tigerbus.data.bus.BusVersion;
import com.tigerbus.data.bus.RouteStop;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CityBusInterface {

    String BUS_VERSION = "BUS_VERSION";
    String BUS_ROUTE = "BUS_ROUTE";
    String BUS_ROUTESTOP = "BUS_ROUTESTOP";
    String BUS_STOP_OF_ROUTE = "BUS_STOP_OF_ROUTE";
    String BUS_ESTIMATE_TIME = "BUS_ESTIMATE_TIME";
    String BUS_SHAPE = "BUS_SHAPE";
    String BUS_A1DATA = "BUS_A1DATA";
    String BUS_A2DATA = "BUS_A2DATA";

    CityBusService cityBusService = RetrofitModel.getInstance().create(CityBusService.class);

    default String getRoureUIDQuery(String routeUID) {
        return "RouteUID eq '" + routeUID + "'";
    }

    default String getRemindQuery(RouteStop routeStop) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("RouteUID eq '");
        stringBuffer.append(routeStop.getBusRoute().getRouteUID());
        if (!routeStop.getBusRoute().getCityName().getEn().contains("Taipei")) {
            stringBuffer.append("' and SubRouteUID eq '");
            stringBuffer.append(routeStop.getBusSubRoute().getSubRouteUID());
        }
        stringBuffer.append("' and Direction eq '");
        stringBuffer.append(routeStop.getBusSubRoute().getDirection());
        stringBuffer.append("' and StopUID eq '");
        stringBuffer.append(routeStop.getStop().getStopUID());
        stringBuffer.append("'");
        return stringBuffer.toString();
    }


}
