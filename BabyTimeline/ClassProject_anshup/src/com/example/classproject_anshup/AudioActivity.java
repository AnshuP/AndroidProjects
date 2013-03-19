package com.example.classproject_anshup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import com.example.classproject_anshup.PictureActivity.DatePickerFragment;
import com.example.classproject_anshup.PictureActivity.TimePickerFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class AudioActivity extends Activity implements OnClickListener {

    private static final String TAG = "AudioActivity";

    //private Button mIntentButton;
    private Button mPlayButton;
    private Button mStartButton;
    private Button mStopButton;
    private MediaRecorder mMediaRecorder;
    private static String mFileName;
    private Chronometer watch;
    private Button mDatePicker;
	private Button mTimePicker;
	private Button mSaveAudio;
	private Button mCancelAudio;
	private EditText mNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.setContentView(R.layout.audio);
        
        watch = (Chronometer) findViewById(R.id.stopWatch);

       /* mIntentButton = (Button) this.findViewById(R.id.recordIntent);
        mIntentButton.setOnClickListener(this);*/
        
        mPlayButton = (Button) this.findViewById(R.id.playAudio);
        mPlayButton.setOnClickListener(this);

        mStartButton = (Button) this.findViewById(R.id.startAudio);
        mStartButton.setOnClickListener(this);

        mStopButton = (Button) this.findViewById(R.id.stopAudio);
        mStopButton.setOnClickListener(this);
        
        mDatePicker = (Button) this.findViewById(R.id.pickDateAudio);
        mDatePicker.setOnClickListener(this);
        
        mTimePicker = (Button) findViewById(R.id.pickTimeAudio);
        mTimePicker.setOnClickListener(this);
        
        mSaveAudio = (Button)findViewById(R.id.saveAudio);
        mSaveAudio.setOnClickListener(this);
        
        mCancelAudio = (Button)findViewById(R.id.cancelAudio);
        mCancelAudio.setOnClickListener(this);
        
        mNote = (EditText)findViewById(R.id.notesAudio);
        
        mMediaRecorder = new MediaRecorder();
    }

    //@Override
    public void onClick(View v) {

        switch (v.getId()) {
            
           /* case R.id.recordIntent:
                Intent i = new Intent("android.provider.MediaStore.RECORD_SOUND");
                startActivityForResult(i, 0);
                break;*/

            case R.id.playAudio:
            	watch.setBase(SystemClock.elapsedRealtime());
            	watch.start();
                playRecording();
                watch.stop();
                break;

            case R.id.startAudio:
            	
            	watch.setBase(SystemClock.elapsedRealtime());
            	watch.start();
                startRecording();
                break;

            case R.id.stopAudio:
            	watch.stop();
                stopRecording();
                break;
                
            case R.id.pickDateAudio:
            	showDatePickerDialog(v);
                break;
                
            case R.id.pickTimeAudio:
            	showTimePickerDialog(v);
                break;
                
            case R.id.saveAudio:
            	saveAudioData(v);
            	break;
            	
            case R.id.cancelAudio:
            	
            	break;
        }

    }

    
    private void saveAudioData(View v)
    {
    	Intent intent = new Intent();
    	AppDataSQLiteOpenHelper db = new AppDataSQLiteOpenHelper(getApplicationContext());
				
		ContentValues values = new ContentValues();
        values.put(Constant.AppData.DATE, mDatePicker.getText().toString());
        values.put(Constant.AppData.TIME, mTimePicker.getText().toString());
        values.put(Constant.AppData.NOTE, mNote.getText().toString());
        if(mFileName != null)
        	values.put(Constant.AppData.PHOTOLOCATION, mFileName);
       
        // Inserting Contacts
        Log.v("Insert Audio: ", "Inserting ..");
        db.InsertRecord(values);
        setResult(RESULT_OK, intent);
    	finish();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        if (mMediaRecorder != null)
            mMediaRecorder.release();
    }

    private void playRecording() {

        File playFile = new File(mFileName);
        if (playFile.exists()) {

            try {
                MediaPlayer mp = new MediaPlayer();
                FileInputStream fis = new FileInputStream(playFile);
                mp.setDataSource(fis.getFD());
                mp.prepare();
                mp.start();

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {
            Log.e(TAG, "playRecording() File does not exist: " + mFileName);
        }
    }

    private void startRecording() {

        mMediaRecorder.reset();

        String fileName = UUID.randomUUID().toString().substring(0, 6).concat(".3gp");
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName;

        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mMediaRecorder.setOutputFile(mFileName);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mMediaRecorder.start();
    }

    private void stopRecording() {

        if (mMediaRecorder == null)
            return;

        mMediaRecorder.stop();
        mMediaRecorder = null;
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
    {Log.v("updateAudioDate","1");
    	
    	mDatePicker.setText(mMonth+1 + "/" + mDay + "/" + mYear); Log.v("update","3");
    	
    }
    
    public void updateTimeDisplay(int hour, int minute)
    {
    	
    		mTimePicker.setText(hour + ":" + minute);
    	
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
    }
    

}
