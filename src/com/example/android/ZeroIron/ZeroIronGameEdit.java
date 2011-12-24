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
import android.widget.Spinner;
import android.widget.TextView;

public class ZeroIronGameEdit extends Activity {

	int mCurrentWeatherSelection;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zeroiron_editgame);
		
		mCurrentWeatherSelection = 0;
		
		//retrieve intent
		Bundle bundle = getIntent().getExtras();

		final ImageView okButton = (ImageView) findViewById(R.id.imageOk);
		
		okButton.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				//Retrieve score?
				TextView textView = (TextView) findViewById(R.id.courseName);
				String courseName = textView.getText().toString();

				//retrieve content string
				ZeroIronGameStructure gameStructure = new ZeroIronGameStructure();
				gameStructure.setGameScore(101);
				gameStructure.setCourseName(courseName);				
				
				Intent i = new Intent(ZeroIronGameEdit.this, ZeroIronGamesList.class);
				i.putExtra(ZeroIronGamesList.GAME_STRUCTURE, gameStructure);
				
				setResult(RESULT_OK, i);
				finish();
			}
			
		});
		
        /*
		v = (ImageView) findViewById(R.id.imageMixed);
        v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				v.setBackgroundColor(0);
				//v.setImageBitmap(res.getDrawable(R.drawable.img_down));
			}
        });
        */
		/*
		//Build text & spinners
		TextView textViewHole = (TextView) findViewById(R.id.textView_holeValue);
		Integer holeValue = currentHole;
		textViewHole.setText(holeValue.toString());
		
		Spinner spinnerPar = (Spinner) findViewById(R.id.spinner_par);
        ArrayAdapter<CharSequence> adapterPar = ArrayAdapter.createFromResource(
        		this, R.array.par_values, android.R.layout.simple_spinner_item);
        adapterPar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPar.setAdapter(adapterPar);
        spinnerPar.setSelection(currentPar - ZeroIronScoreBoard.SPINNER_PAR_OFFSET);   

		Spinner spinnerScore = (Spinner) findViewById(R.id.spinner_score);
		 ArrayAdapter<CharSequence> adapterScore = ArrayAdapter.createFromResource(
        		this, R.array.score_values, android.R.layout.simple_spinner_item);
		adapterScore.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerScore.setAdapter(adapterScore);
        spinnerScore.setSelection(currentScore - ZeroIronScoreBoard.SPINNER_SCORE_OFFSET);

		Button saveButton = (Button) findViewById(R.id.save_button);
		saveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				//get hole value
				Intent intent = getIntent();
				Bundle extras = intent.getExtras();
				int listIndex = extras.getInt("index");
				int hole = extras.getInt("hole");
				
				//get par value
				Spinner spinnerPar = (Spinner) findViewById(R.id.spinner_par);
				int newPar = spinnerPar.getSelectedItemPosition();
				newPar += ZeroIronScoreBoard.SPINNER_PAR_OFFSET;
				
				//get score value
				Spinner spinnerScore = (Spinner) findViewById(R.id.spinner_score);
				int newScore = spinnerScore.getSelectedItemPosition();
				newScore += ZeroIronScoreBoard.SPINNER_SCORE_OFFSET;
				
				//insert values in intent
				intent = new Intent(ZeroIronEditScore.this, ZeroIronScoreBoard.class);
				intent.putExtra("index", listIndex);
				intent.putExtra("hole", hole);
				intent.putExtra("par", newPar);
				intent.putExtra("score", newScore);

				//return
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		
		Button cancelButton = (Button) findViewById(R.id.imageCancel);
		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});	
		*/	

		
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
	

	
}
