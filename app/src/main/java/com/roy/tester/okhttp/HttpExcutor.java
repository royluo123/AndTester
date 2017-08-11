package com.roy.tester.okhttp;

import java.io.IOException;

import android.util.Log;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import okhttp3.Callback;
import okhttp3.Call;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/1/23.
 */
public class HttpExcutor {
    private static final boolean TEST = true;

    private String mUrl = null;
    private ExcuteListenner mListener = null;
    private String mBody;
    private MediaType mRequestMediaType;

    public static interface ExcuteListenner{
        public void onExcuteSuccess(String result);
        public void onExcuteFaild(String msg);
    }

    public HttpExcutor(String url){
        mUrl = url;
    }

    public void setExcuteListener(ExcuteListenner listener){
        mListener = listener;
    }

    public void get(){
        if(mUrl == null){
            return;
        }

        if(TEST){
            OkHttpClient client = HttpClientManager.getInstance().getHttpClient();

            try {
                Request request = new Request.Builder()
                        .url(mUrl)
                        .header("User-Agent", "OkHttp Tester")
                        .addHeader("Accept", "application/json; q=0.5")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        if(mListener != null){
                            mListener.onExcuteFaild(e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                        Headers responseHeaders = response.headers();
                        for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                            Log.i("okhttp", responseHeaders.name(i) + ": " + responseHeaders.value(i));
                        }
                        if(mListener != null){
                            mListener.onExcuteSuccess(response.body().string());
                        }
                    }
                });

            }catch (Exception e){
                if(mListener != null){
                    mListener.onExcuteFaild(e.getMessage());
                }
            }

        }else{
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
                            Log.i("okhttp",responseHeaders.name(i) + ": " + responseHeaders.value(i));
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

    public void post(){
        OkHttpClient client = HttpClientManager.getInstance().getHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(mRequestMediaType, mBody))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(mListener != null){
                    mListener.onExcuteFaild(e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    Log.i("okhttp", responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
                if(mListener != null){
                    mListener.onExcuteSuccess(response.body().string());
                }
            }
        });
    }
}
