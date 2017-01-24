package com.roy.tester.okhttp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.roy.tester.mytester.R;

/**
 * Created by Administrator on 2017/1/23.
 */
public class OkHttpActivity extends Activity implements HttpExcutor.ExcuteListenner{
    private TextView mTextView;
    HttpExcutor mHttpExcutor;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okhttp_layout);

        mHandler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    if(msg.obj instanceof  String){
                        String result = (String)msg.obj;
                        mTextView.setText(result);
                    }
                }
            }
        };

        mHttpExcutor = new HttpExcutor("https://m.baidu.com");
        mHttpExcutor.setExcuteListener(this);

        mTextView = (TextView)findViewById(R.id.ok_http_text);
        Button btnGet = (Button)findViewById(R.id.ok_http_btn_get);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHttpExcutor.excute();
            }
        });
    }

    @Override
    public void onExcuteSuccess(String result) {
        mHandler.sendMessage(Message.obtain(mHandler, 1, result));
    }

    @Override
    public void onExcuteFaild(String msg) {
        mHandler.sendMessage(Message.obtain(mHandler, 1, msg));
    }
}
