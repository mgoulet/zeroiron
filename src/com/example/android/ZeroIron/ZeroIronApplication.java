package com.example.android.ZeroIron;

import android.app.backup.BackupAgentHelper;

public class ZeroIronApplication extends android.app.Application {

	private static ZeroIronApplication mInstance;
	
	private static ZeroIronDbAdapter mDbAdapter;
	
	public static ZeroIronApplication getInstance() {
		return mInstance;
	}
	
	public static ZeroIronDbAdapter getDbAdapter() {
		return mDbAdapter;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		//create the db adapter
		mDbAdapter = new ZeroIronDbAdapter(this.getApplicationContext());
		mDbAdapter.open();
		
		mInstance = this;
	}
	
}

