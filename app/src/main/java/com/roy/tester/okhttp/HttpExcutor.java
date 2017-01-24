package com.roy.tester.okhttp;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Headers;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/1/23.
 */
public class HttpExcutor {
    public static interface ExcuteListenner{
        public void onExcuteSuccess(String result);
        public void onExcuteFaild(String msg);
    }

    private String mUrl = null;
    private ExcuteListenner mListener = null;

    public HttpExcutor(String url){
        mUrl = url;
    }

    public void setExcuteListener(ExcuteListenner listener){
        mListener = listener;
    }

    public void excute(){
        if(mUrl == null){
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = HttpClientManager.getInstance().getHttpClient();

                try {
                    Request request = new Request.Builder()
                            .url(mUrl)
                            .build();

                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()){
                        mListener.onExcuteFaild("no response");
                    }

                    Headers responseHeaders = response.headers();
                    for (int i = 0; i < responseHeaders.size(); i++) {
                        Log.i("",responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    ResponseBody body = response.body();
                    if(mListener != null){
                        mListener.onExcuteSuccess(body.string());
                    }
                }catch (Exception e){
                    if(mListener != null){
                        mListener.onExcuteFaild(e.getMessage());
                    }
                }

            }
        }).start();

    }
}
