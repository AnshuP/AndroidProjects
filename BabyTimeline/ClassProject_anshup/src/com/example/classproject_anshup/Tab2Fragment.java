package com.example.classproject_anshup;




import android.text.format.DateFormat;
import java.util.Calendar;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;


public class Tab2Fragment extends Fragment
 {
	/*private static int RESULT_LOAD_IMAGE = 1;
	private static int IMAGE_CAPTURE_REQUEST = 2;
    private static String TAG = "Tab2Fragment";
	Button mDatePicker;
	Button mTimePicker;
	Button mGalleryPicker;
	Button mCameraPicker;
	ImageView image;
	private Uri mImageURI;*/
	private Button mTakePicture;
	private Button mRecordAudio;
	
	//@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {  	
	    	View v = (LinearLayout) inflater.inflate(R.layout.tab2_menu, container, false);
	    	Log.v("onCreate","1");
	    	if (v != null) {
	    		mTakePicture = (Button) v.findViewById(R.id.takePicture);
	    		mTakePicture.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//this.startActivity(new Intent(this, PictureActivity.class));
						Intent i = new Intent(getActivity().getApplicationContext(), PictureActivity.class);
						startActivity(i);
					}
				});
	    		
	    		mRecordAudio = (Button) v.findViewById(R.id.recordAudio);
	    		mRecordAudio.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(getActivity().getApplicationContext(), AudioActivity.class);
						startActivity(i);
					}
				});
	    	/*mDatePicker = (Button) v.findViewById(R.id.pickDate);
	        		
	    	Log.v("mDatePicker","2");
	    	mDatePicker.setOnClickListener(new View.OnClickListener() { 
	    	       public void onClick(View v) { 
	    	    	   Log.v("setOnClickListener","1");
	    	    	   showDatePickerDialog(v); 
	    	    	   Log.v("setOnClickListener","2");
	    	       } 
	    	}); 
	    	
	    	mTimePicker = (Button) v.findViewById(R.id.pickTime);
	    	mTimePicker.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showTimePickerDialog(v);
				}
			});
	    	
	    	image = (ImageView) v.findViewById(R.id.picture);
	    	mGalleryPicker = (Button) v.findViewById(R.id.pickGallery);
	    	mGalleryPicker.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(
	                        Intent.ACTION_PICK,
	                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	                 
	                startActivityForResult(i, RESULT_LOAD_IMAGE);
				}
			});
	    	
	    	mCameraPicker = (Button) v.findViewById(R.id.pickCamera);
	    	mCameraPicker.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startCamera();
				}
			});*/
	    	
	    	}        
	    	return v;
	    }
	    
	   /* public void startCamera() {
	    	 String imageFileName = UUID.randomUUID().toString() + ".jpg";

	         ContentValues contentValues = new ContentValues();
	         contentValues.put(MediaStore.Images.Media.TITLE, imageFileName);

	         mImageURI = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

	         Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	         i.putExtra(MediaStore.EXTRA_OUTPUT, mImageURI);

	         try {
	             startActivityForResult(i, IMAGE_CAPTURE_REQUEST);
	         }
	         catch (ActivityNotFoundException e) {
	             Log.e(TAG, "Could not start a Camera Intent");
	         }
	    }
	    
	    public void showDatePickerDialog(View v) {
	        DialogFragment newFragment = new DatePickerFragment();
	        newFragment.show(getFragmentManager(), "datePicker");
	    }
	    
	    public void showTimePickerDialog(View v) {
	        DialogFragment newFragment = new TimePickerFragment();
	        newFragment.show(getFragmentManager(), "timePicker");
	    }
		
	    public void updateDisplay(int mYear, int mMonth, int mDay)
	    {Log.v("update","1");
	    	if(this.getView() != null)
	    	{Log.v("update","2");
	    	int year = preferences.getInt("year", 0);
	    	int month = preferences.getInt("month", 0);
	    	int day = preferences.getInt("day", 0);
	    		mDatePicker.setText(mMonth + "/" + mDay + "/" + mYear); Log.v("update","3");
	    	}
	    }
	    
	    public void updateTimeDisplay(int hour, int minute)
	    {
	    	if(this.getView() != null)
	    	{
	    		mTimePicker.setText(hour + ":" + minute);
	    	}
	    }
	    
	    @Override
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	         
	        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
	            Uri selectedImage = data.getData();
	            String[] filePathColumn = { MediaStore.Images.Media.DATA };
	 
	            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
	                    filePathColumn, null, null, null);
	            cursor.moveToFirst();
	 
	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            String picturePath = cursor.getString(columnIndex);
	            cursor.close();
	             
	            if(this.getView() != null)
	            image.setImageBitmap(BitmapFactory.decodeFile(picturePath));
	         
	        } 
	        else if (requestCode == IMAGE_CAPTURE_REQUEST) {
	            if (resultCode == Activity.RESULT_OK) {
	                Log.d(TAG, "onActivityResult() Camera Returned OK image: " + mImageURI);
	                if(this.getView() != null)
	                	image.setImageURI(mImageURI);
	            }
	            else if (resultCode == Activity.RESULT_CANCELED) {
	                Log.d(TAG, "onActivityResult() Camera Returned CANCELED");
	            }
	            else {
	                Log.d(TAG, "onActivityResult() Camera Returned " + resultCode);
	            }
	        }
	    }
	    
	   	    
	    @SuppressLint("ValidFragment") 
	    public class DatePickerFragment extends DialogFragment
	    implements DatePickerDialog.OnDateSetListener {

	    	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    		// Use the current date as the default date in the picker
	    		final Calendar c = Calendar.getInstance();
	    		int year = c.get(Calendar.YEAR);
	    		int month = c.get(Calendar.MONTH);
	    		int day = c.get(Calendar.DAY_OF_MONTH);

	    		// Create a new instance of DatePickerDialog and return it
	    		return new DatePickerDialog(getActivity(), this, year, month, day);
	    	}

	    	public void onDateSet(DatePicker view, int year, int month, int day) {
	    		// Do something with the date chosen by the user
	    		//new Tab2Fragment().updateDisplay(year, month, day);
	    		//findViewById(R.id.pickDate).setText(month + "/" + day + "/" + year);
	    		//findViewByTag()
	    		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
	    	    preferences.putInt("year", year);
	    	    preferences.putInt("month", month);
	    	    preferences.putInt("day", day);
	    	    preferences.commit();

	    	    return;
	    		updateDisplay(year, month, day);
	    	}

	    }
	    
	    
	    @SuppressLint("ValidFragment") 
	    public class TimePickerFragment extends DialogFragment
	    implements TimePickerDialog.OnTimeSetListener {

	    	@Override
	    	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    		// Use the current time as the default values for the picker
	    		final Calendar c = Calendar.getInstance();
	    		int hour = c.get(Calendar.HOUR_OF_DAY);
	    		int minute = c.get(Calendar.MINUTE);

	    		// Create a new instance of TimePickerDialog and return it
	    		return new TimePickerDialog(getActivity(), this, hour, minute,
	    				DateFormat.is24HourFormat(getActivity()));
	    		//DateFormat
	    	}

	    	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	    		// Do something with the time chosen by the user
	    		updateTimeDisplay(hourOfDay,minute);
	    	}
	    }*/
	    
	    
}

 