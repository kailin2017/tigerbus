package com.tigerbus.ui;

import com.tigerbus.base.BaseView;
import com.tigerbus.base.ViewState;

import io.reactivex.Observable;

public interface MainView extends BaseView {

    String MENUDATA = "MENUDATA";
    String VOICEDATA = "VOICEDATA";
    String APPLOGIN = "APPLOGIN";
    String AD = "AD";
    String LOGINAD = "LOGINAD";

    void render(ViewState viewState);

    Observable<Boolean> getInitDataSubject();
}
