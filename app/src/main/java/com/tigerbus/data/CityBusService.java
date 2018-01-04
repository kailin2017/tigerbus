package com.tigerbus.data;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CityBusService {

    String getBusVersion = "getBusVersion";
    String getBusRoute = "getBusRoute";
    String getBusDisplayStopOfRoute = "getBusDisplayStopOfRoute";
    String getBusN1EstimateTime = "getBusN1EstimateTime";

    @GET("http://ptx.transportdata.tw/MOTC/v2/Bus/DataVersion/City/{city}?$format=JSON")
    Observable<BusVersion> getBusVersion(@NonNull @Path("city") String city);

    @GET("http://ptx.transportdata.tw/MOTC/v2/Bus/Route/City/{city}?$format=JSON")
    Observable<ArrayList<BusRoute>> getBusRoute(@NonNull @Path("city") String city);

    @GET("http://ptx.transportdata.tw/MOTC/v2/Bus/DisplayStopOfRoute/City/{city}/{route}?$format=JSON")
    Observable<ArrayList<BusDisplayStopOfRoute>> getBusDisplayStopOfRoute(
            @NonNull @Path("city") String city, @NonNull @Path("route") String route);

    @GET("http://ptx.transportdata.tw/MOTC/v2/Bus/EstimatedTimeOfArrival/City/{city}/{route}?$format=JSON")
    Observable<ArrayList<BusN1EstimateTime>> getBusN1EstimateTime(
            @NonNull @Path("city") String city, @NonNull @Path("route") String route);


}
