package com.example.mhasan.tabwithviewpager;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by mhasan on 4/25/2017.
 */

public class DataProvider extends ContentProvider {
    private static final String AUTHORITY = "com.example.mhasan.tabwithviewpager.dataprovider";
    private static final String BASE_PATH = "user";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    // Constant to identify the requested operation
    private static final int USER = 1;
    private static final int USER_ID = 2;
    private static final UriMatcher uriMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, USER);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", USER_ID);

    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        DatabaseHelper Db = new DatabaseHelper(getContext());
        database = Db.getWritableDatabase();
        return true;
    }

    /*
    * get data back from database (retrieve all or spcefic record )
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return database.query(DatabaseHelper.TABLE_NAME, DatabaseHelper.ALL_COLUMNS, selection, null, null, null, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long id = database.insert(DatabaseHelper.TABLE_NAME, null, values);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return database.delete(DatabaseHelper.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return database.update(DatabaseHelper.TABLE_NAME, values, selection, selectionArgs);
    }
}
