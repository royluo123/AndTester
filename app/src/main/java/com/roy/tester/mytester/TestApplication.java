package com.roy.tester.mytester;

import android.app.Application;

/**
 * Created by Administrator on 2017/1/20.
 */
public class TestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ContextManager.setAppContex(this);
    }


}
