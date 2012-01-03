package com.example.android.ZeroIron;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ZeroIronGameStructure implements Serializable {
    
	public static final String GAME_STRUCTURE = "GAME_STRUCTURE";
	
	//members
	protected int mCourseId;
	protected String mName;
	protected GregorianCalendar mDate;
	protected String mNotes;
	protected int mScore;
	protected int mStatus;
	
	//constructor
	public ZeroIronGameStructure() {
		mCourseId = 0;
		mName = new String();
		mDate = new GregorianCalendar();
		mNotes = new String();
		mScore = 0;
		mStatus = 0;
	}
	
	public ZeroIronGameStructure(int courseId, String name, GregorianCalendar date, String notes, int score, int status) {
		mCourseId = courseId;
		mName = name;
		mDate = date;		
		mNotes = notes;
		mScore = score;
		mStatus = status;
	}
    
	public ZeroIronGameStructure(int courseId, String name, String date, String notes, int score, int status) {
		mCourseId = courseId;
		mName = name;
		mNotes = notes;
		mScore = score;
		mStatus = status;
		
		//convert date from string
		SimpleDateFormat sdf = new SimpleDateFormat(ZeroIronDbAdapter.DATE_FORMAT);
		Date dateObject;
		
		try {
			
			dateObject = sdf.parse(date);
			mDate = new GregorianCalendar();			
			mDate.setTime(dateObject);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int getCourseId() {
		return mCourseId;
	}
	
	public String getName() {
		return mName;
	}
	
	public GregorianCalendar getDate() {
		return mDate;
	}

	public String getNotes() {
		return mNotes;
	}

	public int getScore() {
		return mScore;
	}	
	
	public int getStatus() {
		return mStatus;
	}
	
	public void setCourseId(int courseId) {
		mCourseId = courseId;
	}
	
	public void setName(String name) {
		mName = name;
	}
	
	public void setDate(GregorianCalendar date) {
		mDate = date;
	}
	
	public void setNotes(String notes) {
		mNotes = notes;
	}
	
	public void setScore(int score) {
		mScore = score;
	}
	
	public void setStatus(int status) {
		mStatus = status;
	}
	
}