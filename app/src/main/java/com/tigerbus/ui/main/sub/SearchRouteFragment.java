package com.tigerbus.ui.main.sub;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.tigerbus.R;
import com.tigerbus.base.BaseFragment;
import com.tigerbus.base.ViewStateRender;
import com.tigerbus.base.annotation.FragmentView;
import com.tigerbus.base.annotation.ViewInject;
import com.tigerbus.data.CityBusService;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.ui.route.RouteActivity;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

@FragmentView(layout = R.layout.search_fragmet)
public final class SearchRouteFragment extends BaseFragment<SearchRouteView, SearchRoutePresenter>
        implements SearchRouteView<ViewStateRender>, ViewStateRender<ArrayList<BusRoute>> {

    private PublishSubject<String> searchSubject = PublishSubject.create();
    private PublishSubject<ArrayList<BusRoute>> adapterSubject = PublishSubject.create();

    @ViewInject(R.id.recyclerview)
    private RecyclerView recyclerView;
    @ViewInject(R.id.searchview)
    private SearchView searchView;

    public static SearchRouteFragment newInstance() {
        SearchRouteFragment fragment = new SearchRouteFragment();
        return fragment;
    }

    @NonNull
    @Override
    public SearchRoutePresenter createPresenter() {
        return new SearchRoutePresenter();
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    protected void initView() {
        SearchRouteAdapter adapter = new SearchRouteAdapter(adapterSubject);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        presenter.addDisposable(adapter.getPublishSubject().subscribe(busRoute -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable(CityBusService.BUS_ROUTE, busRoute);
            startActivity(getActivity(), RouteActivity.class, bundle);
        }));
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
