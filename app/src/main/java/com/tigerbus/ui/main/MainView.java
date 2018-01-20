package com.tigerbus.ui.main;

import com.tigerbus.base.BaseView;
import com.tigerbus.base.ViewStateRender;

import io.reactivex.Observable;

public interface MainView<VR extends ViewStateRender> extends BaseView<VR>{

    Observable<Boolean> getInitDataSubject();

    void goHome();

    void goSearch();

    void goRemind();
}
