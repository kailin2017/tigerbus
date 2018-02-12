package com.tigerbus.connection;

import com.tigerbus.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitModel {

    public static final String JSON_RESULT = "result";
    public static final String JSON_HEADER = "Header";
    public static final String JSON_HEADER_STMT = "Header_STMT";
    public static final String JSON_HEADER_INFO = "Header_INFO";
    public static final String JSON_MESSAGE = "Message";
    public static final String JSON_MESSAGE_STMT = "Message_STMT";
    public static final String JSON_MESSAGE_INFO = "Message_INFO";
    public static final String RESULT_SUCCESS = "SUCCESS";
    public static final String RESULT_FAIL = "FAIL";
    public static final String RESULT_EXCEPTION = "EXCEPTION";
    public static final String RESULT_TIMEOUT = "TIMEOUT";
    public static final String RESULT_Y = "Y";
    public static final String RESULT_N = "N";

    private static Retrofit retrofit;

    // 靜態內部類別單例模式
    public static void createInstance(String serviceHost) {
        retrofit = createRetrofit(serviceHost);
    }

    public static Retrofit getInstance() {
        if (retrofit == null) {
            synchronized (Retrofit.class) {
                if (retrofit == null)
                    createInstance(null);
            }
        }
        return retrofit;
    }

    private static Retrofit createRetrofit(String serviceHost) {
        serviceHost = serviceHost != null ? serviceHost : BuildConfig.MainService;
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new HanderInterceptor(serviceHost))
                .addNetworkInterceptor(chain -> chain.proceed(chain.request()))
                .retryOnConnectionFailure(true)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        return new Retrofit.Builder()
                .baseUrl(serviceHost)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }
}
