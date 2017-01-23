package com.roy.tester.process;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.os.PowerManager;

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

        if(ACTTION_ALARM.equals(intent.getAction())){
            Toast.makeText(context, "Recrive alarm", Toast.LENGTH_SHORT).show();

            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            PowerManager .WakeLock sWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                            | PowerManager.ACQUIRE_CAUSES_WAKEUP
                            | PowerManager.ON_AFTER_RELEASE, "AlramManager");
            if(sWakeLock != null && !sWakeLock.isHeld()){
                sWakeLock.acquire();
                sWakeLock.release();
            }
        }
    }
}
