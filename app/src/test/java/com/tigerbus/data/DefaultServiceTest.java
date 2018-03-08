package com.tigerbus.data;


import com.tigerbus.BuildConfig;
import com.tigerbus.UnitTestTools;
import com.tigerbus.conn.RetrofitModel;
import com.tigerbus.data.detail.City;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.ArrayList;

import io.reactivex.observers.TestObserver;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 27)
public final class DefaultServiceTest {

    private final CityConfigService serviceSuccess = RetrofitModel.createInstance().create(CityConfigService.class);

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        UnitTestTools.async2sync();
    }

    @Test
    public void getCitys() throws Exception {
        TestObserver<ArrayList<City>> testObserver = new TestObserver<>();
        serviceSuccess.getCitys().subscribe(testObserver);
        testObserver.assertValue(cities -> cities.size() >= 6);
    }
}