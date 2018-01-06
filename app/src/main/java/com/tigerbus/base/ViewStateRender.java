package com.tigerbus.base;

import com.tigerbus.TigerApplication;
import com.tigerbus.base.log.TlogType;

public interface ViewStateRender<T> {
    void renderLoading();

    default void renderLogInfo(String logMessage) {
        TigerApplication.printLog(TlogType.debug, "renderLogInfo", logMessage);
    }

    default void renderException(String error) {
        TigerApplication.printLog(TlogType.error, "renderException", error);
        renderFinish();
    }

    void renderSuccess(T result);

    void renderFinish();
}
