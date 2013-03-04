package homework02.h02;

import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class CommentsDataSource {

	 // Database fields
	  private SQLiteDatabase database;
	  private ExampleSQLiteOpenHelper dbHelper;
	  private String[] allColumns = { ExampleSQLiteOpenHelper.TABLE_ROW_ID,
			  ExampleSQLiteOpenHelper.TABLE_ROW_ONE };

	  public CommentsDataSource(Context context) {
	    dbHelper = new ExampleSQLiteOpenHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public ListItem createComment(String comment) {
	    ContentValues values = new ContentValues();
	    values.put(ExampleSQLiteOpenHelper.TABLE_ROW_ONE, comment);
	    long insertId = database.insert(ExampleSQLiteOpenHelper.TABLE_NAME, null,
	        values);
	    Cursor cursor = database.query(ExampleSQLiteOpenHelper.TABLE_NAME,
	        allColumns, ExampleSQLiteOpenHelper.TABLE_ROW_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    ListItem newComment = cursorToComment(cursor);
	    cursor.close();
	    return newComment;
	  }

	  public void deleteComment(ListItem comment) {
	    long id = comment.getId();
	    System.out.println("Comment deleted with id: " + id);
	    database.delete(ExampleSQLiteOpenHelper.TABLE_NAME, ExampleSQLiteOpenHelper.TABLE_ROW_ID
	        + " = " + id, null);
	  }

	  public List<ListItem> getAllComments() {
	    List<ListItem> comments = new ArrayList<ListItem>();

	    Cursor cursor = database.query(ExampleSQLiteOpenHelper.TABLE_NAME,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	ListItem comment = cursorToComment(cursor);
	      comments.add(comment);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return comments;
	  }

	  private ListItem cursorToComment(Cursor cursor) {
		  ListItem comment = new ListItem();
	    comment.setId(cursor.getLong(0));
	    comment.setComment(cursor.getString(1));
	    return comment;
	  }

	  class ListItem {
		  private long id;
		  private String comment;

		  public long getId() {
		    return id;
		  }

		  public void setId(long id) {
		    this.id = id;
		  }

		  public String getComment() {
		    return comment;
		  }

		  public void setComment(String comment) {
		    this.comment = comment;
		  }

		  // Will be used by the ArrayAdapter in the ListView
		  @Override
		  public String toString() {
		    return comment;
		  }
		} 
    
    

}
