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

    @GET("http://ptx.transportdata.tw/MOTC/v2/Bus/DataVersion/City/{City}?$format=JSON")
    Observable<BusVersion> getBusVersion(@NonNull @Path("City") String city);

    @GET("http://ptx.transportdata.tw/MOTC/v2/Bus/Route/City/{City}?$format=JSON")
    Observable<ArrayList<BusRoute>> getBusRoute(@NonNull @Path("City") String city);

    @GET("http://ptx.transportdata.tw/MOTC/v2/Bus/DisplayStopOfRoute/City/{City}/{route}?$format=JSON")
    Observable<ArrayList<BusDisplayStopOfRoute>> getBusDisplayStopOfRoute(
            @NonNull @Path("City") String city, @NonNull @Path("route") String route);

    @GET("http://ptx.transportdata.tw/MOTC/v2/Bus/EstimatedTimeOfArrival/City/{City}/{route}?$format=JSON")
    Observable<ArrayList<BusN1EstimateTime>> getBusN1EstimateTime(
            @NonNull @Path("City") String city, @NonNull @Path("route") String route);


}
