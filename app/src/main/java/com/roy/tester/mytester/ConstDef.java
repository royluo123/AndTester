package com.roy.tester.mytester;

/**
 * ****************************************************************************
 * Copyright (C) 2004 - 2016 UCWeb Inc. All Rights Reserved.
 * <p/>
 * Description : ConstDef
 * <p/>
 * Creation :2016/7/1.
 * <p/>
 * Author : huake.ghk@alibaba-inc.com
 * ****************************************************************************
 */
public class ConstDef {

    public static final String VALUE_TRUE  = "1";
    public static final String VALUE_FALSE = "0";

    /**
     * Google play包名
     * Reference: http://developer.android.com/reference/com/google/android/gms/common/GooglePlayServicesUtil.html
     */
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";

    public static final String FACEBOOK_APP_PACKAGE_NAME = "com.facebook.katana";

    /**
     * 1秒
     */
    public final static int ONE_SECOND = 1000;
    /**
     * 1分钟
     */
    public final static int ONE_MINUTE = 60 * 1000;
    /**
     * 15分钟
     */
    public final static int FIFTEEN_MINUTE = 900 * 1000;
    /**
     * 半小时
     */
    public final static int HALF_HOUR = 1800 * 1000;
    /**
     * 一小时
     */
    public final static int AN_HOUR = 1 * 3600 * 1000;
    /**
     * 2小时
     */
    public final static int TWO_HOUR = 2 * 3600 * 1000;
    /**
     * 三小时
     */
    public final static int THREE_HOUR = 3 * 3600 * 1000;

    /**
     * 6小时
     */
    public final static int SIX_HOUR = 6 * 3600 * 1000;

    /**
     * 48小时
     */
    public final static int FORTYEIGHT_HOUR = 48 * 3600 * 1000;

    /**
     * 12小时
     */
    public final static int TWELVE_HOUR = 12 * 3600 * 1000;

    /**
     * 1天
     */
    public final static int A_DAY = 24 * 60 * 60 * 1000;

    /**
     * 3天
     */
    public final static int THREE_DAY = 3 * 24 * 60 * 60 * 1000;

    /**
     * 7天/一周
     */
    public final static int SEVEN_DAY = 7 * 24 * 60 * 60 * 1000;

    /**
     * 14天
     */
    public final static int FOURTEEN_DAY = 14 * A_DAY;

    public static final int INVALID_VALUE = -1 ;

    public static final String KEY_URL = "url";
    public static final String KEY_URL_LOAD_STATE = "loadstate";
    public final static String UC_NEWS_APP = "UCNewsApp";
    public final static String UC_NEWS_MINI = "UCNewsAppMini";
    public final static String UC_NEWS_MINI_DEBUG_FILE_NAME = "mini_iflow_debug.ini";

    public static final int LOADING_STATE_T0 = 0;
    public static final int LOADING_STATE_T1 = 1;
    public static final int LOADING_STATE_T2 = 2;
    public static final int LOADING_STATE_T3 = 3;



    public static final int CHILD_CHANNEL_TAB  = 1;
    public static final int CHANNEL_TAB  = 2;
    public static final int SPECIAL_WEBVIEW_TAB = 3;

}
