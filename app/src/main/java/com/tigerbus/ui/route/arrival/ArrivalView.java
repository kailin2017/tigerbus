package com.tigerbus.ui.route.arrival;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.tigerbus.R;
import com.tigerbus.base.BaseView;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusRouteInterface;
import com.tigerbus.data.bus.BusSubRoute;
import com.tigerbus.ui.widget.TabPager;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import android.content.Context;

public interface ArrivalView extends BaseView, TabPager {

    Observable<Bundle> bindInitData();

    Observable<Boolean> bindOnTimeData();

    default Observable<Bundle> bundle2Obserable(Bundle bundle) {
        return Observable.just(bundle);
    }

}
