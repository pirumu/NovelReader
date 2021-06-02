package com.myproject.novel.ui.novel;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.myproject.novel.R;
import com.myproject.novel.local.util.JavaScriptInterface;
import com.myproject.novel.local.util.UC;
import com.myproject.novel.net.C;


public class ReadActivity extends AppCompatActivity {

    private String url = C.BASE_URL + "/read/";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        WebView myWebView = findViewById(R.id.novel_read_webview);
        WebSettings webSettings = myWebView.getSettings();
        myWebView.clearCache(true);
        webSettings.setJavaScriptEnabled(true);
        String chapterId = getIntent().getStringExtra(UC.CHAPTER_ID);
        String novelId = getIntent().getStringExtra(UC.NOVEL_ID);
        url = url + novelId + "/" + chapterId;
        myWebView.loadUrl(url);
        myWebView.addJavascriptInterface(new JavaScriptInterface(this, myWebView), UC.READER_HANDLER);
        WebView.setWebContentsDebuggingEnabled(true);


        settingSwipeRefresh(myWebView);
    }


    private void settingSwipeRefresh(WebView myWebView) {
        final SwipeRefreshLayout srl = findViewById(R.id.swipe_refresh);
        srl.setColorSchemeResources(R.color.home, R.color.menu, R.color.popular);
        srl.setRefreshing(true);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myWebView.loadUrl(url);
            }
        });
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                srl.setRefreshing(false);
            }
        });
    }

    public void webviewClickBack() {

        Handler handler = new Handler(Looper.getMainLooper());
        class MyThread implements Runnable {
            public void run() {
                onBackPressed();
            }
        }
        handler.post(new MyThread());
    }
}