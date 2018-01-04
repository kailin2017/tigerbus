package com.tigerbus.webview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.tigerbus.R;
import com.tigerbus.base.BaseActivity;
import com.tigerbus.base.MvpPresenter;
import com.tigerbus.base.annotation.ActivityView;
import com.tigerbus.base.annotation.ViewInject;

@ActivityView(mvp = false,layout = R.layout.webview_activity)
public final class WebViewActivity extends BaseActivity implements WebViewInterface {

    @ViewInject(R.id.webview)
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWebview(webView);
    }

    @NonNull
    @Override
    public MvpPresenter createPresenter() {
        return null;
    }
}
