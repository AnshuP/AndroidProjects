package com.example.classproject_anshup;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;


public class Tab1Fragment extends Fragment implements DialogInterface.OnClickListener{
	
	public final static int CREATE_DIALOG  = -1;
    public final static int THEME_HOLO_LIGHT  = 0;
    public final static int THEME_BLACK  = 1;
    int position;
	private ImageButton mCalendar;
	private final String TAG = "Tab1Fragment";
	private ImageButton mAllEvents;
	private ImageButton mTheme;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = (LinearLayout) inflater.inflate(R.layout.tab1, container, false);
        if (v != null) {
        	mCalendar = (ImageButton) v.findViewById(R.id.calendarButton);
        	mCalendar.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//this.startActivity(new Intent(this, PictureActivity.class));
					Calendar c = Calendar.getInstance();
					int year = c.get(Calendar.YEAR);
					int month = c.get(Calendar.MONTH);
					int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
					Intent i = new Intent(getActivity().getApplicationContext(), CalendarActivity.class);
					i.putExtra("date", year + "-" + month + "-" + dayOfMonth);//dp.getYear()+"-"+dp.getMonth()+"-"+dp.getDayOfMonth());
					Log.v(TAG,"Date passed: " + year + "-" + month + "-" + dayOfMonth);
					startActivity(i);
				}
			});
        	
        	mAllEvents = (ImageButton) v.findViewById(R.id.allEventsButton);
        	mAllEvents.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity().getApplicationContext(), AllEventsListView.class);
					intent.putExtra("allEvents", "All Events");
		        	startActivity(intent);
		        	//setResult(Activity.RESULT_OK, intent);
		        	//finish();
				}
			});
        	
        	mTheme = (ImageButton) v.findViewById(R.id.themeButton);
        	mTheme.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					position = getActivity().getIntent().getIntExtra("position", -1);

			        switch(position)
			        {
			        case CREATE_DIALOG:
			            createDialog();
			            break;
			        case THEME_HOLO_LIGHT:
			            getActivity().setTheme(R.drawable.background_gradient);
			            break;
			        case THEME_BLACK:
			        	getActivity().setTheme(android.R.style.Theme_Black);
			            break;
			        default:
			        }
				}
			});
        }
        return v;
    }

	private void createDialog()
    {
        /** Options for user to select*/
        String choose[] = {"Theme_Pink","Theme_Blue"};

        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());

        /** Setting a title for the window */
        b.setTitle("Choose your Application Theme");

        /** Setting items to the alert dialog */
        b.setSingleChoiceItems(choose, 0, null);

        /** Setting a positive button and its listener */
        b.setPositiveButton("OK",(OnClickListener) this);

        /** Setting a positive button and its listener */
        b.setNegativeButton("Cancel", null);

        /** Creating the alert dialog window using the builder class */
        AlertDialog d = b.create();

        /** show dialog*/
        d.show();
    }
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}
}
