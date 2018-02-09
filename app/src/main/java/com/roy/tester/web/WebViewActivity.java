package com.roy.tester.web;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.IntDef;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.roy.tester.mytester.R;
import com.roy.tester.service.TestActivity;
import com.roy.tester.service.TestService;
import com.roy.tester.web.hack.HackManger;
import com.roy.tester.web.hack.HackThread;
import com.roy.tester.web.hack.SubThreadWebView;

/**
 * Created by Administrator on 2018/1/12.
 */

public class WebViewActivity extends Activity {

    private FrameLayout mMainView;
    private WebView mWebView;
    private Button mBtnTest;
    private TextView mTextView;
    private ImageView mTestView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);

        mMainView = (FrameLayout)findViewById(R.id.web_view);
        mTextView = (TextView) findViewById(R.id.web_text_test);
        mBtnTest =(Button)findViewById(R.id.web_btn_test);
        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mWebView == null){
                    mWebView = getWebView();
                }

                mMainView.addView(mWebView);
                mTextView.setText(mWebView.getClass().getCanonicalName());
                load();

//                mMainView.addView(mTestView);
//                Drawable drawable = getResources().getDrawable(R.drawable.error);
//                mTestView.setImageDrawable(drawable);
            }
        });

       initHackWebview();
    }

    private void load(){
       mWebView.loadUrl("https://m.baidu.com");
    }

    private WebView getWebView(){
        Log.i("webview","main webview init start time = " + System.currentTimeMillis());
        WebView webView = new MainWebView(this);
        initWebView(webView);

        Log.i("webview","main webview init end time = " + System.currentTimeMillis());
        return webView;
    }

    private void initWebView(WebView webView){
        WebViewClient client = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        };
        webView.setWebViewClient(client);

        WebChromeClient chromeClient = new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        };
        webView.setWebChromeClient(chromeClient);

        webView.setNetworkAvailable(true);
        webView.clearCache(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setUserAgentString("AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0");
        webView.getSettings().setAppCacheEnabled(true);
    }

    private void initHackWebview(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("webview","hack webview init start time = " + System.currentTimeMillis());
//                if(!HackManger.prepareLooperWithMainThreadQueue(false)){
//                    return;
//                }
//
//                final long tid = Thread.currentThread().getId();
//                try {
//                    long mainThreadId = Looper.getMainLooper().getThread().getId();
//                    HackManger.fakeThreadId(mainThreadId);
//                    WebView webView = new SubThreadWebView(WebViewActivity.this);
////                    initWebView(webView);
//                } finally {
//                    HackManger.prepareLooperWithMainThreadQueue(true);
//                    HackManger.fakeThreadId(tid);
//                }

                if(Build.VERSION.SDK_INT >= 17) {
                    WebSettings.getDefaultUserAgent(WebViewActivity.this);
                }

                Log.i("webview","hack webview init end time = " + System.currentTimeMillis());

 //                mTestView = new ImageView(WebViewActivity.this);


            }
        });

        thread.start();
    }
}
