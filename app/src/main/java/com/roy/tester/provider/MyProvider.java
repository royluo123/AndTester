package com.roy.tester.provider;

import android.content.ContentProvider;    
import android.content.ContentUris;    
import android.content.ContentValues;    
import android.content.UriMatcher;    
import android.database.Cursor;    
import android.database.SQLException;    
import android.database.sqlite.SQLiteDatabase;    
import android.net.Uri;    
    
public class MyProvider extends ContentProvider {    
    
    DBHelper mDbHelper = null;    
    SQLiteDatabase db = null;    
    
    private static final UriMatcher mMatcher;    
    static{    
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);    
        mMatcher.addURI(Constant.AUTOHORITY,Constant.TABLE_NAME, Constant.ITEM);    
        mMatcher.addURI(Constant.AUTOHORITY, Constant.TABLE_NAME+"/#", Constant.ITEM_ID);    
    }    
    
    @Override    
    public int delete(Uri uri, String selection, String[] selectionArgs) {    
        // TODO Auto-generated method stub    
        return 0;    
    }    
    
    @Override    
    public String getType(Uri uri) {    
        switch (mMatcher.match(uri)) {    
        case Constant.ITEM:    
            return Constant.CONTENT_TYPE;    
        case Constant.ITEM_ID:    
            return Constant.CONTENT_ITEM_TYPE;    
        default:    
            throw new IllegalArgumentException("Unknown URI"+uri);    
        }    
    }    
    
    @Override    
    public Uri insert(Uri uri, ContentValues values) {    
        // TODO Auto-generated method stub    
        long rowId;    
        if(mMatcher.match(uri)!=Constant.ITEM){    
            throw new IllegalArgumentException("Unknown URI"+uri);    
        }    
        rowId = db.insert(Constant.TABLE_NAME,null,values);    
        if(rowId>0){    
            Uri noteUri=ContentUris.withAppendedId(Constant.CONTENT_URI, rowId);    
            getContext().getContentResolver().notifyChange(noteUri, null);    
            return noteUri;    
        }    
    
        throw new SQLException("Failed to insert row into " + uri);    
    }    
    
    @Override    
    public boolean onCreate() {    
        // TODO Auto-generated method stub    
        mDbHelper = new DBHelper(getContext());    
    
        db = mDbHelper.getReadableDatabase();    
    
        return true;    
    }    
    
    @Override    
    public Cursor query(Uri uri, String[] projection, String selection,    
            String[] selectionArgs, String sortOrder) {    
        // TODO Auto-generated method stub    
        Cursor c = null;    
        switch (mMatcher.match(uri)) {    
        case Constant.ITEM:    
            c =  db.query(Constant.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);    
            break;    
        case Constant.ITEM_ID:    
            c = db.query(Constant.TABLE_NAME, projection,Constant.COLUMN_ID + "="+uri.getLastPathSegment(), selectionArgs, null, null, sortOrder);    
            break;    
        default:    
            throw new IllegalArgumentException("Unknown URI"+uri);    
        }    
    
        c.setNotificationUri(getContext().getContentResolver(), uri);    
        return c;    
    }    
    
    @Override    
    public int update(Uri uri, ContentValues values, String selection,    
            String[] selectionArgs) {    
        // TODO Auto-generated method stub    
        return 0;    
    }    
    
}    