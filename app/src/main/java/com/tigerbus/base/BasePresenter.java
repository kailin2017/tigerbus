package com.tigerbus.base;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BasePresenter<V extends BaseView> extends MvpPresenterImpl<V> {

    private CompositeDisposable disposables = new CompositeDisposable(), uiDisposables = new CompositeDisposable();
    protected Consumer<Throwable> throwableConsumer = throwable -> render(ViewState.Exception.create(throwable.toString()));
    protected Consumer<Disposable> defaultDisposableConsumer = disposable -> addDisposable(disposable);
    protected Consumer<Disposable> renderDisposableConsumer = disposable -> {
        addDisposable(disposable);
        render(ViewState.Loading.create());
    };

    public void removeDisposable(@NonNull Disposable disposabled) {
        disposables.remove(disposabled);
    }

    public void addDisposable(@NonNull Disposable disposabled) {
        disposables.add(disposabled);
    }

    public void clearDisposable() {
        disposables.clear();
    }

    protected void render(ViewState viewState) {
        getView().render(viewState, (ViewStateRender) getView());
    }

    public <T> Observable<T> rxSwitchThread(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
