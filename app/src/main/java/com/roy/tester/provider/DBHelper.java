package com.roy.tester.provider;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;  
import android.database.SQLException;  
  
public class DBHelper extends SQLiteOpenHelper {  
  
    private static final String DATABASE_NAME = "finch.db";    
    private static final int DATABASE_VERSION = 1;    
  
    public DBHelper(Context context) {  
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }  
  
    @Override  
    public void onCreate(SQLiteDatabase db)  throws SQLException {  

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ Constant.TABLE_NAME + "("+ Constant.COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," + Constant.COLUMN_NAME +" VARCHAR NOT NULL);");  
    }  
  
    @Override  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)  throws SQLException {  

        db.execSQL("DROP TABLE IF EXISTS "+ Constant.TABLE_NAME+";");  
        onCreate(db);  
    }  
}  
