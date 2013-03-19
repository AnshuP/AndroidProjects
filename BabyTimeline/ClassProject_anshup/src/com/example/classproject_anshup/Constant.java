package com.example.classproject_anshup;


import android.provider.BaseColumns;

public class Constant implements BaseColumns {

    public static final class AppData {
        
        public static final String TABLE_NAME = "babyData";

        public static final String ID = BaseColumns._ID;
        public static final String DATE = "date";
        public static final String TIME = "time";
        public static final String NOTE = "note";
        public static final String PHOTOLOCATION = "photoLoc";
        public static final String AUDIOLOCATION = "audioLoc";

        //public static final String[] PROJECTION = new String[] {
        ///* 0 */ Books.Book.ID,
        //* 1 */ Books.Book.NAME,
        //* 2 */ Books.Book.ISBN };

    }
}
