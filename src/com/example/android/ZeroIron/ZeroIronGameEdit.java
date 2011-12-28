package com.example.android.ZeroIron;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class ZeroIronGameEdit extends Activity implements OnClickListener, OnDateSetListener, OnTimeSetListener {

	public static final String OLD_RECORD = "oldrecord";
	public static final String NEW_RECORD = "newrecord";
	
	//for use when a new game is created from a course name and id.
	protected int mCourseId;
	
	//for use when a game is being edited and an existing record is sent through
	protected ZeroIronGameStructure mOldGameStructure;

	//variable used in either cases above
	protected String mCourseName;
	
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
		mCourseName = null;
		mCourseId = 0;
		
		mOldGameStructure = null;
		
		mActiveCalendar = new GregorianCalendar();
			
		//different behaviour if a new game is created or an existing one is edited
		prepareFromIntent();
		
		//set button listeners
		setButtonListeners();		
		
	}
	
	protected boolean prepareFromIntent() {
		
		Bundle bundle = getIntent().getExtras();
		
		if ( bundle != null && bundle.containsKey(ZeroIronDbAdapter.KEY_COURSE_NAME) &&
				   bundle.containsKey(ZeroIronDbAdapter.KEY_COURSE_ID) ) {

			mCourseName = bundle.getString(ZeroIronDbAdapter.KEY_COURSE_NAME);
			mCourseId = bundle.getInt(ZeroIronDbAdapter.KEY_COURSE_ID);
			
			this.onDateSet(null,
					mActiveCalendar.get(Calendar.YEAR),
					mActiveCalendar.get(Calendar.MONTH),
					mActiveCalendar.get(Calendar.DAY_OF_MONTH));
			
			this.onTimeSet(null,
					mActiveCalendar.get(Calendar.HOUR),
					mActiveCalendar.get(Calendar.MINUTE));
			
		} else if ( bundle != null && bundle.containsKey(ZeroIronGameStructure.GAME_STRUCTURE) &&
				bundle.containsKey(ZeroIronDbAdapter.KEY_COURSE_NAME)) {
			
			mOldGameStructure = (ZeroIronGameStructure) bundle.getSerializable(ZeroIronGameStructure.GAME_STRUCTURE);
			mCourseId = mOldGameStructure.getCourseId();
			
			//populate fields using game structure
			
			EditText gameNameText = (EditText) findViewById(R.id.editGameName);
			gameNameText.setText(mOldGameStructure.getName());
			
			this.onDateSet(null,
					mOldGameStructure.getDate().get(Calendar.YEAR),
					mOldGameStructure.getDate().get(Calendar.MONTH),
					mOldGameStructure.getDate().get(Calendar.DAY_OF_MONTH));
			
			this.onTimeSet(null,
					mOldGameStructure.getDate().get(Calendar.HOUR),
					mOldGameStructure.getDate().get(Calendar.MINUTE));
			
			EditText gameNotesText = (EditText) findViewById(R.id.editGameNotes);
			gameNotesText.setText(mOldGameStructure.getNotes());
						
		} else {
			Toast.makeText(this, "ERR: Intent contents corrupt", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		//Retrieve course name
		mCourseName = bundle.getString(ZeroIronDbAdapter.KEY_COURSE_NAME);
		
		TextView textNameView = (TextView) findViewById(R.id.textCourseNameValue);
		textNameView.setText(mCourseName);
		
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

	@Override
	public void onClick(View view) {
		
		if (view == mOkButton) {
			
			//1 - retrieve content from widgets
			TextView nameText = (TextView) findViewById(R.id.editGameName);
			TextView notesText = (TextView) findViewById(R.id.editGameNotes);
			
			//2 - store in structure
			ZeroIronGameStructure newGameStructure = new ZeroIronGameStructure();
			
			newGameStructure.setCourseId(mCourseId);			
			newGameStructure.setName(nameText.getText().toString());
			newGameStructure.setDate(mActiveCalendar);
			newGameStructure.setNotes(notesText.getText().toString());
			newGameStructure.setStatus(0);
						
			//3 - return to invoking activity
			Intent i = new Intent(ZeroIronGameEdit.this, ZeroIronGamesList.class);
			i.putExtra(NEW_RECORD, newGameStructure);
			
			//4 - If old game structure is not null, add it in the intent.
			if (mOldGameStructure != null) {
				i.putExtra(OLD_RECORD, mOldGameStructure);				
			}
			
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

