package com.myproject.novel.local.util;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.myproject.novel.ui.novel.ReadActivity;

public class JavaScriptInterface {

    protected ReadActivity parentActivity;
    protected WebView mWebView;

    public JavaScriptInterface(ReadActivity _activity, WebView _webView) {
        parentActivity = _activity;
        mWebView = _webView;

    }

    @JavascriptInterface
    public void clickBack() {

        this.parentActivity.webviewClickBack();
    }

}
