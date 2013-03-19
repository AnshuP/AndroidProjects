package com.example.classproject_anshup;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class AllEventsListView extends Activity 
{
	private final String TAG = "AllEventsListView";
	private ListView listContent;
	AppDataSQLiteOpenHelper mDbHelper;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_list);
	
		mDbHelper = new AppDataSQLiteOpenHelper(this);
		listContent = (ListView)findViewById(R.id.list);
		//Log.v(TAG,getIntent().getStringExtra("date"));
		
		// if(getIntent().getStringExtra("date") != null)
		//{
			//fillSelectedData(getIntent());
		//}
		/*if(getIntent().getStringExtra("allEvents").equals("All Events"))
		{*/
			fillAllData();
		//}
		
		//fillSelectedData(getIntent());
		Log.v(TAG,"getStringExtra(allEvents):"+getIntent().getStringExtra("allEvents"));
		//Log.v(TAG,"getStringExtra(date):"+getIntent().getStringExtra("date"));
	}
	
	private void fillAllData() {

		
        Cursor fetchInfo = (Cursor)mDbHelper.getAllRecordsCursor();
        startManagingCursor(fetchInfo);


        // Create an array to specify the fields we want to display in the list (TITLE,DATE,NUMBER)
        String[] from = new String[]{Constant.AppData.NOTE, Constant.AppData.DATE, 
        		Constant.AppData.TIME };

        // an array of the views that we want to bind those fields to (in this case text1,text2,text3)
        int[] to = new int[]{R.id.text1, R.id.text2, R.id.text3};

        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter notes = 
            new SimpleCursorAdapter(this, R.layout.row, fetchInfo, from, to);
        listContent.setAdapter(notes);
        
        listContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), "msg msg", Toast.LENGTH_SHORT).show();
				//create the send intent  
				Intent shareIntent =   
				 new Intent(android.content.Intent.ACTION_SEND);  
				  
				//set the type  
				shareIntent.setType("text/plain");  
				  
				//add a subject  
				shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,   
				 "Insert Subject Here");  
				  
				//build the body of the message to be shared  
				String shareMessage = "Insert message body here.";  
				  
				//add the message  
				shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,   
				 shareMessage);  
				  
				//start the chooser for sharing  
				startActivity(Intent.createChooser(shareIntent,   
				 "Share")); 
			}
        });
    }
	
	
}
