package com.tigerbus.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;

import com.tigerbus.R;
import com.tigerbus.base.BaseFragment;
import com.tigerbus.base.MvpPresenter;
import com.tigerbus.base.annotation.FragmentView;
import com.tigerbus.base.annotation.ViewInject;

@FragmentView(mvp = false,layout = R.layout.webview_content)
public final class WebViewFragment extends BaseFragment implements WebViewInterface{

    @ViewInject(R.id.webview)
    private WebView webView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWebview(webView);
    }

    @Override
    public MvpPresenter createPresenter() {
        return null;
    }
}
