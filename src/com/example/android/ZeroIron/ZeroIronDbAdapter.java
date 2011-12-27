
/*
 * Copyright (C) 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.android.ZeroIron;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Simple database access helper class, taken from the notepad tutorial
 * at developer.android.com and modified for the ZeroIron application.
 * It defines the basic CRUD operations and gives the ability to list
 * all or a single entry.
 */
public class ZeroIronDbAdapter {

	public static final String KEY_COURSE_ID = "_id";
	public static final String KEY_COURSE_NAME = "name";
	public static final String KEY_COURSE_LOCATION = "location";
	public static final String KEY_COURSE_PAR = "par";
	public static final String KEY_COURSE_SIZE = "size";		
	
	public static final String KEY_GAME_ID = "_id";
	public static final String KEY_GAME_COURSE_ID = "course_id";
	public static final String KEY_GAME_NAME = "name";
	public static final String KEY_GAME_DATE = "date";
	public static final String KEY_GAME_NOTES = "notes";
	public static final String KEY_GAME_STATUS = "status";

	public static final String KEY_ROWID = "_id";
    public static final String KEY_HOLE = "hole";
    public static final String KEY_PAR = "par";
    public static final String KEY_SCORE = "score";
    
    public static final String KEY_SETTING = "setting";
    public static final String KEY_VALUE = "value";
        
    private static final String TAG = "ZeroIronDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    /**
     * Database creation sql statement
     */
    private static final String DATABASE_CREATE_COURSES =
            "create table if not exists courses (_id integer primary key autoincrement, "
            + KEY_COURSE_NAME + " text not null, " + KEY_COURSE_LOCATION + " text not null, "
            + KEY_COURSE_PAR + " integer, " + KEY_COURSE_SIZE + " integer);";
                
    private static final String DATABASE_CREATE_GAMES =
            "create table if not exists games (_id integer primary key autoincrement, "
            + KEY_GAME_COURSE_ID + " integer not null, " + KEY_GAME_NAME + " text not null, " + KEY_GAME_DATE + " text not null, "
            + KEY_GAME_NOTES + " text not null, " + KEY_GAME_STATUS + " integer not null" + ");";
            
    private static final String DATABASE_CREATE_SCORES =
        "create table if not exists scores (_id integer primary key autoincrement, "
        + "hole text not null, par text not null, score text not null);";
    
    private static final String DATABASE_CREATE_SETTINGS =
            "create table if not exists settings (_id integer primary key autoincrement, "
                    + "setting text not null, value integer);";

    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_COURSES = "courses";
    private static final String DATABASE_GAMES = "games";
    private static final String DATABASE_SCORES = "scores";
    private static final String DATABASE_SETTINGS = "settings";
    private static final int DATABASE_VERSION = 2;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        	db.execSQL(DATABASE_CREATE_COURSES);
        	db.execSQL(DATABASE_CREATE_GAMES);
            db.execSQL(DATABASE_CREATE_SCORES);
            db.execSQL(DATABASE_CREATE_SETTINGS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
        }
    }

    public ZeroIronDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public ZeroIronDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }
    
////////////////////////////////////////////////////////////////////////
    
    public boolean createCoursesTableIfRequired() {
    	
    	//test to see if table is actually deleted.
    	Cursor x;
    	try {
    		x = mDb.query(DATABASE_COURSES, new String[] {KEY_ROWID, KEY_COURSE_NAME,
	        		KEY_COURSE_LOCATION, KEY_COURSE_PAR, KEY_COURSE_SIZE}, null, null, null, null, null);
    	} catch (Exception e) {
    		int ert=0;
    		ert++;
    	}
    	
    	try {
    		mDb.execSQL(DATABASE_CREATE_COURSES);
    	} catch (Exception e) {
    		Toast.makeText(this.mCtx, "Error creating courses table.", Toast.LENGTH_SHORT).show();
    		return false;
    	}    	
    	return true;
    }
    

    public Cursor fetchAllCourses() { 
    	try {
	        return mDb.query(DATABASE_COURSES, new String[] {KEY_ROWID, KEY_COURSE_NAME,
	        		KEY_COURSE_LOCATION, KEY_COURSE_PAR, KEY_COURSE_SIZE}, null, null, null, null, null);
    	} catch (Exception e ) {
    		Toast.makeText(this.mCtx, "Error fetching all courses.", Toast.LENGTH_SHORT).show();
    		return null;
    	}

    }
    
    public int fetchCourseId(String courseName) {
    	
    	try {
    		Cursor cursor = mDb.query(DATABASE_COURSES,  new String[] {KEY_ROWID}, KEY_COURSE_NAME + "= '" + courseName + "'", null, null, null, null, null);
            if (cursor != null) {
            	cursor.moveToFirst();
            	return cursor.getInt(0);

            }
    	} catch (Exception e ) {
    		Toast.makeText(this.mCtx, "Error fetching course with name: " + courseName + ".", Toast.LENGTH_SHORT).show();
    	}
    	
    	return -1;
    	
    }
    
    public boolean writeCourse(ZeroIronCourseStructure oldCourseStructure, ZeroIronCourseStructure newCourseStructure) { 
    	
    	if (oldCourseStructure == null) {
    		
    		//new record
	        ContentValues initialValues = new ContentValues();
	        initialValues.put(KEY_COURSE_NAME, newCourseStructure.getCourseName());
	        initialValues.put(KEY_COURSE_LOCATION, newCourseStructure.getCourseLocation());
	        initialValues.put(KEY_COURSE_PAR, newCourseStructure.getCoursePar());
	        initialValues.put(KEY_COURSE_SIZE, newCourseStructure.getCourseSize());
	
	        return mDb.insert(DATABASE_COURSES, null, initialValues) > 0;
	        
    	} else {
    		
    		ContentValues newValues = new ContentValues();
    		newValues.put(KEY_COURSE_NAME, newCourseStructure.getCourseName());
    		newValues.put(KEY_COURSE_LOCATION, newCourseStructure.getCourseLocation());
    		newValues.put(KEY_COURSE_PAR, newCourseStructure.getCoursePar());
    		newValues.put(KEY_COURSE_SIZE, newCourseStructure.getCourseSize());
    		
    		ContentValues oldValues = new ContentValues();
    		oldValues.put(KEY_COURSE_NAME, "'" + oldCourseStructure.getCourseName() + "'");
    		
    		String newTest = newValues.toString();
    		String oldTest = oldValues.toString();
    		
    		return mDb.update(DATABASE_COURSES, newValues, oldValues.toString(), null) > 0;

    	}

    }
    
    public boolean deleteAllCourses() {
    	
    	try {
    		mDb.delete(DATABASE_COURSES, null, null);
    	} catch (Exception e) {
    		Toast.makeText(this.mCtx, "Error deleting courses table.", Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	
    	return true;
    }
   
    public boolean deleteCourse(String courseName) {
    	
    	try {
    		
    		mDb.delete(DATABASE_COURSES, KEY_COURSE_NAME + "='" + courseName + "'", null);
    		
    	} catch (Exception e) {
    		Toast.makeText(this.mCtx, "Error deleting course with name" + courseName + ".", Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	
    	int ert=0;
    	ert++;
    	
    	return true;
    }
    
    public boolean dropCoursesTable() {
    	try {
    		mDb.execSQL("drop table courses");
    	} catch (Exception e) {
    		Toast.makeText(this.mCtx, "Error dropping courses table.", Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	
    	return true;
    }    
    
//////////////////////////////////////////////////////////////////////

    public boolean createGamesTableIfRequired() {
    	
    	//test to see if table is actually deleted.
    	Cursor x;
    	try {
    		x = mDb.query(DATABASE_GAMES, new String[] {KEY_ROWID, KEY_GAME_COURSE_ID, KEY_GAME_NAME,
	        		KEY_GAME_DATE, KEY_GAME_NOTES, KEY_GAME_STATUS}, null, null, null, null, null);
    	} catch (Exception e) {
    		int ert=0;
    		ert++;
    	}
    	
    	try {
    		mDb.execSQL(DATABASE_CREATE_GAMES);
    	} catch (Exception e) {
    		Toast.makeText(this.mCtx, "Error creating games table.", Toast.LENGTH_SHORT).show();
    		return false;
    	}    	
    	return true;
    }
    
    public Cursor fetchAllGames() { 
    	try {
	        return mDb.query(DATABASE_GAMES, new String[] {KEY_ROWID, KEY_GAME_COURSE_ID, KEY_GAME_NAME,
	        		KEY_GAME_DATE, KEY_GAME_NOTES, KEY_GAME_STATUS}, null, null, null, null, null);
    	} catch (Exception e ) {
    		Toast.makeText(this.mCtx, "Error fetching all games.", Toast.LENGTH_SHORT).show();
    		return null;
    	}

    }
    
    public boolean writeGame(ZeroIronGameStructure game) {    	
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_GAME_COURSE_ID, game.getCourseId());
        initialValues.put(KEY_GAME_NAME, game.getName().toString());
        initialValues.put(KEY_GAME_DATE, game.getDate().toString());
        initialValues.put(KEY_GAME_NOTES, game.getNotes());
        initialValues.put(KEY_GAME_STATUS, game.getStatus());

        return mDb.insert(DATABASE_GAMES, null, initialValues) > 0;

    }
    
    public boolean deleteAllGames() {
    	
    	try {
    		mDb.delete(DATABASE_GAMES, null, null);
    	} catch (Exception e) {
    		Toast.makeText(this.mCtx, "Error deleting games table.", Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	
    	return true;
    }
   
    public boolean deleteGame(ZeroIronGameStructure game) {
    	
    	return false;
    }
    
    public boolean dropGamesTable() {
    	try {
    		mDb.execSQL("drop table games");
    	} catch (Exception e) {
    		Toast.makeText(this.mCtx, "Error dropping games table.", Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	
    	return true;
    }
    
////////////////////////////////////////////////////////////////////////
     
    
    public long createScore(int hole, int par, int score) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_HOLE, hole);
        initialValues.put(KEY_PAR, par);
        initialValues.put(KEY_SCORE, score);

        return mDb.insert(DATABASE_SCORES, null, initialValues);
    }

    public Cursor fetchScore(long rowId) throws SQLException {

        Cursor mCursor =

            mDb.query(true, DATABASE_SCORES, new String[] {KEY_ROWID,
                    KEY_HOLE, KEY_PAR, KEY_SCORE}, KEY_ROWID + "=" + rowId, null,
                    null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean updateScore(long rowId, int hole, int par, int score) {
        ContentValues args = new ContentValues();
        args.put(KEY_HOLE, hole);
        args.put(KEY_PAR, par);
        args.put(KEY_SCORE, score);

        return mDb.update(DATABASE_SCORES, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor fetchAllScores() {

        return mDb.query(DATABASE_SCORES, new String[] {KEY_ROWID, KEY_HOLE,
                KEY_PAR, KEY_SCORE}, null, null, null, null, null);
    }
    
    public void deleteAllScores() {
    	
    	try {
    		mDb.delete(DATABASE_SCORES, null, null);
    	} catch (Exception e) {
    		Toast.makeText(this.mCtx, "Scores table does not exist.", Toast.LENGTH_SHORT).show();
    	}
    	
    }

    public long getCount() {
    	String sql = "SELECT COUNT(*) FROM " + DATABASE_SCORES;
        SQLiteStatement statement = mDb.compileStatement(sql);
        long count = statement.simpleQueryForLong();
    	
    	return count;
    }

/////////////////////////////////////////////

    //Method that attempts to create a settings database if it does not exist.
    public boolean settingsTableRebuild() {
    	try {
    	
    		//recreate a new table
    		mDb.delete(DATABASE_SETTINGS, null, null);
    	   	mDb.execSQL(DATABASE_CREATE_SETTINGS);
    	   	
    	   	long test = createSetting(ZeroIronSettings.TRANSPARENCY, 1);
    	   	
    	   	//TESTS.
    	   	/*
    	   	Cursor c = settingsTableFetchAllSettings();
    	   	if (c.moveToFirst()) {
    	   		do {
    	   			String s1 = c.getString(0);
    	   			String s2 = c.getString(1);
    	   			String s3 = c.getString(2);
    	   			int ert=0;
    	   			ert++;
    	   		} while (c.moveToNext());
    	   	}
    	   	*/
    	   	
    	} catch (Exception e) {
    		Toast.makeText(this.mCtx, "Error creating settings database.", Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	return true;
    }

    public long createSetting(String setting, int value) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_SETTING, setting);
        initialValues.put(KEY_VALUE, value);

        return mDb.insert(DATABASE_SETTINGS, null, initialValues);
    }
    
    public boolean settingsTableDelete() {
    	try {
    	} catch (Exception e) {
    		mDb.delete(DATABASE_SETTINGS, null, null);
    		Toast.makeText(this.mCtx, "Error deleting database.", Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	return true;
    }
    
    public Cursor settingsTableFetchAllSettings() {
    	try {
    		return mDb.query(DATABASE_SETTINGS, new String[] {KEY_ROWID, KEY_SETTING,
                KEY_VALUE}, null, null, null, null, null);
    	} catch (Exception e) {
    		Toast.makeText(this.mCtx, "Error reading settings. Rebuilding...", Toast.LENGTH_SHORT).show();
    	}
    	return null;
    }
    
    public boolean settingsTableSetAttrib(String attrib, int value) {
        ContentValues args = new ContentValues();
        //args.put(KEY_SETTING, attrib);
        args.put(KEY_VALUE, value);

        boolean result = false;
        
        try {
        	//does not work
        	//result = mDb.update(DATABASE_SETTINGS, args, KEY_SETTING + "=" + ZeroIronSettings.TRANSPARENCY, null) > 0;
        	
        	//working
        	mDb.execSQL("update settings set value='" + value + "' where setting='" + attrib + "'");
        	
        } catch (Exception e) {
        	Toast.makeText(this.mCtx, "Cannot update setting.", Toast.LENGTH_SHORT).show();
        }
        return result;

    }

/////////////////////////////////////////////

    
}







