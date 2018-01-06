package com.tigerbus.base;

import com.google.auto.value.AutoValue;

public interface ViewState {

    @AutoValue
    abstract class Finish implements ViewState {
        public static Finish create() {
            return new AutoValue_ViewState_Finish();
        }
    }

    @AutoValue
    abstract class Loading implements ViewState {

        public static Loading create() {
            return new AutoValue_ViewState_Loading();
        }
    }

    @AutoValue
    abstract class LogInfo implements ViewState {
        public abstract String logMessage();

        public static LogInfo create(String logMessage) {
            return new AutoValue_ViewState_LogInfo(logMessage);
        }
    }

    @AutoValue
    abstract class Exception implements ViewState {
        public abstract String error();

        public static Exception create(String error) {
            return new AutoValue_ViewState_Exception(error);
        }
    }

    @AutoValue
    abstract class Success<T> implements ViewState {
        public abstract T success();

        public static <T> Success create(T success) {
            return new AutoValue_ViewState_Success(success);
        }
    }
}
