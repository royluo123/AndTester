package com.roy.tester.web;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.HandlerThread;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.roy.tester.mytester.R;
import com.roy.tester.service.TestActivity;
import com.roy.tester.service.TestService;
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
       mWebView.loadUrl("m.baidu.com");
    }

    private WebView getWebView(){
        WebView webView = new MainWebView(this);

        WebViewClient client = new WebViewClient() {

        };
        return webView;
    }

    private void initHackWebview(){
        HackThread thread = new HackThread(new Runnable() {
            @Override
            public void run() {
                mWebView = new SubThreadWebView(WebViewActivity.this);
                mTestView = new ImageView(WebViewActivity.this);

            }
        });

        thread.start();
    }
}
