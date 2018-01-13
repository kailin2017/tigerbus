package com.tigerbus.data;

import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusStopOfRoute;
import com.tigerbus.data.bus.BusVersion;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CityBusService {

    String BUS_VERSION = "BUS_VERSION";
    String BUS_ROUTE = "BUS_ROUTE";
    String BUS_STOP_OF_ROUTE = "BUS_STOP_OF_ROUTE";
    String BUS_ESTIMATE_TIME = "BUS_ESTIMATE_TIME";

    @GET("http://ptx.transportdata.tw/MOTC/v2/Bus/DataVersion/City/{City}?$format=JSON")
    Observable<BusVersion> getBusVersion(@NonNull @Path("City") String city);

    @GET("http://ptx.transportdata.tw/MOTC/v2/Bus/Route/City/{City}?$format=JSON")
    Observable<ArrayList<BusRoute>> getBusRoute(@NonNull @Path("City") String city);

    @GET("http://ptx.transportdata.tw/MOTC/v2/Bus/StopOfRoute/City/{City}/{route}?$format=JSON")
    Observable<ArrayList<BusStopOfRoute>> getBusStopOfRoute(
            @NonNull @Path("City") String city, @NonNull @Path("route") String route);

    @GET("http://ptx.transportdata.tw/MOTC/v2/Bus/EstimatedTimeOfArrival/City/{City}/{route}?$format=JSON")
    Observable<ArrayList<BusEstimateTime>> getBusEstimateTime(
            @NonNull @Path("City") String city, @NonNull @Path("route") String route);


}
