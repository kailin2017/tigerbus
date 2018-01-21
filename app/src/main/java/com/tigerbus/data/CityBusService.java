package com.tigerbus.data;

import com.tigerbus.data.bus.BusA2Data;
import com.tigerbus.data.bus.BusA1Data;
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

public interface CityBusService {

    String BUS_VERSION = "BUS_VERSION";
    String BUS_ROUTE = "BUS_ROUTE";
    String BUS_STOP_OF_ROUTE = "BUS_STOP_OF_ROUTE";
    String BUS_ESTIMATE_TIME = "BUS_ESTIMATE_TIME";
    String BUS_SHAPE = "BUS_SHAPE";
    String BUS_A1DATA = "BUS_A1DATA";
    String BUS_A2DATA = "BUS_A2DATA";

    @GET("/MOTC/v2/Bus/DataVersion/City/{City}?$format=JSON")
    Observable<BusVersion> getBusVersion(@NonNull @Path("City") String city);

    @GET("/MOTC/v2/Bus/Route/City/{City}?$format=JSON")
    Observable<ArrayList<BusRoute>> getBusRoute(@NonNull @Path("City") String city);

    @GET("/MOTC/v2/Bus/StopOfRoute/City/{City}?$format=JSON")
    Observable<ArrayList<BusStopOfRoute>> getBusStopOfRoute(
            @NonNull @Path("City") String city, @NonNull @Query("$filter") String uid);

    @GET("/MOTC/v2/Bus/EstimatedTimeOfArrival/City/{City}?$format=JSON")
    Observable<ArrayList<BusEstimateTime>> getBusEstimateTime(
            @NonNull @Path("City") String city, @NonNull @Query("$filter") String uid);

    @GET("/MOTC/v2/Bus/Shape/City/{City}?$format=JSON")
    Observable<ArrayList<BusShape>> getShape(
            @NonNull @Path("City") String city, @NonNull @Query("$filter") String uid);

    @GET("/MOTC/v2/Bus/RealTimeByFrequency/City/{City}?$format=JSON")
    Observable<ArrayList<BusA1Data>> getBusA1Data(
            @NonNull @Path("City") String city, @NonNull @Query("$filter") String uid);

    @GET("/MOTC/v2/Bus/BusEstimateTime/City/{City}?$format=JSON")
    Observable<ArrayList<BusA2Data>> getBusA2Data(
            @NonNull @Path("City") String city, @NonNull @Query("$filter") String uid);

    static String getRoureUIDQuery(String routeUID) {
        return "RouteUID eq '" + routeUID + "'";
    }

    static String getRemindQuery(RouteStop routeStop) {
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
