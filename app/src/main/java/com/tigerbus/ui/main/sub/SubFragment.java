package com.tigerbus.ui.main.sub;

import com.tigerbus.R;
import com.tigerbus.base.BasePresenter;
import com.tigerbus.base.BaseView;
import com.tigerbus.base.MvpFragment;
import com.tigerbus.base.annotation.FragmentView;


@FragmentView(layout = R.layout.default_recycler)
public abstract class SubFragment<V extends BaseView, P extends BasePresenter<V>> extends MvpFragment<V, P> {

    public abstract String getTitle();

    public abstract int getIconRes();
}
