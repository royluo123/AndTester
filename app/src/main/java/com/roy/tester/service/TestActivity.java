package com.roy.tester.service;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.ComponentName;
import android.os.IBinder;
import android.os.Message;
import android.os.Handler;
import android.view.View;
import android.annotation.SuppressLint;

import com.roy.tester.mytester.R;

/**
 * Created by Administrator on 2016/11/11.
 */

public class TestActivity extends Activity implements OnClickListener {

    private Intent intent = null;
    private Button btn_start_service;
    private Button btn_stop_service;
    private Button btn_bind_service;
    private Button btn_unbind_service;
    private Button btn_sync_data;
    private EditText et_data;
    private TextView tv_out;
    MyServiceConn myServiceConn;
    TestService.MyBinder binder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_layout);
        intent = new Intent(this, TestService.class);
        myServiceConn = new MyServiceConn();
        setOnClick();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_service:
                //用intent启动Service并传值
                intent.putExtra("data", et_data.getText().toString());
                startService(intent);
                break;
            case R.id.btn_stop_service:
                //停止Service
                stopService(intent);
                break;
            case R.id.btn_bind_service:
                //绑定Service
                bindService(intent, myServiceConn, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind_service:
                //解绑Service
                if (binder != null) {
                    unbindService(myServiceConn);
                }
                break;
            case R.id.btn_sync_data:
                //注意：需要先绑定，才能同步数据
                if (binder != null) {
                    binder.setData(et_data.getText().toString());
                }
                break;
            default:
                break;
        }
    }

    class MyServiceConn implements ServiceConnection {
        // 服务被绑定成功之后执行
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // IBinder service为onBind方法返回的Service实例
            binder = (TestService.MyBinder) service;
            binder.getService().setDataCallback(new TestService.DataCallback() {
                //执行回调函数
                @Override
                public void dataChanged(String str) {
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("str", str);
                    msg.setData(bundle);
                    //发送通知
                    handler.sendMessage(msg);
                }
            });
        }

        @SuppressLint("HandlerLeak")
        Handler handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                //在handler中更新UI
                tv_out.setText(msg.getData().getString("str"));
            };
        };

        // 服务奔溃或者被杀掉执行
        @Override
        public void onServiceDisconnected(ComponentName name) {
            binder = null;
        }
    }

    private void loadUI() {
        btn_start_service = (Button) findViewById(R.id.btn_start_service);
        btn_stop_service = (Button) findViewById(R.id.btn_stop_service);
        btn_bind_service = (Button) findViewById(R.id.btn_bind_service);
        btn_unbind_service = (Button) findViewById(R.id.btn_unbind_service);
        btn_sync_data = (Button) findViewById(R.id.btn_sync_data);
        et_data = (EditText) findViewById(R.id.et_data);
        tv_out = (TextView) findViewById(R.id.tv_out);
    }

    private void setOnClick() {
        loadUI();
        btn_start_service.setOnClickListener(this);
        btn_stop_service.setOnClickListener(this);
        btn_bind_service.setOnClickListener(this);
        btn_unbind_service.setOnClickListener(this);
        btn_sync_data.setOnClickListener(this);
    }

}