package com.roy.tester.process;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/11/16.
 */
public class DaemonReceiver extends BroadcastReceiver {

    public static final String ACTTION_ALARM = "com.roy.tester.alarm";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent == null){
            return;
        }
        
        if(intent.getAction().equals(ACTTION_ALARM)){
            Toast.makeText(context, "Recrive alarm", Toast.LENGTH_SHORT).show();
        }
    }
}
