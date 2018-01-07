package com.tigerbus.ui.route;

import com.tigerbus.base.BaseView;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusStopOfRoute;

import java.util.ArrayList;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by Kailin on 2018/1/6.
 */

public interface ArrivalView extends BaseView {

    void setSubject(
            PublishSubject<ArrayList<BusStopOfRoute>> stopOfRouteSubject,
            PublishSubject<ArrayList<BusEstimateTime>> estimateSubject);
}
