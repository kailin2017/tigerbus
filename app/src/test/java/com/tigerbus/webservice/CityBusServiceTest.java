package com.tigerbus.webservice;

import com.tigerbus.BuildConfig;
import com.tigerbus.UnitTestUtil;
import com.tigerbus.conn.RetrofitModel;
import com.tigerbus.data.CityBusInterface;
import com.tigerbus.data.CityBusService;
import com.tigerbus.data.bus.BusA1Data;
import com.tigerbus.data.bus.BusA2Data;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusShape;
import com.tigerbus.data.bus.BusStopOfRoute;
import com.tigerbus.data.bus.BusVersion;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 27)
public final class CityBusServiceTest implements CityBusInterface {

    private final static String CITY = "Taipei", ROUTENAME = "235", ROUTEUID = "TPE10283";
    private final UnitTestUtil unitTestUtil = UnitTestUtil.getInstance();
    private final CityBusService serviceSuccess = RetrofitModel.createInstance().create(CityBusService.class);

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        unitTestUtil.async2sync();
    }

    @Test
    public void getBusVersion() throws Exception {
        Observable<BusVersion> observable = serviceSuccess.getBusVersion(CITY);
        unitTestUtil.getTestObserver(observable).assertValue(
                busVersion -> busVersion.getVersionID() > 130);
    }

    @Test
    public void getBusRoute() throws Exception {
        Observable<ArrayList<BusRoute>> observable = serviceSuccess.getBusRoute(CITY);
        unitTestUtil.getTestObserver(observable).assertValue(busRoutes -> {
            for (BusRoute busRoute : busRoutes) {
                if (busRoute.getRouteName().getZh_tw().equalsIgnoreCase(ROUTENAME)) {
                    return true;
                }
            }
            return false;
        });
    }

    @Test
    public void getBusDisplayStopOfRoute() throws Exception {
        Observable<ArrayList<BusStopOfRoute>> observable =
                serviceSuccess.getBusStopOfRoute(CITY, getRoureUIDQuery(ROUTEUID));
        unitTestUtil.getTestObserver(observable).assertValue(
                busStopOfRoutes -> busStopOfRoutes.get(0).getRouteName().getZh_tw().equals(ROUTENAME));
    }

    @Test
    public void getBusN1EstimateTime() throws Exception {
        Observable<ArrayList<BusEstimateTime>> observable =
                serviceSuccess.getBusEstimateTime(CITY, getRoureUIDQuery(ROUTEUID));
        unitTestUtil.getTestObserver(observable).assertValue(
                busEstimateTimes -> busEstimateTimes.get(0).getRouteName().getZh_tw().equals(ROUTENAME));
    }

    @Test
    public void getShape() throws Exception {
        Observable<ArrayList<BusShape>> observable =
                serviceSuccess.getShape(CITY, getRoureUIDQuery(ROUTEUID));
        unitTestUtil.getTestObserver(observable).assertValue(
                busShapes -> busShapes.get(0).getRouteName().getZh_tw().equals(ROUTENAME));
    }

    @Test
    public void getBusA1Data() throws Exception {
        Observable<ArrayList<BusA1Data>> observable =
                serviceSuccess.getBusA1Data(CITY, getRoureUIDQuery(ROUTEUID));
        unitTestUtil.getTestObserver(observable).assertValue(
                busA1Data -> busA1Data.get(0).getRouteName().getZh_tw().equals(ROUTENAME));
    }

    @Test
    public void getBusA2Data() throws Exception {
        Observable<ArrayList<BusA2Data>> observable =
                serviceSuccess.getBusA2Data(CITY, getRoureUIDQuery(ROUTEUID));
        unitTestUtil.getTestObserver(observable).assertValue(
                busA2Data -> busA2Data.get(0).getRouteName().getZh_tw().equals(ROUTENAME));
    }

}