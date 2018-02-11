package com.tigerbus.ui.main.sub;

import com.tigerbus.base.BaseView;
import com.tigerbus.base.ViewStateRender;

import io.reactivex.Observable;

public interface SearchRouteView<VR extends ViewStateRender> extends BaseView<VR> {

    Observable<Boolean> bindInit();

    Observable<String> bindSearch();
}
