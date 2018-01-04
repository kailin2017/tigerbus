package com.tigerbus.data;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Kailin on 2017/12/31.
 */

public interface DefaultService {

    @GET("https://us-central1-kailinfire.cloudfunctions.net/citylist")
    Observable<String> getCitys();
}
