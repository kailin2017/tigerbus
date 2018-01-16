package com.tigerbus.ui;

import com.tigerbus.BuildConfig;
import com.tigerbus.UnitTestTools;
import com.tigerbus.ui.main.MainActivity;
import com.tigerbus.ui.main.MainViewState;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 27)
public final class MainActivityTest {

    private MainActivity mainActivity;

    @Before
    public void setUp() throws Exception {
        ShadowLog.stream = System.out;
        UnitTestTools.async2sync();
        mainActivity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void testActivity() throws Exception {
        assertNotNull(mainActivity);
        assertFalse(mainActivity.progressDialog.isShowing());
    }

    @Test
    public void testMainState() throws Exception {
//        render(MainViewState.Loading.create(Mockito.mock(Disposable.class)));
//        assertTrue(mainActivity.progressDialog.isShowing());
//        render(MainViewState.Finish.create());
//        assertFalse(mainActivity.progressDialog.isShowing());
//        render(MainViewState.Exception.create("Error Msg"));
//        assertTrue(mainActivity.messageDialog.isShowing());
    }

    private void render(MainViewState mainViewState) {
        mainActivity.render(mainViewState);
    }


}