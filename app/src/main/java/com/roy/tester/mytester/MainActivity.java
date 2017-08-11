package com.roy.tester.mytester;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.roy.tester.aidl.AidlClientActivity;
import com.roy.tester.gp.GpAccountActivity;
import com.roy.tester.greendao.GreenDaoActivity;
import com.roy.tester.okhttp.OkHttpActivity;
import com.roy.tester.process.DaemonProcess;
import com.roy.tester.provider.ProviderActivity;
import com.roy.tester.service.TestActivity;
import com.roy.tester.tpaint.PaintActivity;
import com.roy.tester.vpn.ToyVpnActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView)findViewById(R.id.listView);
        ArrayList<TestData> testDatas = new ArrayList<>();
        testDatas.add(new TestData("Temporary Test", PaintActivity.class));
        testDatas.add(new TestData("Service", TestActivity.class));
        testDatas.add(new TestData("Aidl", AidlClientActivity.class));
        testDatas.add(new TestData("GP Account", GpAccountActivity.class));
        testDatas.add(new TestData("Content Provider", ProviderActivity.class));
        testDatas.add(new TestData("GreenDao", GreenDaoActivity.class));
        testDatas.add(new TestData("OKHttp", OkHttpActivity.class));
        testDatas.add(new TestData("VPN", ToyVpnActivity.class));
        TestAdapter adapter = new TestAdapter(this, testDatas);
        mListView.setAdapter(adapter);

        Log.i("lxw", "start DaemonProcess");
        startService(new Intent(this, DaemonProcess.class));
    }
}
