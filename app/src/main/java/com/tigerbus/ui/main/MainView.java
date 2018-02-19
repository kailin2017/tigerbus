package com.tigerbus.ui.main;

import com.tigerbus.app.BaseView;
import com.tigerbus.app.ViewStateRender;

import io.reactivex.Observable;

public interface MainView<VR extends ViewStateRender> extends BaseView<VR>{

    Observable<Boolean> getInitDataSubject();

    void goHome();

    void goSearch();

    void goRemind();
}
