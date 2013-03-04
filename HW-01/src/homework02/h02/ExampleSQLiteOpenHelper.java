package homework02.h02;



import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExampleSQLiteOpenHelper extends SQLiteOpenHelper{

	private final static String DB_NAME = "example";
    private final static int DB_VERSION = 1;

    public final static String TABLE_NAME = "content";
    public final static String TABLE_ROW_ID = "_id";
    public final static String TABLE_ROW_ONE = "row_one";
    //public final static String TABLE_ROW_TWO = "row_two";
    private ExampleSQLiteOpenHelper mExampleHelper;
    private SQLiteDatabase mReadableDB;
    
    public ExampleSQLiteOpenHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

     String createTableQueryString = "create table "
    		      + TABLE_NAME + "(" + TABLE_ROW_ID
    		      + " integer primary key autoincrement, " + TABLE_ROW_ONE
    		      + " text not null);";
        /*String createTableQueryString = "CREATE TABLE " + TABLE_NAME + " (" + TABLE_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," 
        + TABLE_ROW_ONE + " TEXT," + ");";*/
        //+ TABLE_ROW_TWO + " TEXT" + ");";
        db.execSQL(createTableQueryString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    
   /* public long InsertRecord(String msg, String msg1,String msg2) {

    	//mExampleHelper = new ExampleSQLiteOpenHelper(this);
        SQLiteDatabase wdb = this.getWritableDatabase();//mExampleHelper.getWritableDatabase();
        long d = 0;
        ContentValues values = new ContentValues();

        values.put(SMS_NO, msg);
        values.put(SMS_BODY, msg1);
        values.put(SMS_BODY, msg2);

        try {
            d = myDataBase.insert(SMS_TBL, null, values);
        } catch (Exception e) {

        }
        return d;

    }*/
}
