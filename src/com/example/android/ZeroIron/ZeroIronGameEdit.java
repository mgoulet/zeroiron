package com.example.android.ZeroIron;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.example.android.ZeroIron.R.id;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class ZeroIronGameEdit extends Activity implements OnClickListener, OnDateSetListener, OnTimeSetListener {

	int mCurrentWeatherSelection;
	
	ZeroIronCourseStructure mCourseStructure;
	int mCourseId;
	
	public static final int DATE_DIALOG_ID = 0;
	public static final int TIME_DIALOG_ID = 1;
	
	private GregorianCalendar mActiveCalendar;
	
	//button handles for callbacks
	ImageView mOkButton;
	ImageView mCancelButton;
	ImageButton mDateChangeButton;
	ImageButton mTimeChangeButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zeroiron_editgame);

		//set members to zero or null
		mCourseStructure = null;
		mCourseId = 0;
		
		mActiveCalendar = new GregorianCalendar();
				
		//update fields accordingly
		updateFieldsFromIntent();
		
		//set button listeners
		setButtonListeners();		
		
	}
	
	protected boolean updateFieldsFromIntent() {

		//retrieve intent
		Bundle bundle = getIntent().getExtras();

		if ( bundle != null && bundle.containsKey(ZeroIronCourseList.COURSE_STRUCTURE) &&
							   bundle.containsKey(ZeroIronDbAdapter.KEY_COURSE_ID) ) {
					
			mCourseStructure = (ZeroIronCourseStructure) bundle.getSerializable(ZeroIronCourseList.COURSE_STRUCTURE);
			mCourseId = bundle.getInt(ZeroIronDbAdapter.KEY_COURSE_ID);
					
			//populate widgets
			populateCourseWidgets(mCourseStructure.getCourseName(),
							mCourseStructure.getCourseLocation(),
							mCourseStructure.getCoursePar(),
							mCourseStructure.getCourseSize());
			
		} else if (bundle != null && bundle.containsKey(ZeroIronGamesList.GAME_STRUCTURE)) {
			
			//we are editing an existing game.
			//...
			
		}
		
		return true;
	}
	
	protected boolean setButtonListeners() {
		
		//set button references
		mOkButton = (ImageView) findViewById(R.id.imageOk);
		mCancelButton = (ImageView) findViewById(R.id.imageCancel);
		mDateChangeButton = (ImageButton) findViewById(R.id.editGameDateButton);
		mTimeChangeButton = (ImageButton) findViewById(R.id.editGameTimeButton);
		
		//link to this object as onClickListener
		mOkButton.setOnClickListener(this);
		mCancelButton.setOnClickListener(this);
		mDateChangeButton.setOnClickListener(this);
		mTimeChangeButton.setOnClickListener(this);
		
		return true;
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	

	private boolean populateGameWidgets() {
		
		
		return false;
	}
	
	private boolean populateCourseWidgets(String courseName, String courseLocation, int coursePar, int courseSize) {
		
		//Retrieve course name
		TextView textNameView = (TextView) findViewById(R.id.textCourseNameValue);
		textNameView.setText(courseName);
		
		//other fields not displayed for the moment
		//...
		
		return true;
		
	}

	@Override
	public void onClick(View view) {
		
		if (view == mOkButton) {
			
			//1 - retrieve content from widgets
			
			//2 - store in structure
			ZeroIronGameStructure gameStructure = new ZeroIronGameStructure();
			gameStructure.setCourseId(mCourseId);
			TextView nameText = (TextView) findViewById(R.id.editGameName);
			gameStructure.setName(nameText.getText().toString());
			gameStructure.setStatus(0); //Initial status is 0.
			gameStructure.setDate(mActiveCalendar);
			TextView notesText = (TextView) findViewById(R.id.editGameNotes);
			gameStructure.setNotes(notesText.getText().toString());
						
			//3 - return to invoking activity
			Intent i = new Intent(ZeroIronGameEdit.this, ZeroIronGamesList.class);
			i.putExtra(ZeroIronGamesList.GAME_STRUCTURE, gameStructure);
			
			setResult(RESULT_OK, i);
			finish();
			
		} else if (view == mCancelButton) {
			setResult(RESULT_CANCELED);
			finish();
		} else if (view == mDateChangeButton) {
			showDialog(DATE_DIALOG_ID);
			
		} else if (view == mTimeChangeButton) {
			showDialog(TIME_DIALOG_ID);
		}
				
	}
	
	@Override
	public Dialog onCreateDialog(int id) {
		
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, this,
					GregorianCalendar.getInstance().get(Calendar.YEAR),
					GregorianCalendar.getInstance().get(Calendar.MONTH),
					GregorianCalendar.getInstance().get(Calendar.DAY_OF_MONTH));
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, this,
					GregorianCalendar.getInstance().get(Calendar.HOUR_OF_DAY),
					GregorianCalendar.getInstance().get(Calendar.MINUTE),
					false);	
		}
		
		return null;
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		// TODO Auto-generated method stub
		
		mActiveCalendar.set(year, month, day);
		
		//update fields
		String dateText = new String (day + "/" + month + "/" + year);
		TextView dateTextView = (TextView) findViewById(R.id.editGameDateField);
		dateTextView.setText(dateText);

	}
	
	@Override
	public void onTimeSet(TimePicker view, int hour, int minute) {

		mActiveCalendar.set(Calendar.HOUR, hour);
		mActiveCalendar.set(Calendar.MINUTE, minute);
		
		//update fields
		String timeText;
		if (hour > 12) {
			hour -= 12;
			timeText = new String(hour + ":" + minute + " PM");
		} else {
			timeText = new String(hour + ":" + minute + " AM");
		}
		
		TextView timeTextView = (TextView) findViewById(R.id.editGameTimeField);
		timeTextView.setText(timeText);
	
	}
	
}

