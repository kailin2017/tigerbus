package com.tigerbus.base;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public abstract class BasePresenter<V extends BaseView> extends MvpPresenterImpl<V> {

    private CompositeDisposable disposables = new CompositeDisposable(), uiDisposables = new CompositeDisposable();
    protected Consumer<Disposable> defaultDisposableConsumer = disposable -> addDisposable(disposable);

    public void removeDisposable(@NonNull Disposable disposabled) {
        disposables.remove(disposabled);
    }

    public void addDisposable(@NonNull Disposable... disposable) {
        for (Disposable d : disposable)
            disposables.add(d);
    }

    public void renderDisposable(@NonNull Disposable disposable) {
        addDisposable(disposable);
        render(ViewState.Loading.create());
    }

    public void addUiDisposable(@NonNull Disposable disposabled) {
        uiDisposables.add(disposabled);
    }

    public void clearDisposable() {
        disposables.clear();
    }

    public void clearUiDisposable() {
        uiDisposables.clear();
    }

    protected void throwable(Throwable throwable) {
        render(ViewState.Exception.create(throwable.toString()));
    }

    protected void render(ViewState viewState) {
        getView().render(viewState, (ViewStateRender) getView());
    }

    protected Scheduler threadIO() {
        return Schedulers.io();
    }

    protected Scheduler threadMain() {
        return AndroidSchedulers.mainThread();
    }

    protected <T> Observable<T> rxSwitchThread(Observable<T> observable) {
        return observable.subscribeOn(threadIO()).observeOn(threadMain());
    }
}
