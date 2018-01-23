package com.tigerbus.data;

import com.tigerbus.data.bus.BusA1Data;
import com.tigerbus.data.bus.BusA2Data;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusShape;
import com.tigerbus.data.bus.BusStopOfRoute;
import com.tigerbus.data.bus.BusVersion;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CityBusService {

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
}
