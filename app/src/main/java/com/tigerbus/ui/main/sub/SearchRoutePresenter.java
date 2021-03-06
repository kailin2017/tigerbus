package com.tigerbus.ui.main.sub;

import com.tigerbus.TigerApplication;
import com.tigerbus.app.BasePresenter;
import com.tigerbus.data.bus.BusRoute;

import java.util.ArrayList;
import java.util.Collections;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class SearchRoutePresenter extends BasePresenter<SearchRouteView> {

    private final ArrayList<BusRoute> searchData = TigerApplication.getBusRouteData();
    private final ArrayList<BusRoute> searchResult = new ArrayList<>();
    private final ArrayList<CharSequence> filterItems = new ArrayList<>();

    @Override
    public void bindIntent() {
        getView().bindSelectFilter().subscribe(this::bindSelectFilter);
        Observable<String> stringObservable = getView().bindInit().flatMap(b -> getView().bindSearch());
        stringObservable
                .doOnSubscribe(defaultDisposableConsumer)
                .flatMap(this::searchRoute)
                .subscribe(busRoute -> searchResult.add(busRoute), this::throwable);
    }


    public void getFilterItems(ArrayList<String> selectionItems){
        filterItems.clear();
        filterItems.addAll(selectionItems);
    }

    private void bindSelectFilter(Object o){
        getView().showMultiChoiceItems();
    }

    private Observable<BusRoute> searchRoute(String searchRoute) {
        return Observable.fromIterable(searchData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(busRoute -> {
                    if(filterItems.size()>0)
                        return filterItems.contains(busRoute.getCityName().getZh_tw());
                    else
                        return true;
                })
                .filter(busRoute -> {
                    String routeName = busRoute.getRouteName().getZh_tw();
                    return searchSocre(routeName, searchRoute) > 0 && searchResult.size() <= 40;
                })
                .doOnSubscribe(disposable -> searchResult.clear())
                .doOnComplete(() -> {
                    Collections.sort(searchResult);
                    StringBuffer log = new StringBuffer();
                    log.append("searchDataSize:" + searchData.size());
                    log.append("\nsearchCount:" + searchResult.size());
                    log.append("\nsearchResult:" + TigerApplication.object2String(searchResult));
                    render(SearchRouteViewState.LogInfo.create(log.toString()));
                    render(SearchRouteViewState.Success.create(searchResult));
                    render(SearchRouteViewState.Finish.create());
                });
    }

    private int searchSocre(String string1, String string2) {
        int socre = 0;
        StringBuffer sb1 = new StringBuffer(string1), sb2 = new StringBuffer(string2);
        if (sb1.length() < sb2.length())
            return socre;
        for (int i = 0; i < sb2.length(); i++) {
            boolean isEquals = sb1.substring(i, i + 1).equalsIgnoreCase(sb2.substring(i, i + 1));
            if (isEquals) {
                socre++;
            } else {
                if (i == 0) {
                    return -1;
                }
                socre -= 2;
            }
        }
        return socre;
    }
}
