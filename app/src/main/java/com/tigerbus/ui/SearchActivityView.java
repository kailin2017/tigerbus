package com.tigerbus.ui;

import com.tigerbus.base.BaseView;
import com.tigerbus.base.ViewState;

import io.reactivex.Observable;

public interface SearchActivityView extends BaseView {

    String BUSROUTES = "BUSROUTES";

    void render(ViewState viewState);

    Observable<Boolean> bindIntent();
}
