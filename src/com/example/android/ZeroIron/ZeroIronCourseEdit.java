package com.example.android.ZeroIron;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class ZeroIronCourseEdit extends Activity {

	public static final String OLD_RECORD = "oldrecord";
	public static final String NEW_RECORD = "newrecord";
	
	ZeroIronCourseStructure mOldCourseStructure;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zeroiron_editcourse);
								
		mOldCourseStructure = null;
		
		//This checks if we are editing an existing record or not
		Bundle bundle = getIntent().getExtras();
		
		if ( bundle != null && bundle.containsKey(OLD_RECORD) ) {
			
			mOldCourseStructure = (ZeroIronCourseStructure) bundle.getSerializable(OLD_RECORD);
			
			//populate widgets
			populateWidgets(mOldCourseStructure.getCourseName(),
							mOldCourseStructure.getCourseLocation(),
							mOldCourseStructure.getCoursePar(),
							mOldCourseStructure.getCourseSize());
		}
			
		final ImageView okButton = (ImageView) findViewById(R.id.imageOk);
		
		okButton.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				//Retrieve course name
				TextView textNameView = (TextView) findViewById(R.id.editCourseName);
				String courseName = textNameView.getText().toString();
				
				//Retrieve course location
				TextView textLocationView = (TextView) findViewById(R.id.editCourseLocation);
				String courseLocation = textLocationView.getText().toString(); 
				
				//Retrieve course Par
				int coursePar = 5;
				RadioButton optionRadioPar0 = (RadioButton) findViewById(R.id.radioPar0);
				RadioButton optionRadioPar1 = (RadioButton) findViewById(R.id.radioPar1);
				RadioButton optionRadioPar2 = (RadioButton) findViewById(R.id.radioPar2);
				if (optionRadioPar0.isChecked()) {
					coursePar = 3;
				} else if (optionRadioPar1.isChecked()) {
					coursePar = 4;
				} else if (optionRadioPar2.isChecked()) {
					coursePar = 5;
				}
				
				//Retrieve course Size
				int courseSize = 5;
				RadioButton optionRadioSize0 = (RadioButton) findViewById(R.id.radioSize0);
				RadioButton optionRadioSize1 = (RadioButton) findViewById(R.id.radioSize1);
				if (optionRadioSize0.isChecked()) {
					courseSize = 9;
				} else if (optionRadioSize1.isChecked()) {
					courseSize = 18;
				}
				
				ZeroIronCourseStructure newCourseStructure = new ZeroIronCourseStructure();
				newCourseStructure.setCourseName(courseName);
				newCourseStructure.setCourseLocation(courseLocation);
				newCourseStructure.setCoursePar(coursePar);
				newCourseStructure.setCourseSize(courseSize);	
				
				Intent i = new Intent(ZeroIronCourseEdit.this, ZeroIronGamesList.class);
				i.putExtra(NEW_RECORD, newCourseStructure);
				
				//add old structure if required
				if (mOldCourseStructure != null) {
					i.putExtra(OLD_RECORD, mOldCourseStructure);
				}
				
				setResult(RESULT_OK, i);
				finish();
			}
			
		});

		final ImageView cancelButton = (ImageView) findViewById(R.id.imageCancel);
		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});		

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
	
	private boolean populateWidgets(String courseName, String courseLocation, int coursePar, int courseSize) {
		
		//Retrieve course name
		TextView textNameView = (TextView) findViewById(R.id.editCourseName);
		textNameView.setText(courseName);
		
		//Retrieve course location
		TextView textLocationView = (TextView) findViewById(R.id.editCourseLocation);
		textLocationView.setText(courseLocation);
		
		RadioButton optionRadioPar0 = (RadioButton) findViewById(R.id.radioPar0);
		RadioButton optionRadioPar1 = (RadioButton) findViewById(R.id.radioPar1);
		RadioButton optionRadioPar2 = (RadioButton) findViewById(R.id.radioPar2);
		switch (coursePar) {
		default:
		case 3:
			optionRadioPar0.setChecked(true);
			optionRadioPar1.setChecked(false);
			optionRadioPar2.setChecked(false);
			break;
		case 4:
			optionRadioPar0.setChecked(false);
			optionRadioPar1.setChecked(true);
			optionRadioPar2.setChecked(false);
			break;
		case 5:
			optionRadioPar0.setChecked(false);
			optionRadioPar1.setChecked(false);
			optionRadioPar2.setChecked(true);
			break;	
		}
		
		RadioButton optionRadioSize0 = (RadioButton) findViewById(R.id.radioSize0);
		RadioButton optionRadioSize1 = (RadioButton) findViewById(R.id.radioSize1);
		
		switch (courseSize) {
		default:
		case 9:
			optionRadioSize0.setChecked(true);
			optionRadioSize1.setChecked(false);
			break;
		case 18:
			optionRadioSize0.setChecked(false);
			optionRadioSize1.setChecked(true);
			break;
		}
		if (optionRadioSize0.isChecked()) {
			courseSize = 9;
		} else if (optionRadioSize1.isChecked()) {
			courseSize = 18;
		}
		
		return true;
		
	}
	
}

