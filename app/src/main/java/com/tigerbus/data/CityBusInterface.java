package com.tigerbus.data;

import com.tigerbus.connection.RetrofitModel;
import com.tigerbus.sqlite.data.RouteStop;

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
        stringBuffer.append(routeStop.busRoute().getRouteUID());
        if (!routeStop.busRoute().getCityName().getEn().contains("Taipei")) {
            stringBuffer.append("' and SubRouteUID eq '");
            stringBuffer.append(routeStop.busSubRoute().getSubRouteUID());
        }
        stringBuffer.append("' and Direction eq '");
        stringBuffer.append(routeStop.busSubRoute().getDirection());
        stringBuffer.append("' and StopUID eq '");
        stringBuffer.append(routeStop.stop().getStopUID());
        stringBuffer.append("'");
        return stringBuffer.toString();
    }


}
