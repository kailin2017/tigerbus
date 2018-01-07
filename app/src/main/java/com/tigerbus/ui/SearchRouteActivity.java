package com.tigerbus.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.tigerbus.R;
import com.tigerbus.base.BaseActivity;
import com.tigerbus.base.ViewStateRender;
import com.tigerbus.base.annotation.ActivityView;
import com.tigerbus.base.annotation.ViewInject;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.CityBusService;
import com.tigerbus.ui.route.RouteActivity;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


@ActivityView(layout = R.layout.search_activity)
public final class SearchRouteActivity extends BaseActivity<SearchRouteView, SearchRoutePresenter>
        implements SearchRouteView<ViewStateRender>, ViewStateRender<ArrayList<BusRoute>> {

    private final static String TAG = SearchRouteActivity.class.getSimpleName();
    private PublishSubject<String> searchSubject = PublishSubject.create();
    private PublishSubject<ArrayList<BusRoute>> adapterSubject = PublishSubject.create();
    private SearchRouteAdapter adapter = new SearchRouteAdapter(adapterSubject);
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.recyclerview)
    private RecyclerView recyclerView;
    private SearchView searchView;

    @NonNull
    @Override
    public SearchRoutePresenter createPresenter() {
        return new SearchRoutePresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    protected void initView() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_break);
        toolbar.setNavigationOnClickListener(v -> finish());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        presenter.addDisposable(adapter.getPublishSubject().subscribe(busRoute -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable(CityBusService.BUS_ROUTE, busRoute);
            startActivity(RouteActivity.class, bundle);
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_toolbar, menu);
        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty())
                    renderSuccess(new ArrayList<>());
                else
                    searchSubject.onNext(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public Observable<String> bindSearch() {
        return searchSubject;
    }

    @Override
    public void renderLoading() {
    }

    @Override
    public void renderSuccess(ArrayList<BusRoute> searchResult) {
        adapterSubject.onNext(searchResult);
    }

    @Override
    public void renderFinish() {
    }
}
