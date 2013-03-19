package com.example.classproject_anshup;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AppDataSQLiteOpenHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "babyApp";
    private final static int DB_VERSION = 1;

    private final static String TABLE_NAME = Constant.AppData.TABLE_NAME;
    private final static String TABLE_ROW_ID = Constant.AppData.ID;
    private final static String TABLE_ROW_DATE = Constant.AppData.DATE;
    private final static String TABLE_ROW_TIME = Constant.AppData.TIME;
    private final static String TABLE_ROW_NOTE = Constant.AppData.NOTE;
    private final static String TABLE_ROW_PHOTO = Constant.AppData.PHOTOLOCATION;
    private final static String TABLE_ROW_AUDIO = Constant.AppData.AUDIOLOCATION;

    public AppDataSQLiteOpenHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableQueryString = 
                        "CREATE TABLE " + 
                        TABLE_NAME + " (" + 
                        TABLE_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + 
                        TABLE_ROW_DATE + " TEXT, " + 
                        TABLE_ROW_TIME + " TEXT, " + 
                        TABLE_ROW_NOTE + " TEXT, " + 
                        TABLE_ROW_PHOTO + " TEXT, " + 
                        TABLE_ROW_AUDIO + " TEXT" + ");";
        
        db.execSQL(createTableQueryString); 
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    
    public void InsertRecord(ContentValues content) {
    	//mExampleHelper = new ExampleSQLiteOpenHelper(this);
        SQLiteDatabase wdb = this.getWritableDatabase();//mExampleHelper.getWritableDatabase();
        long d = 0;
        /*ContentValues values = new ContentValues();

        values.put(TABLE_ROW_DATE, content.getAsString(""));
        values.put(TABLE_ROW_TIME, content.getAsString(""));
        values.put(SMS_BODY, msg2);*/

        try {
            d = wdb.insert(TABLE_NAME, null, content);
        } catch (Exception e) {

        }
        wdb.close(); // Closing database connection
        //return d;

    }
    
     
    // Getting single contact
    public BabyBook getRecord(int id) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	 
        Cursor cursor = db.query(TABLE_NAME, new String[] { TABLE_ROW_DATE, TABLE_ROW_TIME, TABLE_ROW_NOTE,
                TABLE_ROW_PHOTO, TABLE_ROW_AUDIO }, TABLE_ROW_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        BabyBook records = new BabyBook(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
        // return contact
        db.close();
        return records;
    }
     
    // Getting All Contacts
    public List<BabyBook> getAllRecords() {
    	List<BabyBook> recordList = new ArrayList<BabyBook>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	BabyBook record = new BabyBook();
            	record.setId(Integer.parseInt(cursor.getString(0)));
            	record.setDate(cursor.getString(1));
            	record.setTime(cursor.getString(2));
            	record.setNote(cursor.getString(3));
            	record.setPhotoLocation(cursor.getString(4));
            	record.setAudioLocation(cursor.getString(5));
                // Adding contact to list
            	recordList.add(record);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        db.close();
        return recordList;
    	
    }
    
 // Getting All Contacts
    public Cursor getAllRecordsCursor() {
    	List<BabyBook> recordList = new ArrayList<BabyBook>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor != null) {
        	cursor.moveToFirst();  
        }
 
        // return contact list
        db.close();
        return cursor;
    	
    }
    
    //Get record from date selected
    public Cursor getRecordFromDate(String date) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	 
    	String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " +TABLE_ROW_DATE+ " =?";
    	Log.v("getRecordFromDate",selectQuery);
    	Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(date) });
    	
       /* Cursor cursor = db.query(TABLE_NAME, new String[] { TABLE_ROW_DATE, TABLE_ROW_TIME, TABLE_ROW_NOTE,
                TABLE_ROW_PHOTO, TABLE_ROW_AUDIO }, TABLE_ROW_DATE + "=?",
                new String[] { date }, null, null, null, null);*/
        if (cursor != null)
            cursor.moveToFirst();
 
        // return contact
        db.close();
        return cursor;
    }
     
 // Updating single contact
   /* public int updateContact(BabyBook record) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, record.getName());
        values.put(KEY_PH_NO, record.getPhoneNumber());
 
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }*/
 
    // Deleting single contact
   /* public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }*/
 
    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        db.close();
        return cursor.getCount();
    }
    
}
