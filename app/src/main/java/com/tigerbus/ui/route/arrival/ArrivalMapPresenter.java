package com.tigerbus.ui.route.arrival;

import android.content.Context;

import com.tigerbus.data.bus.BusA1Data;
import com.tigerbus.data.bus.BusStopOfRoute;
import com.tigerbus.data.detail.PointType;
import com.tigerbus.ui.route.adapter.ArrivalMapAdapter;
import com.tigerbus.ui.route.adapter.MapObj;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public final class ArrivalMapPresenter extends ArrivalPresenter<ArrivalMapView> {

    private PublishSubject<ArrayList<BusA1Data>> busA1DataSubject = PublishSubject.create();
    private ArrayList<ArrivalMapAdapter> arrivalMapAdapters = new ArrayList<>();
    private ArrayList<PointType> pointTypes = new ArrayList<>();
    private Context context;

    public ArrivalMapPresenter(Context context) {
        this.context = context;
    }

    @Override
    protected void initSuccess() {
        ArrayList<MapObj> mapObjs = new ArrayList<>();
        Observable.fromIterable(busRoute.getSubRoutes())
                .subscribe(busSubRoute -> {
                    BusStopOfRoute busStopOfRoute = busStopOfRouteMap.get(getKey(busSubRoute));
                    ArrivalMapAdapter arrivalMapAdapter = new ArrivalMapAdapter(context, busSubRoute, busStopOfRoute, busA1DataSubject);
                    mapObjs.add(new MapObj(getTitle(context, busRoute, busSubRoute), arrivalMapAdapter));
                    arrivalMapAdapters.add(arrivalMapAdapter);
                }, this::throwable, () -> render(ArrivalViewState.Success.create(mapObjs)));
    }

    @Override
    public void bindIntent() {
        super.bindIntent();
        busA1DataSubject.doOnSubscribe(this::addDisposable);
        getView().bindOnTimeData().flatMap(this::loadBusShapeSlipt).subscribe(this::loadBusShapeOnNext);
        getView().bindOnTimeData().flatMap(this::startInterval)
                .flatMap(this::loadBusA1Datas).subscribe(this::pushBusA1Datas, this::throwable);
    }

    private Observable<String> loadBusShapeSlipt(boolean b) {
        return rxSwitchThread(Observable
                .just(busShapes.get(0).getGeometry().replace("LINESTRING (", "").replace(")", ""))
                .flatMap(string -> Observable.fromArray(string.split(", "))))
                .doOnComplete(this::pushPointType);
    }

    private void loadBusShapeOnNext(String string) {
        String geomentrySplit[] = string.split(" ");
        pointTypes.add(new PointType(geomentrySplit[1], geomentrySplit[0]));
    }

    private void pushPointType() {
        for (ArrivalMapAdapter arrivalMapAdapter : arrivalMapAdapters) {
            arrivalMapAdapter.setBusShapePointType(pointTypes);
        }
    }

    private Observable<ArrayList<BusA1Data>> loadBusA1Datas(long l) {
        return rxSwitchThread(cityBusService.getBusA1Data(cityNameEn, routeUID));
    }

    private void pushBusA1Datas(ArrayList<BusA1Data> busA1Datas) {
        busA1DataSubject.onNext(busA1Datas);
    }

}
