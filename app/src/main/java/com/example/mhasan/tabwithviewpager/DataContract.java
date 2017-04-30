package com.example.mhasan.tabwithviewpager;

import android.provider.BaseColumns;

/**
 * Created by mhasan on 4/25/2017.
 */

public final class DataContract {
    private DataContract(){}

    public static  class UserEntry implements BaseColumns{
        public static final String TABLE_NAME= "user_table";
        public static final String COL_ID= "id";
        public static final String COL_NAME= "full_name";
        public static final String COL_TITLE= "title";
        public static final String COL_DES= "description";
        public static final String COL_PIC= "profile_pic";
        public static final String COL_IMG= "img";
        public static final String COL_IMG_1= "img1";
        public static final String COL_IMG_2= "img2";

    }
}
