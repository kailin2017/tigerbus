package com.tigerbus.ui.main.sub;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.tigerbus.R;
import com.tigerbus.base.BaseFragment;
import com.tigerbus.base.ViewStateRender;
import com.tigerbus.base.annotation.FragmentView;
import com.tigerbus.base.annotation.ViewInject;
import com.tigerbus.sqlite.data.RemindStop;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

@FragmentView(mvp = false, layout = R.layout.home_fragment)
public final class RemindFragment extends BaseFragment<RemindView, RemindPresenter>
        implements RemindView<ViewStateRender>, ViewStateRender<ArrayList<RemindStop>> {

    private PublishSubject<Boolean> bindSubject = PublishSubject.create();
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.tablayout)
    private TabLayout tabLayout;

    public static RemindFragment newInstance() {
        RemindFragment fragment = new RemindFragment();
        return fragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onStart() {
        super.onStart();
        bindSubject.onNext(true);
    }

    @Override
    public RemindPresenter createPresenter() {
        return new RemindPresenter();
    }

    @Override
    public void renderSuccess(ArrayList<RemindStop> result) {

    }

    @Override
    public Observable<Boolean> bindInit() {
        return bindSubject;
    }
}
