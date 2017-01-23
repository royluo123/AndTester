package com.roy.tester.mytester;

import android.content.Context;

/**
 * Created by Administrator on 2017/1/23.
 */
public class ContextManager {
    private static Context sAppContex = null;

    public static void setAppContex(Context contex){
        sAppContex = contex;
    }

    public static Context getAppContex(){
        return  sAppContex;
    }
}
