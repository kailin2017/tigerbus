package com.tigerbus.base;

import android.os.Bundle;

import com.google.auto.value.AutoValue;

import io.reactivex.disposables.Disposable;

public interface ViewState {
    @AutoValue
    abstract class Finish implements ViewState {
        public static Finish create() {
            return new AutoValue_ViewState_Finish();
        }
    }

    @AutoValue
    abstract class Loading implements ViewState {
        public abstract Disposable disposable();

        public static Loading create(Disposable disposable) {
            return new AutoValue_ViewState_Loading(disposable);
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
    abstract class Success implements ViewState {
        public abstract Bundle bundle();

        public static Success create(Bundle bundle) {
            return new AutoValue_ViewState_Success(bundle);
        }
    }
}
