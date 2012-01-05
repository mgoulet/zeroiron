/*
 * Copyright Martin Goulet 2012 - ZeroIron
 */

package com.mg.ZeroIron;

import android.app.backup.BackupAgentHelper;

/**
 * Main applicaiton class
 */
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

