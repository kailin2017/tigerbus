package com.tigerbus.base;

import android.view.MotionEvent;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.Observable;

public interface BaseView<VR extends ViewStateRender> extends MvpView {

    default void render(ViewState viewState) {
    }

    default void render(ViewState viewState, VR vr) {
        if (viewState instanceof ViewState.Loading) {
            vr.renderLoading();
        } else if (viewState instanceof ViewState.LogInfo) {
            vr.renderLogInfo(((ViewState.LogInfo) viewState).logMessage());
        } else if (viewState instanceof ViewState.Exception) {
            vr.renderException(((ViewState.Exception) viewState).error());
        } else if (viewState instanceof ViewState.Success) {
            vr.renderSuccess(((ViewState.Success) viewState).success());
        } else if (viewState instanceof ViewState.Finish) {
            vr.renderFinish();
        }
    }

    default Observable<Object> rxClick(View view){
        return RxView.clicks(view);
    }

    default Observable<MotionEvent> rxTouch(View view){
        return RxView.touches(view);
    }
}
