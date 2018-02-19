package com.tigerbus.ui.main.sub;

import com.tigerbus.app.BaseView;
import com.tigerbus.app.ViewStateRender;

import io.reactivex.Observable;

public interface SearchRouteView<VR extends ViewStateRender> extends BaseView<VR> {

    Observable<Boolean> bindInit();

    Observable<String> bindSearch();
}
