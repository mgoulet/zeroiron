package com.example.android.ZeroIron;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class ZeroIronMain extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zeroiron_main);
		
		// Create an Intent to launch an Activity for the tab (to be reused)
        Intent intent;
		Resources res = getResources();
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;

		intent = new Intent().setClass(this, ZeroIronCourseList.class);
        spec = tabHost.newTabSpec("Tab3").setIndicator("Courses",
                res.getDrawable(R.drawable.golf32))
            .setContent(intent);
		tabHost.addTab(spec);	
		
		intent = new Intent().setClass(this, ZeroIronGamesList.class);
        spec = tabHost.newTabSpec("Tab1").setIndicator("Games",
                res.getDrawable(R.drawable.ball32))
            .setContent(intent);
		tabHost.addTab(spec);
		
		intent = new Intent().setClass(this, ZeroIronSettings.class);
        spec = tabHost.newTabSpec("Tab4").setIndicator("Options",
                res.getDrawable(R.drawable.gear32))
            .setContent(intent);
		tabHost.addTab(spec);
		
		tabHost.setCurrentTab(0);
		
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
