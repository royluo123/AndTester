package com.roy.tester.process;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.IBinder;
import android.content.Context;
import android.app.ActivityManager;
import java.util.List;
import java.util.Calendar;
import android.support.annotation.Nullable;
import android.util.Log;
import android.app.PendingIntent;
import android.app.AlarmManager;

/**
 * Created by Administrator on 2016/11/12.
 */
public class DaemonProcess extends Service{
    private static final int INTERVAL = 1000 * 60 * 60 * 24;// 24h

    private static Thread mThread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startDaemon();
        scheduleStart();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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

    public void scheduleStart(){
        Intent intent = new Intent(this, DaemonReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Schedule the alarm!
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.cancel(sender);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 20);
        calendar.set(Calendar.SECOND, 10);
        calendar.set(Calendar.MILLISECOND, 0);

        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), INTERVAL, sender);
    }


}
