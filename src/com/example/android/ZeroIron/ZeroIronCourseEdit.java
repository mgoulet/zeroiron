package com.example.android.ZeroIron;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class ZeroIronCourseEdit extends Activity implements OnClickListener {

	protected ZeroIronCourseStructure mOldCourseStructure;
	
	protected ImageView mOkButton;
	protected ImageView mCancelButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zeroiron_editcourse);
				
		prepareFromIntent();
		
		setButtonListeners();

	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	
	protected boolean prepareFromIntent() {
		
		mOldCourseStructure = null;
		
		//This checks if we are editing an existing record or not
		Bundle bundle = getIntent().getExtras();
		
		if ( bundle != null && bundle.containsKey(ZeroIronDbAdapter.OLD_RECORD) ) {
			
			mOldCourseStructure = (ZeroIronCourseStructure) bundle.getSerializable(ZeroIronDbAdapter.OLD_RECORD);
			
			//Retrieve course name
			TextView textNameView = (TextView) findViewById(R.id.editCourseName);
			textNameView.setText(mOldCourseStructure.getCourseName());
			
			//Retrieve course location
			TextView textLocationView = (TextView) findViewById(R.id.editCourseLocation);
			textLocationView.setText(mOldCourseStructure.getCourseLocation());
			
			RadioButton optionRadioPar0 = (RadioButton) findViewById(R.id.radioPar0);
			RadioButton optionRadioPar1 = (RadioButton) findViewById(R.id.radioPar1);
			RadioButton optionRadioPar2 = (RadioButton) findViewById(R.id.radioPar2);
			switch (mOldCourseStructure.getCoursePar()) {
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
			
			switch (mOldCourseStructure.getCourseSize()) {
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

		}
		
		return true;
	}
	
	protected boolean setButtonListeners() {
		
		mOkButton = (ImageView) findViewById(R.id.imageOk);
		mCancelButton = (ImageView) findViewById(R.id.imageCancel);
		
		mOkButton.setOnClickListener(this);
		mCancelButton.setOnClickListener(this);
		
		return true;
	}

	@Override
	public void onClick(View view) {

		if (view == mOkButton) {
			
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
			i.putExtra(ZeroIronDbAdapter.NEW_RECORD, newCourseStructure);
			
			//add old structure if required
			if (mOldCourseStructure != null) {
				i.putExtra(ZeroIronDbAdapter.OLD_RECORD, mOldCourseStructure);
			}
			
			setResult(RESULT_OK, i);
			finish();			
			
		} else if (view == mCancelButton) {
			
			setResult(RESULT_CANCELED);
			finish();			
			
		}
		
	}
	
}





