package com.tigerbus.ui.route;



import com.tigerbus.base.BaseView;
import com.tigerbus.base.ViewStateRender;
import com.tigerbus.data.bus.BusRoute;

import io.reactivex.Observable;

public interface RouteView<VR extends ViewStateRender> extends BaseView<VR> {

    Observable<BusRoute> bindStopOfRoute();

}
