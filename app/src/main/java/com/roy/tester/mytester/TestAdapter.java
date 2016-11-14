package com.roy.tester.mytester;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.roy.tester.service.TestActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/11.
 */
public class TestAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<TestData> mData = new ArrayList<TestData>();

    public TestAdapter(Context context){
        mContext = context;
    }

    public TestAdapter(Context context, ArrayList<TestData> data){
        mContext =context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button sButton = new Button(mContext);
        final int index = position;
        sButton.setText(mData.get(index).title);
        sButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent sIntent = new Intent(mContext, mData.get(index).activity);
                mContext.startActivity(sIntent);
            }
        });
        return sButton;
    }
}
