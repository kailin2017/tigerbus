package com.tigerbus.ui.route.arrival;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.tigerbus.R;
import com.tigerbus.TigerApplication;
import com.tigerbus.app.BaseFragment;
import com.tigerbus.app.ViewStateRender;
import com.tigermvp.annotation.FragmentView;
import com.tigermvp.annotation.ViewInject;
import com.tigerbus.sqlite.BriteDB;
import com.tigerbus.sqlite.data.CommonStopType;
import com.tigerbus.sqlite.data.RouteStop;
import com.tigerbus.ui.route.RouteInterface;
import com.tigerbus.ui.widget.PagerRecyclerAdapter;
import com.tigerbus.ui.widget.PagerRecyclerObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

@FragmentView(layout = R.layout.route_arrival_fragment)
public final class ArrivalMainFragment extends BaseFragment<ArrivalMainView, ArrivalMainPresenter>
        implements ArrivalMainView, ViewStateRender<ArrayList<PagerRecyclerObj>> {

    private PublishSubject<Integer> typelistSubject = PublishSubject.create();
    private PublishSubject<Boolean> bindOnTimeSubject = PublishSubject.create();
    private BottomSheetBehavior bottomSheetBehavior;
    private RouteInterface routeInterface;

    @ViewInject(R.id.tablayout)
    private TabLayout tabLayout;
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.bottom_sheet)
    private NestedScrollView sheetView;

    @ViewInject(R.id.sheetitem_remind)
    private TextView remidSheetItem;
    @ViewInject(R.id.sheetitem_station_save)
    private TextView stationSaveSheetItem;
    @ViewInject(R.id.sheetitem_station_lcaotion)
    private TextView stasionLocationSheetItem;
    @ViewInject(R.id.sheetitem_station_view)
    private TextView stationViewSheetItem;

    public static ArrivalMainFragment newInstance(Bundle bundle) {
        ArrivalMainFragment arrivalMainFragment = new ArrivalMainFragment();
        arrivalMainFragment.setArguments(bundle);
        return arrivalMainFragment;
    }

    @Override
    public ArrivalMainPresenter createPresenter() {
        return new ArrivalMainPresenter(BriteDB.getInstance(application), context);
    }

    @Override
    public Observable<Bundle> bindInitData() {
        return bundle2Obserable(getArguments());
    }

    @Override
    public Observable<Object> bindClickRemind() {
        return rxClick(remidSheetItem);
    }

    @Override
    public Observable<Object> bindClickStationSave() {
        return rxClick(stationSaveSheetItem);
    }

    @Override
    public Observable<Object> bindClickStationLocation() {
        return rxClick(stasionLocationSheetItem);
    }

    @Override
    public Observable<Object> bindClickStationView() {
        return rxClick(stationViewSheetItem);
    }

    @Override
    public Observable<MotionEvent> bindTouchPager() {
        return rxTouch(viewPager);
    }

    @Override
    public Observable<Integer> bindTypeList() {
        return typelistSubject;
    }

    @Override
    public Observable<Boolean> bindOnTimeData() {
        return bindOnTimeSubject;
    }

    @Override
    public void showTypeList() {
        HashMap<Integer, CommonStopType> commodStopTypeList = application.getCommodStopTypes();
        List<String> item = new ArrayList<>();
        for (int i : commodStopTypeList.keySet()) {
            item.add(commodStopTypeList.get(i).type());
        }
        item.add(getString(R.string.dialog_add));
        showListDialog(context, item.toArray(new String[]{}), this::typeListOnSelection);
    }

    public void showAddType(){
        showEditTextDialog(context,"",presenter::insertCommmonStopType);
    }

    @Override
    public void goArrivalMap(RouteStop routeStop) {
        routeInterface.goArrivalMap(routeStop);
    }

    private void typeListOnSelection(DialogInterface dialogInterface, int i) {
        typelistSubject.onNext(i + 1);
        dialogInterface.dismiss();
    }

    @Override
    public void hideBottomSheet() {
        if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    @Override
    public void showBottomSheet() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        routeInterface = (RouteInterface) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        bindOnTimeSubject.onNext(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        hideBottomSheet();
    }

    public void initView() {
        bottomSheetBehavior = BottomSheetBehavior.from(sheetView);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        hideBottomSheet();
    }

    @Override
    public void renderLoading() {
        showProgressDialog(context);
    }

    @Override
    public void renderSuccess(ArrayList<PagerRecyclerObj> pagerRecyclerObjs) {
        PagerRecyclerAdapter pagerRecyclerAdapter = new PagerRecyclerAdapter(tabLayout, pagerRecyclerObjs);
        initTabPager(viewPager, tabLayout, pagerRecyclerAdapter);
    }

    @Override
    public void renderFinish() {
        dimessProgressDialog();
    }

}
