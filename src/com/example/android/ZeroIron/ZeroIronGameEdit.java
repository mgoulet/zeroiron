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
	
	//for use when a new game is created from a course name and id.
	protected int mCourseId;
	
	//for use when a game is being edited and an existing record is sent through
	protected ZeroIronGameStructure mOldGameStructure;

	//variable used in either cases above
	protected String mCourseName;
	
	protected static final int DATE_DIALOG_ID = 0;
	protected static final int TIME_DIALOG_ID = 1;
	
	protected GregorianCalendar mActiveCalendar;
	
	//button handles for callbacks
	protected ImageView mOkButton;
	protected ImageView mCancelButton;
	protected ImageButton mDateChangeButton;
	protected ImageButton mTimeChangeButton;
	
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
			
			//A new game is being created
			
			mCourseName = bundle.getString(ZeroIronDbAdapter.KEY_COURSE_NAME);
			mCourseId = bundle.getInt(ZeroIronDbAdapter.KEY_COURSE_ID);
			
		} else if ( bundle != null && bundle.containsKey(ZeroIronGameStructure.GAME_STRUCTURE) &&
				bundle.containsKey(ZeroIronDbAdapter.KEY_COURSE_NAME)) {
			
			//An existing game is being edited
			
			mOldGameStructure = (ZeroIronGameStructure) bundle.getSerializable(ZeroIronGameStructure.GAME_STRUCTURE);
			mCourseId = mOldGameStructure.getCourseId();
			mActiveCalendar = mOldGameStructure.getDate();
			
			//populate fields using game structure
			
			EditText gameNameText = (EditText) findViewById(R.id.editGameName);
			gameNameText.setText(mOldGameStructure.getName());
			
			EditText gameNotesText = (EditText) findViewById(R.id.editGameNotes);
			gameNotesText.setText(mOldGameStructure.getNotes());
			
			EditText gameScoreText = (EditText) findViewById(R.id.editGameScore);
			String tmp = new String( "" + mOldGameStructure.getScore());
			gameScoreText.setText(tmp);
						
		} else {
			Toast.makeText(this, "ERR: Intent contents corrupt", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		//populate course name
		mCourseName = bundle.getString(ZeroIronDbAdapter.KEY_COURSE_NAME);
		TextView textNameView = (TextView) findViewById(R.id.textCourseNameValue);
		textNameView.setText(mCourseName);
		
		//populate date
		this.onDateSet(null,
				mActiveCalendar.get(Calendar.YEAR),
				mActiveCalendar.get(Calendar.MONTH),
				mActiveCalendar.get(Calendar.DAY_OF_MONTH));
		
		//populate time
		this.onTimeSet(null,
				mActiveCalendar.get(Calendar.HOUR),
				mActiveCalendar.get(Calendar.MINUTE));
		
		return true;
	}
	
	protected boolean setButtonListeners() {

		mOkButton = (ImageView) findViewById(R.id.imageOk);
		mCancelButton = (ImageView) findViewById(R.id.imageCancel);
		mDateChangeButton = (ImageButton) findViewById(R.id.editGameDateButton);
		mTimeChangeButton = (ImageButton) findViewById(R.id.editGameTimeButton);
		
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

	@Override
	public void onClick(View view) {
		
		if (view == mOkButton) {
			
			//1 - retrieve content from widgets
			TextView nameText = (TextView) findViewById(R.id.editGameName);
			TextView notesText = (TextView) findViewById(R.id.editGameNotes);
			TextView gameScoreText = (TextView) findViewById(R.id.editGameScore);
			
			//2 - store in structure
			ZeroIronGameStructure newGameStructure = new ZeroIronGameStructure();
			
			newGameStructure.setCourseId(mCourseId);			
			newGameStructure.setName(nameText.getText().toString());
			newGameStructure.setDate(mActiveCalendar);
			newGameStructure.setNotes(notesText.getText().toString());
			newGameStructure.setStatus(0);
			
			String tmp = gameScoreText.getText().toString();
			if (tmp.equals("")) {
				newGameStructure.setScore(0);
			} else {
				newGameStructure.setScore(Integer.parseInt(tmp));
			}
						
			//3 - return to invoking activity
			Intent i = new Intent(ZeroIronGameEdit.this, ZeroIronGamesList.class);
			i.putExtra(ZeroIronDbAdapter.NEW_RECORD, newGameStructure);
			
			//4 - If old game structure is not null, add it in the intent.
			if (mOldGameStructure != null) {
				i.putExtra(ZeroIronDbAdapter.OLD_RECORD, mOldGameStructure);				
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
					mActiveCalendar.get(Calendar.YEAR),
					mActiveCalendar.get(Calendar.MONTH),
					mActiveCalendar.get(Calendar.DAY_OF_MONTH));
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, this,
					mActiveCalendar.get(Calendar.HOUR_OF_DAY),
					mActiveCalendar.get(Calendar.MINUTE),
					true );	
		}
		
		return null;
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		// TODO Auto-generated method stub
		
		mActiveCalendar.set(year, month, day);
		
		//update fields
		String dateText = new String (day + "/" + (month + 1) + "/" + year);
		TextView dateTextView = (TextView) findViewById(R.id.editGameDateField);
		dateTextView.setText(dateText);

	}
	
	@Override
	public void onTimeSet(TimePicker view, int hour, int minute) {

		mActiveCalendar.set(Calendar.HOUR, hour);
		mActiveCalendar.set(Calendar.MINUTE, minute);
		
		//update fields
		String timeText;
		if (minute < 10) {
		timeText = new String(hour + ":0" + minute);
		} else {
			timeText = new String(hour + ":" + minute);
		}
		
		TextView timeTextView = (TextView) findViewById(R.id.editGameTimeField);
		timeTextView.setText(timeText);
	
	}
	
}

