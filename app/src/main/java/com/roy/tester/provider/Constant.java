package com.roy.tester.provider;

import android.net.Uri;  

public class Constant {  
      
    public static final String TABLE_NAME = "user";  
      
    public static final String COLUMN_ID = "_id";  
    public static final String COLUMN_NAME = "name";  
       
       
    public static final String AUTOHORITY = "com.roy.myprovider";
    public static final int ITEM = 1;  
    public static final int ITEM_ID = 2;  
       
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/user";  
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/user";  
       
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTOHORITY + "/user");  
}  
