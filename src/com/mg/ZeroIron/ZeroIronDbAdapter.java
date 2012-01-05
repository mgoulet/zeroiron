/*
 * Copyright Martin Goulet 2012 - ZeroIron
 */


package com.mg.ZeroIron;

import java.text.SimpleDateFormat;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Simple database access helper class, taken from the notepad tutorial
 * at developer.android.com and modified for the ZeroIron application.
 * It defines the basic CRUD operations for courses and games.
 */
public class ZeroIronDbAdapter {

	public static final String OLD_RECORD = "oldrecord";
	public static final String NEW_RECORD = "newrecord";
	
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
	
	public static final String KEY_COURSE_ID = "_id";
	public static final String KEY_COURSE_NAME = "name";
	public static final String KEY_COURSE_LOCATION = "location";
	public static final String KEY_COURSE_PAR = "par";
	public static final String KEY_COURSE_SIZE = "size";		
	
	//Constants used as shortcut to map course record columns to textfields
	public static final int COURSE_NAME_COLUMN = 1;
	public static final int COURSE_LOCATION_COLUMN = 2;
	public static final int COURSE_PAR_COLUMN = 3;
	public static final int COURSE_SIZE_COLUMN = 4;
	
	public static final String KEY_GAME_ID = "_id";
	public static final String KEY_GAME_COURSE_ID = "course_id";
	public static final String KEY_GAME_NAME = "name";
	public static final String KEY_GAME_DATE = "date";
	public static final String KEY_GAME_NOTES = "notes";
	public static final String KEY_GAME_SCORE = "score";
	public static final String KEY_GAME_STATUS = "status";
	
	//Constants used as shortcut to map game record columns to textfields
	
	public static final int GAME_ID_COLUMN 			= 0;
	public static final int GAME_COURSE_ID_COLUMN 	= 1;
	public static final int GAME_NAME_COLUMN 		= 2;
	public static final int GAME_DATE_COLUMN 		= 3;
	public static final int GAME_NOTES_COLUMN 		= 4;
	public static final int GAME_SCORE_COLUMN 		= 5;
	public static final int GAME_STATUS_COLUMN 		= 6;
		
    public static final String KEY_SETTING_ID = "_id";
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
            + KEY_GAME_NOTES + " text not null, " + KEY_GAME_SCORE + " integer not null, " + KEY_GAME_STATUS + " integer not null" + ");";
    
    private static final String DATABASE_CREATE_SETTINGS =
            "create table if not exists settings (_id integer primary key autoincrement, "
                    + "setting text not null, value integer);";

    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_COURSES = "courses";
    private static final String DATABASE_GAMES = "games";
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
	        return mDb.query(DATABASE_COURSES, new String[] {KEY_COURSE_ID, KEY_COURSE_NAME,
	        		KEY_COURSE_LOCATION, KEY_COURSE_PAR, KEY_COURSE_SIZE}, null, null, null, null, null);
    	} catch (Exception e ) {
    		Toast.makeText(this.mCtx, "Error fetching all courses.", Toast.LENGTH_SHORT).show();
    		return null;
    	}

    }
    
    public int fetchCourseIdFromName(String courseName) {
    	
    	try {
    		Cursor cursor = mDb.query(DATABASE_COURSES,  new String[] {KEY_COURSE_ID}, KEY_COURSE_NAME + "= '" + courseName + "'", null, null, null, null, null);
            if (cursor != null) {
            	cursor.moveToFirst();
            	return cursor.getInt(0);

            }
    	} catch (Exception e ) {
    		Toast.makeText(this.mCtx, "Error fetching course with name: " + courseName + ".", Toast.LENGTH_SHORT).show();
    	}
    	
    	return -1;
    	
    }
    
    public String fetchCourseNameFromId(int id) {
    	
    	try {
    		Cursor cursor = mDb.query(DATABASE_COURSES, new String[] {KEY_COURSE_NAME}, KEY_COURSE_ID + "= " + id, null, null, null, null, null);
    		if (cursor != null) {
    			cursor.moveToFirst();
    			return cursor.getString(0);
    		}    		

    	} catch (Exception e) {
    		Toast.makeText(this.mCtx, "Error fetching course with id: " + id + ".", Toast.LENGTH_SHORT).show();

    	}

    	return null;    	
    	
    }   
    
    public boolean writeCourse(ZeroIronCourseStructure oldCourseStructure, ZeroIronCourseStructure newCourseStructure) { 
    	
    	if (oldCourseStructure == null) {
    		
    		//new record
	        ContentValues initialValues = new ContentValues();
	        initialValues.put(KEY_COURSE_NAME, newCourseStructure.getCourseName());
	        initialValues.put(KEY_COURSE_LOCATION, newCourseStructure.getCourseLocation());
	        initialValues.put(KEY_COURSE_PAR, newCourseStructure.getCoursePar());
	        initialValues.put(KEY_COURSE_SIZE, newCourseStructure.getCourseSize());
	
	        if (!courseWithNameExist(newCourseStructure.getCourseName())) {
	        	return mDb.insert(DATABASE_COURSES, null, initialValues) > 0;
	        } else {
	        	Toast.makeText(this.mCtx, "Error: Course name already exists.", Toast.LENGTH_SHORT).show();
	        	return false;
	        }
	        
    	} else {
    		
    		ContentValues newValues = new ContentValues();
    		newValues.put(KEY_COURSE_NAME, newCourseStructure.getCourseName());
    		newValues.put(KEY_COURSE_LOCATION, newCourseStructure.getCourseLocation());
    		newValues.put(KEY_COURSE_PAR, newCourseStructure.getCoursePar());
    		newValues.put(KEY_COURSE_SIZE, newCourseStructure.getCourseSize());
    		
    		ContentValues oldValues = new ContentValues();
    		oldValues.put(KEY_COURSE_NAME, "'" + oldCourseStructure.getCourseName() + "'");
    		
	        if (!courseWithNameExist(newCourseStructure.getCourseName())) {
	        	return mDb.update(DATABASE_COURSES, newValues, oldValues.toString(), null) > 0;
	        } else {
	        	Toast.makeText(this.mCtx, "Error: Course name already exists.", Toast.LENGTH_SHORT).show();
	        	return false;
	        }
    		
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
    	
    	return true;
    }
    
    protected boolean courseWithNameExist(String courseName) {
    	
    	Cursor c = fetchAllCourses();
    	
    	if (!c.moveToFirst()) {
    		return false;
    	}
    	
    	int count = 0;
    	
    	do {
    		String result = c.getString(ZeroIronDbAdapter.COURSE_NAME_COLUMN);
    		if (result.equals(courseName)) {
    			count++;
    		}
    	} while (c.moveToNext());
    	
    	if (count!= 0) {
    		return true;
    	} else {
    		return false;
    	}
    	
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
	        return mDb.query(DATABASE_GAMES, new String[] {KEY_GAME_ID, KEY_GAME_COURSE_ID, KEY_GAME_NAME,
	        		KEY_GAME_DATE, KEY_GAME_NOTES, KEY_GAME_SCORE, KEY_GAME_STATUS}, null, null, null, null, null);
    	} catch (Exception e ) {
    		Toast.makeText(this.mCtx, "Error fetching all games.", Toast.LENGTH_SHORT).show();
    		return null;
    	}

    }
    
    public Cursor fetchGameFromName(String name) {
    	
    	try {
    		Cursor cursor = mDb.query(DATABASE_GAMES,  new String[] {KEY_GAME_ID, KEY_GAME_COURSE_ID, KEY_GAME_NAME,
	        		KEY_GAME_DATE, KEY_GAME_NOTES, KEY_GAME_SCORE, KEY_GAME_STATUS}, KEY_GAME_NAME + "= '" + name + "'", null, null, null, null, null);
            if (cursor != null) {
            	cursor.moveToFirst();
            	return cursor;
            } 

    	} catch (Exception e ) {
    		Toast.makeText(this.mCtx, "Error fetching all games.", Toast.LENGTH_SHORT).show();
    		return null;
    	}   
    	
    	return null;
    	
    }
    
    public boolean writeGame(ZeroIronGameStructure oldGameStructure, ZeroIronGameStructure newGameStructure) {
    	
    	if (oldGameStructure == null) {
    		
            ContentValues newValues = new ContentValues();
            newValues.put(KEY_GAME_COURSE_ID, newGameStructure.getCourseId());
            newValues.put(KEY_GAME_NAME, newGameStructure.getName());
            newValues.put(KEY_GAME_NOTES, newGameStructure.getNotes());
            newValues.put(KEY_GAME_SCORE, newGameStructure.getScore());
            newValues.put(KEY_GAME_STATUS, newGameStructure.getStatus());
            
            //format date property
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            newValues.put(KEY_GAME_DATE, sdf.format(newGameStructure.getDate().getTime()));
        	
	        if (!gameWithNameExist(newGameStructure.getName())) {
	        	return mDb.insert(DATABASE_GAMES, null, newValues) > 0;
	        } else {
	        	Toast.makeText(this.mCtx, "Error: Game name already exists.", Toast.LENGTH_SHORT).show();
	        	return false;
	        }
            
    	} else {
    		
    		ContentValues newValues = new ContentValues();
    		newValues.put(KEY_GAME_COURSE_ID, newGameStructure.getCourseId());
    		newValues.put(KEY_GAME_NAME, newGameStructure.getName());
    		newValues.put(KEY_GAME_NOTES, newGameStructure.getNotes());
    		newValues.put(KEY_GAME_SCORE, newGameStructure.getScore());
    		newValues.put(KEY_GAME_STATUS, newGameStructure.getStatus());
            
    		ContentValues oldValues = new ContentValues();
    		oldValues.put(KEY_GAME_NAME, "'" + oldGameStructure.getName() + "'");
    		
	        if (!gameWithNameExist(newGameStructure.getName())) {
	        	return mDb.update(DATABASE_GAMES, newValues, oldValues.toString(), null) > 0;
	        } else {
	        	Toast.makeText(this.mCtx, "Error: Game name already exists.", Toast.LENGTH_SHORT).show();
	        	return false;
	        }

    	}    	

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
    
    public boolean deleteGame(String gameName) {
    	
    	try {
    		mDb.delete(DATABASE_GAMES, KEY_GAME_NAME + "='" + gameName + "'", null);
    	} catch (Exception e) {
    		Toast.makeText(this.mCtx, "Error deleting game with name" + gameName + ".", Toast.LENGTH_SHORT).show();
    		return false;
    	}
	
    	return true;
    }
    
    protected boolean gameWithNameExist(String courseName) {
    	
    	Cursor c = fetchAllGames();
    	
    	if (!c.moveToFirst()) {
    		return false;
    	}
    	
    	int count = 0;
    	
    	do {
    		String result = c.getString(ZeroIronDbAdapter.GAME_NAME_COLUMN);
    		if (result.equals(courseName)) {
    			count++;
    		}
    	} while (c.moveToNext());
    	
    	if (count!= 0) {
    		return true;
    	} else {
    		return false;
    	}
    	
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
    		return mDb.query(DATABASE_SETTINGS, new String[] {KEY_SETTING_ID, KEY_SETTING,
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







