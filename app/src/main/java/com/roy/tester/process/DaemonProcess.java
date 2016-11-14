package com.roy.tester.process;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.content.Context;
import android.app.ActivityManager;
import java.util.List;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2016/11/12.
 */
public class DaemonProcess extends Service{

    private static Thread mThread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        startDaemon();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startDaemon() {
        if (mThread == null || !mThread.isAlive()) {
            mThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
//                        if (!isServiceAlive(DaemonProcess.this, "com.roy.tester.AidlService")) {
//                            Log.i("lxw", "start AidlService");
//                            startService(new Intent("com.roy.tester.AidlService"));
//                        }
                        logAliveService(DaemonProcess.this);
                        try {
                            Thread.sleep(60000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            mThread.start();
        }
    }

    public boolean isServiceAlive(Context context, String serviceClassName) {
        ActivityManager manager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> running = manager
                .getRunningServices(30);

        for (int i = 0; i < running.size(); i++) {
            if (serviceClassName.equals(running.get(i).service.getClassName())) {
                return true;
            }
        }

        return false;

    }

    public void logAliveService(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> running = manager.getRunningServices(30);

        for (int i = 0; i < running.size(); i++) {
            ActivityManager.RunningServiceInfo info = running.get(i);

//            if (info.process.startsWith("com.roy")) {
                Log.i("lxw", info.service.getClassName() + " is running.");
//            }
        }

    }
}
