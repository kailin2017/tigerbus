package com.tigerbus.ui.route.arrival;

import android.os.Bundle;

import com.tigerbus.app.BaseView;
import com.tigerbus.ui.widget.TabPager;

import io.reactivex.Observable;

public interface ArrivalView extends BaseView, TabPager {

    Observable<Bundle> bindInitData();

    Observable<Boolean> bindOnTimeData();

    default Observable<Bundle> bundle2Obserable(Bundle bundle) {
        return Observable.just(bundle);
    }

}
