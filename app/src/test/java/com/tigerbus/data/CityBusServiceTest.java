package com.tigerbus.data;

import com.tigerbus.BuildConfig;
import com.tigerbus.UnitTestTools;
import com.tigerbus.conn.RetrofitModel;
import com.tigerbus.data.bus.BusRoute;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.ArrayList;

import io.reactivex.Observable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 27)
public final class CityBusServiceTest {

    private final static String CITY = "Taipei", ROUTENAME = "235", ROUTEUID = "TPE10283";
    private final CityBusService serviceSuccess = RetrofitModel.createInstance().create(CityBusService.class);

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        UnitTestTools.async2sync();
    }

    @Test
    public void getBusVersion() throws Exception {
        UnitTestTools.rxJavaTest(result -> assertTrue(result.getVersionID() >= 72), serviceSuccess.getBusVersion(CITY));
    }

    @Test
    public void getBusRoute() throws Exception {
        UnitTestTools.rxJavaTest(
                result -> {
                    ArrayList<BusRoute> check = new ArrayList<>();
                    Observable.fromIterable(result).filter(busRoute -> busRoute.getRouteName().getZh_tw().equalsIgnoreCase(ROUTENAME)).subscribe(busRoute -> check.add(busRoute));
                    assertEquals(check.size(), 5);
                }, serviceSuccess.getBusRoute(CITY));
    }

    @Test
    public void getBusDisplayStopOfRoute() throws Exception {
        UnitTestTools.rxJavaTest(
                result -> assertTrue(result.get(0).getRouteName().getZh_tw().equals(ROUTENAME)),
                serviceSuccess.getBusStopOfRoute(CITY, CityBusService.getRoureUIDQuery(ROUTEUID)));
    }

    @Test
    public void getBusN1EstimateTime() throws Exception {
        UnitTestTools.rxJavaTest(
                result -> assertTrue(result.get(0).getRouteName().getZh_tw().equals(ROUTENAME)),
                serviceSuccess.getBusEstimateTime(CITY, CityBusService.getRoureUIDQuery(ROUTEUID)));
    }

    @Test
    public void getShape() throws Exception {
        UnitTestTools.rxJavaTest(
                result -> assertTrue(result.get(0).getRouteName().getZh_tw().equals(ROUTENAME)),
                serviceSuccess.getShape(CITY, CityBusService.getRoureUIDQuery(ROUTEUID)));
    }

    @Test
    public void getBusA1Data() throws Exception {
        UnitTestTools.rxJavaTest(
                result -> assertTrue(result.get(0).getRouteName().getZh_tw().equals(ROUTENAME)),
                serviceSuccess.getBusA1Data(CITY, CityBusService.getRoureUIDQuery(ROUTEUID)));
    }

    @Test
    public void getBusA2Data() throws Exception {
        UnitTestTools.rxJavaTest(
                result -> assertTrue(result.get(0).getRouteName().getZh_tw().equals(ROUTENAME)),
                serviceSuccess.getBusA1Data(CITY, CityBusService.getRoureUIDQuery(ROUTEUID)));
    }

}