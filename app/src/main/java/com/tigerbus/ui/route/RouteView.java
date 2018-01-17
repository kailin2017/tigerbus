package com.tigerbus.ui.route;

import com.tigerbus.base.BaseView;
import com.tigerbus.base.ViewStateRender;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusRouteInterface;

import io.reactivex.Observable;

public interface RouteView<VR extends ViewStateRender> extends BaseView<VR> {

    Observable<BusRoute> bindIntent();

    static <T extends BusRouteInterface> String getSubRouteKey(T t){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(t.getSubRouteUID().isEmpty()? t.getRouteUID():t.getSubRouteUID());
        stringBuffer.append("_");
        stringBuffer.append(t.getDirection());
        return stringBuffer.toString();
    }

}
