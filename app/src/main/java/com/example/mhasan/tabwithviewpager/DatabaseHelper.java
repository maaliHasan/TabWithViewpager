package com.example.mhasan.tabwithviewpager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by mhasan on 4/25/2017.
 * DatabaseHelper
 */

class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 29;
    private static final String DATABASE_NAME = "usersDB.db";
    static final String TABLE_NAME = "user";
    private static final String TABLE_DEL = "photo";
    private static final String USER_TABLE_CREATE = "create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY   ,NAME TEXT ,TITLE TEXT,DESCRIPTION TEXT,PIC TEXT )";
    private static final String PHOTO_TABLE_CREATE = "create table photo ( ID INTEGER PRIMARY KEY, IMG text  not null,user_id INTEGER, FOREIGN KEY (user_id) REFERENCES user (ID));";
    static final String[] USER_COLUMNS = {"ID", "NAME", "TITLE", "DESCRIPTION", "PIC"};

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(PHOTO_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_DEL + "'");
        db.execSQL("DROP TABLE IF EXISTS user ");
        onCreate(db);

    }


}
