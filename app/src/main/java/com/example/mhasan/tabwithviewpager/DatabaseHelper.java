package com.example.mhasan.tabwithviewpager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mhasan on 4/25/2017.
 * DatabaseHelper
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private User mUser;
    public static final int DATABASE_VERSION = 11;
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
      //  db.execSQL(USER_TABLE_CREATE);
     //   db.execSQL(PHOTO_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS '" + TABLE_DEL + "'");
      //  db.execSQL("DELETE FROM'"+TABLE_DEL+"' WHERE ID ='NULL'");
        int id =0;
        db.execSQL("DELETE FROM "+TABLE_DEL+" where ID='"+id+"'");
        onCreate(db);

    }

    public boolean insertData(User user) {
        this.mUser = user;
        long result = -1;
        long result2 = -1;
        SQLiteDatabase Db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase Db2 = this.getWritableDatabase();
        ContentValues photoValues = new ContentValues();
        contentValues.put("ID", mUser.id);
        contentValues.put("NAME", mUser.fullName);
        contentValues.put("TITLE", mUser.title);
        contentValues.put("DESCRIPTION", mUser.description);
        contentValues.put("PIC", mUser.profilePic);
//          result = Db.insert(TABLE_NAME, null, contentValues);

        int size = mUser.images.size();
        Log.d("size of images", Integer.toString(size));
        for (int j = 0; j < size; j++) {
            String img = mUser.images.get(j);
            photoValues.put("img", img);
            photoValues.put("user_id", mUser.id);
         //   result2 = Db2.insert("photo", null, photoValues);
            Log.d("inside", String.valueOf(photoValues));
        }

        Log.d("photoValues", String.valueOf(photoValues.getAsString("img")));
        return !(result == -1 | result2 == -1);

    }

    /**
     * get data from UserTest DB
     */

    public ArrayList<User> getData() {
        User mUser = new User();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                "ID",
                "NAME",
                "TITLE",
                "PIC",
                "DESCRIPTION"
        };

        Cursor cursor = db.query("user", projection, null, null, null, null, null
        );
        ArrayList <User>users = new ArrayList<>();
        while (cursor.moveToNext()) {
            mUser.fullName = cursor.getString(cursor.getColumnIndexOrThrow("NAME"));
            mUser.title = cursor.getString(cursor.getColumnIndexOrThrow("TITLE"));
            mUser.description = cursor.getString(cursor.getColumnIndexOrThrow("DESCRIPTION"));
            mUser.profilePic = cursor.getString(cursor.getColumnIndexOrThrow("PIC"));
            mUser.id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
            users.add(mUser);
        }
        Log.d("retrieved result", String.valueOf(users));
        Log.d("size result", String.valueOf(users.size()));

        cursor.close();
        return users;
    }
}
