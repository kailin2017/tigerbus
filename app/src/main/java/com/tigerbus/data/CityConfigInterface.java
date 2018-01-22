package com.tigerbus.data;

import com.tigerbus.connection.RetrofitModel;
import com.tigerbus.data.detail.City;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CityConfigInterface {

    String CITYS = "CITYS";

    CityConfigService cityConfigService = RetrofitModel.getInstance().create(CityConfigService.class);
}
