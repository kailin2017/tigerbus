package com.tigerbus.base;

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
}
