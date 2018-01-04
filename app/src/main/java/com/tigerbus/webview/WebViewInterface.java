package com.tigerbus.webview;

import android.annotation.SuppressLint;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.List;

public interface WebViewInterface {

    List<WebView> webviewList = new ArrayList<>(1);

    default void initWebview(WebView webView) {
        webView.setWebChromeClient(new DefaultWebChromeClient());
        webView.setWebViewClient(new DefaultWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webviewList.set(0, webView);
    }

    default void setWebChromeClient(WebChromeClient webChromeClient){
        webviewList.get(0).setWebChromeClient(webChromeClient);
    }

    default void setWebViewClient(WebViewClient webViewClient){
        webviewList.get(0).setWebViewClient(webViewClient);
    }

    @SuppressLint("JavascriptInterface")
    default void addJavaScriptInterface(Object o, String name){
        webviewList.get(0).addJavascriptInterface(o,name);
    }

}
