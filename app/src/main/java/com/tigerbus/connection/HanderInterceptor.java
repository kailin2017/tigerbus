package com.tigerbus.connection;

import android.webkit.CookieManager;

import com.tigerbus.TigerApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public final class HanderInterceptor implements Interceptor {

    private String USER_AGENT_HEADER_NAME = "User-Agent";
    private String Content_HEADER_NAME = "Content-Type";
    private String COOKIE_HEADER_NAME = "cookie";
    private String SETCOOKIE_HEADER_NAME = "Set-Cookie";
    private CookieManager cookieManager = CookieManager.getInstance();

    private String host;

    public HanderInterceptor(String host) {
        this.host = host;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        cookieManager.setAcceptCookie(true);
        String loadCookie = cookieManager.getCookie(host);
        Request requestWithUserAgent = chain.request().newBuilder()
                .removeHeader(USER_AGENT_HEADER_NAME)
                .removeHeader(Content_HEADER_NAME)
                .removeHeader(COOKIE_HEADER_NAME)
                .addHeader(USER_AGENT_HEADER_NAME, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36")
                .addHeader(Content_HEADER_NAME, "application/x-www-form-urlencoded; charset=utf-8")
                .addHeader(COOKIE_HEADER_NAME, loadCookie == null ? "" : loadCookie)
                .build();
        Response response = chain.proceed(requestWithUserAgent);
        if (response.headers(SETCOOKIE_HEADER_NAME) != null) {
            for (String cookie : response.headers(SETCOOKIE_HEADER_NAME)) {
                cookieManager.setCookie(host, cookie);
            }
            cookieManager.flush();
        }
        return response;
    }
}
