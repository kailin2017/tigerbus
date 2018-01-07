package com.tigerbus.ui;

import com.tigerbus.TigerApplication;
import com.tigerbus.base.BasePresenter;
import com.tigerbus.data.CityBusService;
import com.tigerbus.data.bus.BusRoute;

import java.util.ArrayList;
import java.util.Collections;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public final class SearchRoutePresenter extends BasePresenter<SearchRouteView> {

    private final ArrayList<BusRoute> searchData = TigerApplication.getBusRouteData();
    private Consumer<Throwable> throwableConsumer = throwable -> render(SearchRouteViewState.Exception.create(throwable.toString()));

    @Override
    public void bindIntent() {
        Observable<String> stringObservable = getView().bindSearch();
        stringObservable
                .doOnSubscribe(defaultDisposableConsumer)
                .subscribe(s -> searchData(s), throwableConsumer);
    }

    private void searchData(String searchRoute) {
        final ArrayList<BusRoute> searchResult = new ArrayList<>();
        Observable.fromIterable(searchData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BusRoute>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        this.disposable = disposable;
                        addDisposable(disposable);
                    }

                    @Override
                    public void onNext(BusRoute busRoute) {
                        String routeName = busRoute.getRouteName().getZh_tw();
                        int socre = searchSocre(routeName, searchRoute);
                        if (socre > 0) {
                            busRoute.setSearchSocre(socre);
                            searchResult.add(busRoute);
                        }
                        if (searchResult.size() >= 30)
                            onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        render(SearchRouteViewState.Exception.create(e.toString()));
                    }

                    @Override
                    public void onComplete() {
                        removeDisposable(disposable);
                        Collections.sort(searchResult);
                        render(SearchRouteViewState.LogInfo.create(
                                "searchDataSize:" + searchData.size() +
                                        "\nsearchRoute:" + searchRoute +
                                        "\nsearchCount:" + searchResult.size() +
                                        "\nsearchResult:" + TigerApplication.object2String(searchResult)));
                        render(SearchRouteViewState.Success.create(searchResult));
                        render(SearchRouteViewState.Finish.create());
                    }
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
