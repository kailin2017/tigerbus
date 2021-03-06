package com.tigerbus.ui.main.sub;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tigerbus.R;
import com.tigerbus.TigerApplication;
import com.tigerbus.app.BaseFragment;
import com.tigerbus.app.ViewStateRender;
import com.tigerbus.data.CityBusInterface;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.detail.City;
import com.tigerbus.ui.route.RouteActivity;
import com.tigerbus.util.DiffListCallBack;
import com.tigermvp.annotation.FragmentView;
import com.tigermvp.annotation.ViewInject;
import com.tigermvp.log.TlogType;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

@FragmentView(layout = R.layout.search_fragmet)
public final class SearchRouteFragment extends BaseFragment<SearchRouteView, SearchRoutePresenter>
        implements SearchRouteView<ViewStateRender>, ViewStateRender<ArrayList<BusRoute>> {

    private PublishSubject<Boolean> bindInitSubject = PublishSubject.create();
    private PublishSubject<String> searchSubject = PublishSubject.create();
    private SearchRouteAdapter searchRouteAdapter = new SearchRouteAdapter();

    @ViewInject(R.id.recyclerview)
    private RecyclerView recyclerView;
    @ViewInject(R.id.searchview)
    private SearchView searchView;
    @ViewInject(R.id.searchfilter)
    private ImageButton searchFilter;

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
    protected void initView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(searchRouteAdapter);

        EditText searchEditText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(Color.BLACK);
        searchEditText.setHintTextColor(Color.GRAY);

        searchView.onActionViewExpanded();
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
    public void onStart() {
        super.onStart();
        bindInitSubject.onNext(true);
        searchRouteAdapter.getPublishSubject().doOnSubscribe(presenter::addDisposable).subscribe(this::adapterClickOnNext);
    }

    public void adapterClickOnNext(BusRoute busRoute) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(CityBusInterface.BUS_ROUTE, busRoute);
        startActivity(getActivity(), RouteActivity.class, bundle);
    }

    @Override
    public Observable<Boolean> bindInit() {
        return bindInitSubject;
    }

    @Override
    public Observable<String> bindSearch() {
        return searchSubject;
    }

    @Override
    public Observable<Object> bindSelectFilter() {
        return rxClick(searchFilter);
    }

    @Override
    public void showMultiChoiceItems() {
        ArrayList<String> arrayList = new ArrayList<>();
        for(City city : application.getCityData()){
            arrayList.add(city.getZh_tw());
        }
        showMultiChoiceItems(context, arrayList.toArray(new String[]{}), presenter::getFilterItems);
    }

    @Override
    public void renderSuccess(ArrayList<BusRoute> searchResult) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
                new DiffListCallBack(searchRouteAdapter.getBusRoutes(), searchResult));
        searchRouteAdapter.setBusRoutes(searchResult);
        diffResult.dispatchUpdatesTo(searchRouteAdapter);
    }
}
