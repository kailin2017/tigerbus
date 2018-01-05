package com.tigerbus.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.ViewTreeObserver;

import com.tigerbus.R;
import com.tigerbus.TigerApplication;
import com.tigerbus.base.BaseActivity;
import com.tigerbus.base.ViewState;
import com.tigerbus.base.annotation.ActivityView;
import com.tigerbus.base.annotation.ViewInject;
import com.tigerbus.base.log.TlogType;
import com.tigerbus.data.BusRoute;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;


@ActivityView(layout = R.layout.search_activity)
public final class SearchActivity extends BaseActivity<SearchActivityView, SearchPresenter> implements SearchActivityView {

    private final static String TAG = SearchActivity.class.getSimpleName();
    private ArrayList<BusRoute> busRoutes = new ArrayList<>();

    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.recyclerview)
    private RecyclerView recyclerView;
    private SearchView searchView;

    @NonNull
    @Override
    public SearchPresenter createPresenter() {
        return new SearchPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        initData();
        initView();
    }

    protected void initData(){
        for(ArrayList<BusRoute> arrayList : TigerApplication.weakHashMap.values()){
            busRoutes.addAll(arrayList);
        }
    }

    protected void initView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                SearchAdapter adapter = new SearchAdapter(busRoutes);
                compositeDisposable.add(adapter.getPublishSubject().subscribe(busRoute -> {
                }));
                recyclerView.setAdapter(adapter);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startActivity(SearchActivity.class, new Bundle());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
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
        compositeDisposable.add(disposable);
    }

    private void renderException(String error) {
        application.printLog(TlogType.error, TAG, error);
    }

    private void renderSuccess(Bundle bundle) {    }

    private void renderFinish() {
    }
}
