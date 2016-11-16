package com.roy.tester.provider;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.roy.tester.mytester.R;

public class ProviderActivity extends Activity {
    private ContentResolver mContentResolver = null; 
    private Cursor cursor = null;  
         @Override
        protected void onCreate(Bundle savedInstanceState) {
        	// TODO Auto-generated method stub
        	super.onCreate(savedInstanceState);
        	setContentView(R.layout.provider_layout);
        	
        	   TextView tv = (TextView) findViewById(R.id.tv);
				
        		mContentResolver = getContentResolver();  
        		tv.setText("Test");
                for (int i = 0; i < 10; i++) {  
                    ContentValues values = new ContentValues();  
                    values.put(Constant.COLUMN_NAME, "fanrunqi"+i);  
                    mContentResolver.insert(Constant.CONTENT_URI, values);  
                } 
                
            	tv.setText("Test");
                cursor = mContentResolver.query(Constant.CONTENT_URI, new String[]{Constant.COLUMN_ID,Constant.COLUMN_NAME}, null, null, null);  
                if (cursor.moveToFirst()) {
                	String s = cursor.getString(cursor.getColumnIndex(Constant.COLUMN_NAME));
                	tv.setText("Test"+s);
                }
        }
         
}  
