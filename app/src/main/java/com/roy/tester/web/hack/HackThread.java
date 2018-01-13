package com.roy.tester.web.hack;

import android.os.Looper;

/**
 * Created by Administrator on 2018/1/12.
 */

public class HackThread extends Thread {
    private Runnable mRunnable;
    public HackThread(Runnable runnable){
        super();
        mRunnable = runnable;
    }


    public void run() {
        Looper.prepare();
        if(mRunnable != null){
            mRunnable.run();
        }
        Looper.loop();
    }

}
