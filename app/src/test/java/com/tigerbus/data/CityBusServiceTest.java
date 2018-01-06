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

    private final CityBusService serviceSuccess = RetrofitModel.createInstance().create(CityBusService.class);

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        UnitTestTools.async2sync();
    }

    @Test
    public void getBusVersion() throws Exception {
        UnitTestTools.rxJavaTest(result -> assertTrue(result.getVersionID() >= 72), serviceSuccess.getBusVersion("Taipei"));
    }

    @Test
    public void getBusRoute() throws Exception {
        UnitTestTools.rxJavaTest(
                result -> {
                    ArrayList<BusRoute> check = new ArrayList<>();
                    Observable.fromIterable(result).filter(busRoute -> busRoute.getRouteName().getZh_tw().equalsIgnoreCase("235")).subscribe(busRoute -> check.add(busRoute));
                    assertEquals(check.size(), 1);
                }, serviceSuccess.getBusRoute("Taipei"));
    }

    @Test
    public void getBusDisplayStopOfRoute() throws Exception {
        UnitTestTools.rxJavaTest(
                result -> assertTrue(result.get(0).getRouteName().equals("235")),
                serviceSuccess.getBusDisplayStopOfRoute("Taipei", "235"));
    }

    @Test
    public void getBusN1EstimateTime() throws Exception {
        UnitTestTools.rxJavaTest(
                result -> assertTrue(result.get(0).getRouteName().equals("235")),
                serviceSuccess.getBusN1EstimateTime("Taipei", "235"));
    }

}