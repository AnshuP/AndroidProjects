package com.example.classproject_anshup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

public class CalendarActivity extends Activity {

	public Calendar month;
	public CalendarAdapter adapter;
	public Handler handler;
	public ArrayList<String> items  = new ArrayList<String>(); // container to store event dates
	
	public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);
        
        month = Calendar.getInstance();
	    onNewIntent(getIntent());
	    
	    //items = new ArrayList<String>();
	    adapter = new CalendarAdapter(this, month);
	    
	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(adapter);
	    
	    handler = new Handler();
	    handler.post(calendarUpdater);
	    
	    TextView title  = (TextView) findViewById(R.id.title);
	    title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
	    
	    TextView previous  = (TextView) findViewById(R.id.previous);
	    previous.setOnClickListener(new OnClickListener() {
	    	//@Override
			public void onClick(View v) {
				if(month.get(Calendar.MONTH)== month.getActualMinimum(Calendar.MONTH)) {				
					month.set((month.get(Calendar.YEAR)-1),month.getActualMaximum(Calendar.MONTH),1);
				} else {
					month.set(Calendar.MONTH,month.get(Calendar.MONTH)-1);
				}
				refreshCalendar();
			}
		});
	    
	    TextView next  = (TextView) findViewById(R.id.next);
	    next.setOnClickListener(new OnClickListener() {
			
			//@Override
			public void onClick(View v) {
				if(month.get(Calendar.MONTH)== month.getActualMaximum(Calendar.MONTH)) {				
					month.set((month.get(Calendar.YEAR)+1),month.getActualMinimum(Calendar.MONTH),1);
				} else {
					month.set(Calendar.MONTH,month.get(Calendar.MONTH)+1);
				}
				refreshCalendar();
				
			}
		});
	    
		gridview.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		    	TextView date = (TextView)v.findViewById(R.id.date);
		        if(date instanceof TextView && !date.getText().equals("")) {
		        	
		        	Intent intent = new Intent(getApplicationContext(), EventsListView.class);
					String day = date.getText().toString();
		        	if(day.length()==1) {
		        		day = "0"+day;
		        	}
		        	// return chosen date as string format 
		        	intent.putExtra("date", android.text.format.DateFormat.format("yyyy-MM", month)+"-"+day);
		        	startActivity(intent);
		        	//setResult(RESULT_OK, intent);
		        	//finish();
		        }
		        
		    }
		});
	    
        
       /* CalendarView calendarView=(CalendarView) findViewById(R.id.calendarView1);
        calendarView.setOnDateChangeListener(new OnDateChangeListener() {

            //@Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                    int dayOfMonth) {
                 Toast.makeText(getApplicationContext(), ""+dayOfMonth, 0).show();// TODO Auto-generated method stub

            }
        });*/
	}
	
	public void refreshCalendar()
	{
		TextView title  = (TextView) findViewById(R.id.title);
		
		adapter.refreshDays();
		adapter.notifyDataSetChanged();				
		handler.post(calendarUpdater); // generate some random calendar items				
		
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
	}
	
	public void onNewIntent(Intent intent) {
		String date = intent.getStringExtra("date");
		String[] dateArr = date.split("-"); // date format is yyyy-mm-dd
		month.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[2]));
	}
	
	public Runnable calendarUpdater = new Runnable() {
		
		//@Override
		public void run() {
			items.clear();
			// format random values. You can implement a dedicated class to provide real values
			/*for(int i=0;i<31;i++) {
				Random r = new Random();
				
				if(r.nextInt(10)>6)
				{
					items.add(Integer.toString(i));
				}
			}*/
			//setCalendarEvent(-1);
			AppDataSQLiteOpenHelper db = new AppDataSQLiteOpenHelper(getApplicationContext());
			Log.d("Reading: ", "Reading all contacts..");
	        List<BabyBook> contacts = db.getAllRecords();       
	 
	        for (BabyBook cn : contacts) {
	            String log = "Id: "+cn.getId()+" ,Date: " + cn.getDate() + " ,Time: " + cn.getTime()+ ", Photo: "+cn.getPhotoLocation();
	                // Writing Contacts to log
	            //Log.d("DataFromDB: ", log);
	            String[] dateArr = cn.getDate().split("/"); // date format is mm/dd/yyyy
	            items.add(dateArr[1]);
	            //items.add(cn.getDate());
	        }
			
			adapter.setItems(items);
			adapter.notifyDataSetChanged();
		}
	};
	
	
	
}
