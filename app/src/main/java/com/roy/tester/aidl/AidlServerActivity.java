package com.roy.tester.aidl;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.roy.tester.mytester.R;

/**
 * Created by Administrator on 2016/11/12.
 */

public class AidlServerActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aidl_server_layout);


        Intent service = new Intent(this, AidlService.class);
        startService(service);
    }
}