package com.tigerbus.data;

import com.tigerbus.BuildConfig;
import com.tigerbus.UnitTestTools;
import com.tigerbus.conn.RetrofitModel;
import com.tigerbus.data.bus.BusA1Data;
import com.tigerbus.data.bus.BusA2Data;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusShape;
import com.tigerbus.data.bus.BusStopOfRoute;
import com.tigerbus.data.bus.BusVersion;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 27)
public final class CityBusServiceTest implements CityBusInterface {

    private final static String CITY = "Taipei", ROUTENAME = "235", ROUTEUID = "TPE10283";
    private final CityBusService serviceSuccess = RetrofitModel.createInstance().create(CityBusService.class);

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        UnitTestTools.async2sync();
    }

    @Test
    public void getBusVersion() throws Exception {
        TestObserver<BusVersion> testObserver = new TestObserver<>();
        serviceSuccess.getBusVersion(CITY).subscribe(testObserver);
        testObserver.assertValue(busVersion -> busVersion.getVersionID() > 130);
    }

    @Test
    public void getBusRoute() throws Exception {
        TestObserver<ArrayList<BusRoute>> testObserver = new TestObserver<>();
        serviceSuccess.getBusRoute(CITY).subscribe(testObserver);
        testObserver.assertValue(busRoutes -> {
            for (BusRoute busRoute : busRoutes) {
                if(busRoute.getRouteName().getZh_tw().equalsIgnoreCase(ROUTENAME)){
                    return true;
                }
            }
            return false;
        });
    }

    @Test
    public void getBusDisplayStopOfRoute() throws Exception {
        TestObserver<ArrayList<BusStopOfRoute>> testObserver = new TestObserver<>();
        serviceSuccess.getBusStopOfRoute(CITY, getRoureUIDQuery(ROUTEUID)).subscribe(testObserver);
        testObserver.assertValue(busStopOfRoutes -> busStopOfRoutes.get(0).getRouteName().getZh_tw().equals(ROUTENAME));
    }

    @Test
    public void getBusN1EstimateTime() throws Exception {
        TestObserver<ArrayList<BusEstimateTime>> testObserver = new TestObserver<>();
        serviceSuccess.getBusEstimateTime(CITY, getRoureUIDQuery(ROUTEUID)).subscribe(testObserver);
        testObserver.assertValue(busEstimateTimes -> busEstimateTimes.get(0).getRouteName().getZh_tw().equals(ROUTENAME));
    }

    @Test
    public void getShape() throws Exception {
        TestObserver<ArrayList<BusShape>> testObserver = new TestObserver<>();
        serviceSuccess.getShape(CITY, getRoureUIDQuery(ROUTEUID)).subscribe(testObserver);
        testObserver.assertValue(busShapes -> busShapes.get(0).getRouteName().getZh_tw().equals(ROUTENAME));
    }

    @Test
    public void getBusA1Data() throws Exception {
        TestObserver<ArrayList<BusA1Data>> testObserver = new TestObserver<>();
        serviceSuccess.getBusA1Data(CITY, getRoureUIDQuery(ROUTEUID)).subscribe(testObserver);
        testObserver.assertValue(busA1Data -> busA1Data.get(0).getRouteName().getZh_tw().equals(ROUTENAME));
    }

    @Test
    public void getBusA2Data() throws Exception {
        TestObserver<ArrayList<BusA2Data>> testObserver = new TestObserver<>();
        serviceSuccess.getBusA2Data(CITY, getRoureUIDQuery(ROUTEUID)).subscribe(testObserver);
        testObserver.assertValue(busA2Data -> busA2Data.get(0).getRouteName().getZh_tw().equals(ROUTENAME));
    }

}