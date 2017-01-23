package com.roy.tester.provider;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.roy.tester.mytester.R;

public class ProviderActivity extends Activity {
    private ContentResolver mContentResolver = null; 
    private Cursor cursor = null;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_layout);

        tv = (TextView) findViewById(R.id.provider_tv);
        tv.setText("first data");

        mContentResolver = getContentResolver();

        Button btn1 = (Button) findViewById(R.id.provider_button1);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 10; i++) {
                    ContentValues values = new ContentValues();
                    values.put(Constant.COLUMN_NAME, "input data "+i);
                    mContentResolver.insert(Constant.CONTENT_URI, values);
                }

                tv.setText("Input data");
            }
        });

        Button btn2 = (Button) findViewById(R.id.provider_button2);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                cursor = mContentResolver.query(Constant.CONTENT_URI, new String[]{Constant.COLUMN_ID,Constant.COLUMN_NAME}, null, null, null);
                if (cursor.moveToFirst()) {
                    String s = cursor.getString(cursor.getColumnIndex(Constant.COLUMN_NAME));
                    tv.setText("Test"+s);
                }
            }
        });

    }
         
}  
