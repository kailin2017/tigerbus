package com.tigerbus.ui.search;

import com.tigerbus.base.BaseView;
import com.tigerbus.base.ViewStateRender;

import io.reactivex.Observable;

public interface SearchRouteView<VR extends ViewStateRender> extends BaseView<VR> {

    Observable<String> bindSearch();
}
