package com.example.mhasan.tabwithviewpager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by mhasan on 4/25/2017.
 * DatabaseHelper
 */

class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 27;
    private static final String DATABASE_NAME = "usersTest.db";
    public static final String TABLE_NAME = "user";
    private static final String TABLE_DEL = "photo";
    private static final String USER_TABLE_CREATE = "create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY   ,NAME TEXT ,TITLE TEXT,DESCRIPTION TEXT,PIC TEXT )";
    private static final String PHOTO_TABLE_CREATE = "create table photo ( ID INTEGER PRIMARY KEY, IMG text  not null,user_id INTEGER, FOREIGN KEY (user_id) REFERENCES user (ID));";
    public static  final String[] ALL_COLUMNS={"ID","NAME","TITLE","DESCRIPTION","PIC"};
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
        onCreate(db);

    }

    boolean insertData(User user) {
        long result = -1;
        long result2 = -1;
        SQLiteDatabase Db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase Db2 = this.getWritableDatabase();
        ContentValues photoValues = new ContentValues();
        contentValues.put("ID", user.id);
        contentValues.put("NAME", user.fullName);
        contentValues.put("TITLE", user.title);
        contentValues.put("DESCRIPTION", user.description);
        contentValues.put("PIC", user.profilePic);
        result = Db.insert(TABLE_NAME, null, contentValues);

        int size = user.images.size();
        Log.d("size of images", Integer.toString(size));
        for (int j = 0; j < size; j++) {
            String img = user.images.get(j);
            photoValues.put("img", img);
            photoValues.put("user_id", user.id);
            //  result2 = Db2.insert("photo", null, photoValues);
            Log.d("inside", String.valueOf(photoValues));
        }

        Log.d("photoValues", String.valueOf(photoValues.getAsString("img")));
        return !(result == -1 | result2 == -1);

    }

    public ArrayList<User> getData() {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] serProjection = {
                "ID",
                "NAME",
                "TITLE",
                "PIC",
                "DESCRIPTION"
        };


        Cursor userCursor = db.query("user", serProjection, null, null, null, null, null
        );
        ArrayList<User> users = new ArrayList<>();
        ArrayList<User> usersWithImg = new ArrayList<>();
        while (userCursor.moveToNext()) {
            User mUser = new User();
            mUser.fullName = userCursor.getString(userCursor.getColumnIndexOrThrow("NAME"));
            mUser.title = userCursor.getString(userCursor.getColumnIndexOrThrow("TITLE"));
            mUser.description = userCursor.getString(userCursor.getColumnIndexOrThrow("DESCRIPTION"));
            mUser.profilePic = userCursor.getString(userCursor.getColumnIndexOrThrow("PIC"));
            mUser.id = userCursor.getInt(userCursor.getColumnIndexOrThrow("ID"));
            users.add(mUser);
        }
        Log.d("retrieved result", String.valueOf(users));
        userCursor.close();

        for (User obj : users) {
            int userID = obj.id;
            Cursor photoCursor = db.rawQuery("SELECT photo.IMG FROM 'photo'   WHERE user_ID= '" + userID + "'", null);
            while (photoCursor.moveToNext()) {
                String img = photoCursor.getString(photoCursor.getColumnIndexOrThrow("IMG"));
                obj.images.add(img);
            }
            usersWithImg.add(obj);
        }

        return usersWithImg;
    }


}
