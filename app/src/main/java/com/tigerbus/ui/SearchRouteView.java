package com.tigerbus.ui;

import com.tigerbus.base.BaseView;
import com.tigerbus.base.ViewStateRender;

import io.reactivex.Observable;

interface SearchRouteView<VR extends ViewStateRender> extends BaseView<VR> {

    Observable<String> bindIntent();
}
