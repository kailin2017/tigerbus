package com.tigerbus.ui.main.sub;

import com.tigerbus.app.BaseView;
import com.tigerbus.app.ViewStateRender;
import com.tigerbus.ui.widget.TabPager;

import io.reactivex.Observable;

public interface HomeView<VR extends ViewStateRender> extends BaseView<VR>, TabPager {

    String COMMONEVENT_DELECT = "COMMONEVENT_DELECT";
    String COMMONEVENT_MOVE = "COMMONEVENT_MOVE";
    String COMMONEVENT_GO = "COMMONEVENT_GO";

    Observable<Boolean> bindInitData();
}
