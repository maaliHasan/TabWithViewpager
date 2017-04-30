package com.example.mhasan.tabwithviewpager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mhasan on 4/25/2017.
 * DatabaseHelper
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "usersTest.db";
    public static final String TABLE_NAME = "user";
    public static final String TABLE_DEL = "user";
    private static final String PHOTO_TABLE_CREATE = "create table photo (photo_id integer primary key autoincrement, IMG text not null,user_id INTEGER, FOREIGN KEY (user_id) REFERENCES user (ID));";
    private static final String USER_TABLE_CREATE = "create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY ,NAME TEXT,TITLE TEXT,DESCRIPTION TEXT,PIC TEXT )";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // db.execSQL("CREATE TABLE" + TABLE_NAME + "("+COL_ID+ " INTEGER PRIMARY KEY, " +COL_NAME + " TEXT," +COL_TITLE + "TEXT," +COL_DES + " TEXT," +COL_PIC + " TEXT );");
        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(PHOTO_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_DEL + "'");
        onCreate(db);

    }

    public boolean insertData(int id, String name, String title, String des, String pic) {

        Log.d("inside insert Data", name);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("NAME", name);
        contentValues.put("TITLE", title);
        contentValues.put("DESCRIPTION", des);
        contentValues.put("PIC", pic);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
      //  ContentValues photoValues= new ContentValues();
       // long result = db.insert("photo", null, contentValues);

    }
}
