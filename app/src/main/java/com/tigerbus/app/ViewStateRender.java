package com.tigerbus.app;

import com.tigerbus.TigerApplication;
import com.tigerbus.app.log.TlogType;

public interface ViewStateRender<T> {
    default void renderLoading(){}

    default void renderLogInfo(String logMessage) {
        TigerApplication.printLog(TlogType.debug, "renderLogInfo", logMessage);
    }

    default void renderException(String error) {
        TigerApplication.printLog(TlogType.error, "renderException", error);
        renderFinish();
    }

    void renderSuccess(T result);

    default void renderFinish(){}
}
