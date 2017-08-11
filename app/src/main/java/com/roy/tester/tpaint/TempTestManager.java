package com.roy.tester.tpaint;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/4.
 */
public class TempTestManager {
    private Context mContext = null;
    private ArrayList<View> mViews = new ArrayList<>();
    private int mIndex = 0;

    public TempTestManager(Context context){
        mContext = context;

        File file = new File("/mnt/sdcard/testpem.log");
        if(file.exists()){
            Log.d("TempTestManager", file.getAbsolutePath());
        }else{
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public View getTempView(){
        if(!mViews.isEmpty()){
            if(mIndex >=0 && mIndex < mViews.size()){
                return  mViews.get(mIndex);
            }

            return mViews.get(0);
        }

        return  new PaintTestView(mContext);
    }
}
