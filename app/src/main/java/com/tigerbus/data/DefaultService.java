package com.tigerbus.data;

import com.tigerbus.data.detail.City;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface DefaultService {

    @GET("https://us-central1-kailinfire.cloudfunctions.net/citylist")
    Observable<ArrayList<City>> getCitys();
}
