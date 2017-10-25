package com.iqinbao.android.songstv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.iqinbao.android.songstv.R;
import com.iqinbao.android.songstv.base.BaseFragmentActivity;

public class WebActivity extends BaseFragmentActivity {
private WebView webView;
    private WebSettings webSettings=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent intent = getIntent();
        String playurl = intent.getStringExtra("playurl");
        setWebView(playurl);
    }

    private void setWebView(String playurl) {
        webView= (WebView) findViewById(R.id.webView);
        // 获取WebSettings对象
        webSettings=webView.getSettings();
        // 设置WebView支持运行普通的Javascript
        webSettings.setJavaScriptEnabled(true);
      // 设置WebViewClient，保证新的链接地址不打开系统的浏览器窗口
        webView.setWebViewClient(new WebViewClient());
        // 设置WebChromeClient，以支持运行特殊的Javascript
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(playurl);
}
}
