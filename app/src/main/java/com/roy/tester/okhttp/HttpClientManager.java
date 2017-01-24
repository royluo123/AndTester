package com.roy.tester.okhttp;

import okhttp3.OkHttpClient;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/1/23.
 */
public class HttpClientManager {
    private static HttpClientManager sInstance = new HttpClientManager();
    private OkHttpClient mHttpClient = null;

    public static HttpClientManager getInstance(){
        return  sInstance;
    }

    public OkHttpClient getHttpClient(){
        if(mHttpClient == null){
            mHttpClient = new OkHttpClient.Builder().
                    readTimeout(30, TimeUnit.SECONDS).
//                    cache(cache).
//                    proxy(proxy).
//                    authenticator(authenticator).
                    build();
        }

        return mHttpClient;
    }

    public OkHttpClient getNewHttpClient(){
        return new OkHttpClient();
    }
}
