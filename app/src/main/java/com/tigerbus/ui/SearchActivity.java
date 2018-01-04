package com.tigerbus.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ViewTreeObserver;

import com.tigerbus.R;
import com.tigerbus.base.BaseActivity;
import com.tigerbus.base.ViewState;
import com.tigerbus.base.annotation.ActivityView;
import com.tigerbus.base.annotation.ViewInject;
import com.tigerbus.base.log.TlogType;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;


@ActivityView(layout = R.layout.search_activity)
public final class SearchActivity extends BaseActivity<SearchActivityView, SearchPresenter> implements SearchActivityView {

    private final static String TAG = SearchActivity.class.getSimpleName();
    private PublishSubject<Boolean> progressSublish = PublishSubject.create();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Disposable disposable;

    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.recyclerview)
    private RecyclerView recyclerView;

    @NonNull
    @Override
    public SearchPresenter createPresenter() {
        return new SearchPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        progressSublish.subscribe(b -> {
            if (b)
                showProgress();
            else
                dimessProgress();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
        disposableDisposed();
    }

    protected void disposableDisposed() {
//        if (disposable.isDisposed())
//            disposable.dispose();
    }

    @Override
    public Observable<Boolean> bindIntent() {
        return Observable.just(true);
    }

    @Override
    public void render(ViewState viewState) {
        if (viewState instanceof SearchViewState.Loading) {
            renderLoading(((SearchViewState.Loading) viewState).disposable());
        } else if (viewState instanceof SearchViewState.Exception) {
            renderException(((SearchViewState.Exception) viewState).error());
        } else if (viewState instanceof SearchViewState.Success) {
            renderSuccess(((SearchViewState.Success) viewState).bundle());
        } else if (viewState instanceof SearchViewState.Finish) {
            renderFinish();
        }
    }

    private void renderLoading(Disposable disposable) {
        this.disposable = disposable;
        progressSublish.onNext(true);
    }

    private void renderException(String error) {
        application.printLog(TlogType.error, TAG, error);
    }

    private void renderSuccess(Bundle bundle) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                SearchAdapter adapter = new SearchAdapter(bundle.getParcelableArrayList(BUSROUTES), progressSublish);
                compositeDisposable.add(adapter.getPublishSubject().subscribe(busRoute -> {
                }));
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void renderFinish() {
        disposableDisposed();
    }
}
