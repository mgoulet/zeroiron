/*
 * Copyright Martin Goulet 2012 - ZeroIron
 */

package com.mg.ZeroIron;

import java.util.Random;
import java.util.Calendar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 * This class contains settings activity (turned into a simple About page).
 */
public class ZeroIronSettings extends Activity {

	private ZeroIronDbAdapter mDbAdapter;

	public static final String TRANSPARENCY = "transparency";
	public static final int TRANSPARENT_VALUE = 1;
	public static final int NOT_TRANSPARENT_VALUE = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zeroiron_settings);

		mDbAdapter = ZeroIronApplication.getDbAdapter();
		
		buildClickListeners();
		
		mDbAdapter.settingsTableRebuild();
	
	}
	
	private void buildClickListeners() {
		/*
		//checkbox
		CheckBox transparencyCheckBox = new CheckBox(this);
		transparencyCheckBox = (CheckBox) findViewById(R.id.checkBox1);
		transparencyCheckBox.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				CheckBox transparencyCheckBox = (CheckBox) findViewById(R.id.checkBox1);
				if ( transparencyCheckBox.isChecked()) {
					mDbAdapter.settingsTableSetAttrib(TRANSPARENCY, TRANSPARENT_VALUE);
				} else {
					mDbAdapter.settingsTableSetAttrib(TRANSPARENCY, NOT_TRANSPARENT_VALUE);
				}
			}
		});
		*/

	}
	
    private void displaySettings() {
        /*
    	// Get a handle on all settings and display them
        Cursor c = mDbAdapter.settingsTableFetchAllSettings();
        
        if (c.moveToFirst()) {
        	do {
        		String settingString = c.getString(1);
        		if (settingString.matches(TRANSPARENCY)) {
        			int value = c.getInt(2);
        			CheckBox checkBox= new CheckBox(this);
        			checkBox = (CheckBox) findViewById(R.id.checkBox1);
        			
        			if (value == TRANSPARENT_VALUE) {
        				//set checked
        				checkBox.setChecked(true);
        			} else {
        				//set unchecked
        				checkBox.setChecked(false);
        			}
        			
        		}
        	} while (c.moveToNext());
        }
        */
        
    }
    
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
				
		//Display current data from database
		displaySettings();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	
}
