package com.tigerbus.ui.main.sub;

import com.tigerbus.base.BaseView;
import com.tigerbus.base.ViewStateRender;
import com.tigerbus.ui.widget.TabPager;

import io.reactivex.Observable;

public interface RemindView<VR extends ViewStateRender> extends BaseView<VR>,TabPager {

    Observable<Boolean> bindInitData();
}
