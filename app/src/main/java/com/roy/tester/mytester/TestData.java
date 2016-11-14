package com.roy.tester.mytester;

import android.app.Activity;

/**
 * Created by Administrator on 2016/11/11.
 */
public class TestData {
    public String title;
    public Class<?> activity;

    public TestData(String title, Class<?> activity){
        this.title = title;
        this.activity = activity;
    }
}
